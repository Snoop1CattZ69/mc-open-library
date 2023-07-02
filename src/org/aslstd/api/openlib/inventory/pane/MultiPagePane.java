package org.aslstd.api.openlib.inventory.pane;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

import org.aslstd.api.bukkit.items.InventoryUtil;
import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.openlib.inventory.Page;
import org.aslstd.api.openlib.inventory.Pane;
import org.aslstd.api.openlib.inventory.element.SimpleElement;
import org.aslstd.api.openlib.inventory.page.LockedPage;
import org.aslstd.core.OpenLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import net.kyori.adventure.text.Component;

/**
 * <p>MultiPagePane class.</p>
 *
 * @deprecated Will be removed after new inventory framework will be completed
 * @author Snoop1CattZ69
 */
@Deprecated(since = "1.0.1", forRemoval = true)
public class MultiPagePane implements Pane {
	/* Обычнай заглушка */
	/** {@inheritDoc} */
	@Override
	public Inventory getInventory() { return Bukkit.createInventory(null, 9); }

	private static SimpleElement btnNext =
			new SimpleElement(ItemStackUtil.toStack(Yaml.of("gui.yml").getString("multi-page-menu.next-page-button", "ARROW:1:0:0♥&6Next Page", true)), e -> {
				final MultiPagePane pane = (MultiPagePane) e.getInventory().getHolder();
				pane.next((Player) e.getWhoClicked(), e);
			}),
			btnPrev =
			new SimpleElement(ItemStackUtil.toStack(Yaml.of("gui.yml").getString("multi-page-menu.next-page-button", "ARROW:1:0:0♥&6Previous Page", true)), e -> {
				final MultiPagePane pane = (MultiPagePane) e.getInventory().getHolder();
				pane.previous((Player) e.getWhoClicked(), e);
			});

	protected final String	title;
	protected final int		size;
	protected final LinkedList<Page>	pages;
	private boolean addButtons = true;

	protected int currentPage = 0;

	/**
	 * <p>Constructor for MultiPagePane.</p>
	 *
	 * @param title a {@link String} object
	 * @param size a int
	 * @param addButtons a boolean
	 * @param pages a {@link org.aslstd.api.openlib.inventory.Page} object
	 */
	public MultiPagePane(String title, int size, boolean addButtons, Page... pages) {
		this(title, size, addButtons);
		for (final Page page : pages)
			addPage(page);
	}

	/**
	 * <p>Constructor for MultiPagePane.</p>
	 *
	 * @param title a {@link String} object
	 * @param size a int
	 * @param addButtons a boolean
	 */
	public MultiPagePane(String title, int size, boolean addButtons) {
		this.title = Objects.requireNonNull(title);
		this.size = size;
		this.addButtons = addButtons;
		pages = new LinkedList<>();
	}

	/**
	 * <p>addPage.</p>
	 *
	 * @param page a {@link org.aslstd.api.openlib.inventory.Page} object
	 */
	public void addPage(Page page) {
		pages.add(page);
		if (addButtons)
			if (pages.size() > 1) {
				pages.get(pages.size()-1).add(btnNext, 5, page.height()-1, true);
				page.add(btnPrev, 3, page.height()-1, true);
			}
	}

	@Override
	public Page getPage() {
		return pages.get(currentPage);
	}


	/** {@inheritDoc} */
	@Override
	public void fire(InventoryClickEvent event) {
		final Page page = pages.get(currentPage);

		if (page instanceof LockedPage) {
			final LockedPage lPage = (LockedPage) page;
			if (!lPage.isUnlocked(event.getSlot())) {
				lPage.fire(event);
				return;
			}
		}

		page.fire(event);
	}

	/** {@inheritDoc} */
	@Override
	public void showTo(Player... players) {
		final Inventory inventory = Bukkit.createInventory(this, size, Component.text(title));

		if (pages.size() == 0) return;

		if (currentPage >= pages.size()) currentPage = 0;
		if (currentPage < 0) currentPage = pages.size()-1;

		pages.get(currentPage).display(inventory);

		Arrays.asList(players).stream()
		.filter(p -> p != null)
		.forEach(p -> {
			Bukkit.getScheduler().scheduleSyncDelayedTask(OpenLib.instance(), () -> {
				p.closeInventory(); p.openInventory(inventory);
			});
		});
	}

	/**
	 * <p>next.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 */
	public void next(Player p, InventoryClickEvent event) {
		this.returnItems(p, event);
		currentPage += 1;
		showTo(p);
	}

	/**
	 * <p>previous.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 */
	public void previous(Player p, InventoryClickEvent event) {
		this.returnItems(p, event);
		currentPage -= 1;
		showTo(p);
	}

	private void returnItems(Player p, InventoryClickEvent event) {
		final Page page = pages.get(currentPage);

		if (page instanceof LockedPage) {
			final LockedPage lPage = (LockedPage) page;
			if (lPage.getUnlocked().isEmpty()) return;
			lPage.getUnlocked().stream()
			.filter(i -> event.getView().getTopInventory().getItem(i) != null)
			.forEach(i -> InventoryUtil.addItem(event.getView().getTopInventory().getItem(i), p));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void returnItems(Player p, InventoryCloseEvent event) {
		final Page page = pages.get(currentPage);

		if (page instanceof LockedPage) {
			final LockedPage lPage = (LockedPage) page;
			if (lPage.getUnlocked().isEmpty()) return;
			lPage.getUnlocked().stream()
			.filter(i -> event.getView().getTopInventory().getItem(i) != null)
			.forEach(i -> InventoryUtil.addItem(event.getView().getTopInventory().getItem(i), p));
		}
	}

	@Override
	public void update(Inventory inv) {
		final Page page = pages.get(currentPage);
		page.display(inv);
		inv.getViewers().stream().filter(h -> h instanceof Player).forEach(h -> ((Player)h).updateInventory());
	}

	@Override
	public void update(Inventory inv, int locX, int locY) {
		pages.forEach(p -> p.update(inv, locX, locY));
	}

}
