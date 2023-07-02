package org.aslstd.api.openlib.inventory.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.aslstd.api.bukkit.items.IStatus;
import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.value.util.ArrayUtil;
import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.openlib.inventory.Chest;
import org.aslstd.api.openlib.inventory.element.SimpleElement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import net.kyori.adventure.text.Component;

/**
 * @deprecated Will be removed after new inventory framework will be completed
 * @author Snoop1CattZ69
 */
@Deprecated(since = "1.0.1", forRemoval = true)
public class MultiPageChestStorage implements Chest {

	public static final ItemStack EMPTY = new ItemStack(Material.AIR, 1);

	private static SimpleElement btnNext =
			new SimpleElement(ItemStackUtil.toStack(Yaml.of("gui.yml").getString("general.next-page", "ARROW:1:0:0♥&6Next Page", true)), e -> {
				final MultiPageChestStorage pane = (MultiPageChestStorage) e.getInventory().getHolder();
				pane.open((Player) e.getWhoClicked(), pane.currentPage.get(e.getWhoClicked().getUniqueId())+1);
			});
	private static SimpleElement btnPrev =
			new SimpleElement(ItemStackUtil.toStack(Yaml.of("gui.yml").getString("general.prev-page", "ARROW:1:0:0♥&6Previous Page", true)), e -> {
				final MultiPageChestStorage pane = (MultiPageChestStorage) e.getInventory().getHolder();
				pane.open((Player) e.getWhoClicked(), pane.currentPage.get(e.getWhoClicked().getUniqueId())-1);
			});

	public static void reload() {
		btnPrev = new SimpleElement(ItemStackUtil.toStack(Yaml.of("gui.yml").getString("general.prev-page", "ARROW:1:0:0♥&6Previous Page", true)), e -> {
			final MultiPageChestStorage pane = (MultiPageChestStorage) e.getInventory().getHolder();
			pane.open((Player) e.getWhoClicked(), pane.currentPage.get(e.getWhoClicked().getUniqueId())-1);
		});
		btnNext = new SimpleElement(ItemStackUtil.toStack(Yaml.of("gui.yml").getString("general.next-page", "ARROW:1:0:0♥&6Next Page", true)), e -> {
			final MultiPageChestStorage pane = (MultiPageChestStorage) e.getInventory().getHolder();
			pane.open((Player) e.getWhoClicked(), pane.currentPage.get(e.getWhoClicked().getUniqueId())+1);
		});
	}

	private static ItemStack decorator = ItemStackUtil.toStack(Yaml.of("gui.yml").getString("general.storage-decorator", "GRAY_STAINED_GLASS_PANE:1:0:0♥&f", true));

	@Getter private Inventory[] pages;

	private int size;

	public MultiPageChestStorage(String title, int pagesAmount) {
		if (pagesAmount < 2)
			throw new IllegalArgumentException("Use SimpleChestStorage to create one-page storage, MultiPageChestStorage require at least 2 pages");
		size = pagesAmount*45;
		pages = new Inventory[pagesAmount];
		for (int i = 0 ; i < pagesAmount ; i++)
			pages[i] = Bukkit.createInventory(this, 54, Component.text(Texts.c(title)));
	}

	private void placeControls(int i) {
		for (int j = 45 ; j < 54 ; j++) {
			if (j == 48 && i > 0) { pages[i].setItem(j, btnPrev.getIcon()); continue; }
			if (j == 50 && i+1 < pages.length) { pages[i].setItem(j, btnNext.getIcon()); continue; }
			pages[i].setItem(j, decorator.clone());
		}
	}

	@Override
	public Map<Integer,String> save() {
		final Map<Integer,String> inventoryContent = new HashMap<>();

		int i = 0;
		for (final Inventory inv : pages) {
			final ItemStack[] content = inv.getContents();
			int j = 0;
			for (final ItemStack stack : content) {
				if (j > 44) break;
				inventoryContent.put(i, NBTItem.convertItemtoNBT(stack == null ? EMPTY.clone() : stack).toString());
				j++; i++;
			}
		}

		return inventoryContent;
	}

	@Override
	public void load(Map<Integer,String> map) {
		final ItemStack[] content = new ItemStack[size];
		for (final Entry<Integer,String> entry : map.entrySet())
			content[entry.getKey()] = NBTItem.convertNBTtoItem(new NBTContainer(entry.getValue()));

		final List<ItemStack[]> splitted = ArrayUtil.split(45, content);

		for (int i = 0 ; i < pages.length && i < splitted.size() ; i++) {
			pages[i].setContents(splitted.get(i));
			placeControls(i);
		}
	}

	@Override
	public void addItem(ItemStack stack) {
		// TODO
	}

	@Override
	public void addItem(ItemStack... stacks) {
		// TODO
	}

	@Override
	public Inventory getInventory() {
		return pages[0];
	}

	@Override
	public int count(Material mat) {
		int amount = 0;

		for (final Inventory inv : pages) {
			for (int i = 0 ; i < 45 ; i++) {
				final ItemStack stack = inv.getContents()[i];
				if (stack == null || stack.getType() == Material.AIR) continue;

				if (stack.getType() == mat) amount+=stack.getAmount();
			}
		}

		return amount;
	}

	@Override
	public void remove(Material mat, int amount) {
		closeForAll();
		final AtomicReference<Integer> am = new AtomicReference<>(amount);

		for (final Inventory inv : pages)
			if (am.get() > 0)
				removeFromInventory(inv,mat,am);
	}

	private void removeFromInventory(Inventory inv, Material mat, AtomicReference<Integer> val) {
		final ItemStack[] content = inv.getContents();

		for (int i = 0 ; i < 45 ; i++) {
			final ItemStack stack = content[i];
			if (stack == null) { content[i] = EMPTY.clone(); continue; }
			if (stack.getType() == Material.AIR) { content[i] = stack; continue; }

			if (stack.getType() == mat)
				if (stack.getAmount() > val.get()) {
					stack.setAmount(stack.getAmount()-val.get());
					content[i] = stack;
					continue;
				} else {
					val.set(val.get()-stack.getAmount());
					content[i] = EMPTY.clone();
					continue;
				}

			content[i] = stack;
		}

		inv.setContents(content);
	}

	@Override
	public void remove(ItemStack stack) {
		if (ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL))
			remove(stack.getType(), stack.getAmount());
	}

	public void closeForAll() {
		for (final Inventory inv : pages)
			inv.getViewers().forEach(HumanEntity::closeInventory);
	}

	@Override
	public void remove(Player p, int slot) {
		if (currentPage.containsKey(p.getUniqueId())) {
			pages[currentPage.get(p.getUniqueId())].setItem(slot, EMPTY.clone());
		}
	}

	@Override
	public void open(Player p) {
		Chest.super.open(p);
		currentPage.put(p.getUniqueId(), 0);
	}

	public void open(Player p, int page) {
		if (page >= 0 && page < pages.length) {
			p.closeInventory();
			p.openInventory(pages[page]);
			currentPage.put(p.getUniqueId(), page);
		} else
			open(p);
	}

	@Override
	public void onClick(InventoryClickEvent e) {
		if (e.getSlot() < 45) return;

		e.setCancelled(true);
		ItemStack stack = e.getInventory().getItem(e.getSlot());

		if (btnNext.equals(stack)) btnNext.accept(e);
		if (btnPrev.equals(stack)) btnPrev.accept(e);

		stack = null;
	}

	private Map<UUID, Integer> currentPage = new HashMap<>();

	@Override
	public void onClose(InventoryCloseEvent e) {
		currentPage.remove(e.getPlayer().getUniqueId());
	}

}
