package org.aslstd.api.bukkit.items;

import org.aslstd.api.bukkit.message.Texts;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

/**
 * <p>InventoryUtil class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public final class InventoryUtil {

	/**
	 * <p>addItem.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @return a boolean
	 */
	public static boolean addItem(ItemStack stack, Player p) {
		if (stack == null) return false;

		for (final ItemStack item : p.getInventory().getStorageContents())
			if (item == null) { p.getInventory().addItem(stack); return true; }

		final Item item = p.getWorld().dropItem(p.getLocation(), stack);
		item.setPickupDelay(0);
		return true;
	}

	public static int count(Player p, Material type) {
		int amount = 0;
		for (final ItemStack item : p.getInventory().getStorageContents()) {
			if (item == null) continue;
			if (item.getType() == type)
				amount+=item.getAmount();
		}
		return amount;
	}

	/**
	 * <p>decreaseItemAmount.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param amount a int
	 */
	public static void decreaseItemAmount(ItemStack stack, Player p, int amount) {
		final ItemStack[] storage = p.getInventory().getContents();
		final String stackString = ItemStackUtil.toString(stack);
		for (int i = 0; i < storage.length; i++)
			if (stackString.equals(ItemStackUtil.toString(storage[i]))) {
				final ItemStack inv = storage[i];

				if (inv.getAmount() > 1) {
					inv.setAmount(inv.getAmount() - amount);
					storage[i] = inv;
				} else
					storage[i] = null;
				p.getInventory().setContents(storage);
				return;
			}
	}

	/**
	 * <p>decreaseItemChecksNameAmount.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param name a {@link String} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param amount a int
	 */
	public static void decreaseItemChecksNameAmount(ItemStack stack, String name, Player p, int amount) {
		if (stack == null) return;
		int value = amount;
		final ItemStack[] storage = p.getInventory().getStorageContents();
		final String toCheck = Texts.e(name == null ? stack.getType().name() : ( (TextComponent)ItemStackUtil.getDisplayName(stack) ).content());

		final Material type = stack.getType();
		for (int i = 0; i < storage.length; i++) {
			if (!ItemStackUtil.validate(storage[i], IStatus.HAS_MATERIAL)) continue;
			if (ItemStackUtil.getDamage(storage[i]) > 0) continue;
			if (Component.EQUALS.test(ItemStackUtil.getDisplayName(storage[i]), Component.text(toCheck)) && storage[i].getType() == type) {
				final ItemStack inv = storage[i];

				if (inv.getAmount() > value) {
					inv.setAmount(inv.getAmount()-value);
					storage[i] = inv;
					break;
				} else {
					value = value-inv.getAmount();
					storage[i] = null;
					continue;
				}
			}
		}
		p.getInventory().setStorageContents(storage);
	}

	/**
	 * <p>decreaseItem.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 */
	public static void decreaseItem(ItemStack stack, Player p) {
		InventoryUtil.decreaseItemAmount(stack,p,1);
	}

	/**
	 * <p>decreaseItemChecksName.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 */
	public static void decreaseItemChecksName(ItemStack stack, Player p) {
		InventoryUtil.decreaseItemChecksNameAmount(stack,"",p,1);
	}

	/**
	 * <p>removeItem.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 */
	public static void removeItem(ItemStack stack, Player p) {
		final ItemStack[] storage = p.getInventory().getStorageContents();
		for (int i = 0; i < storage.length; i++)
			if (ItemStackUtil.toString(stack).equals(ItemStackUtil.toString(storage[i]))) {
				storage[i] = new ItemStack(Material.AIR, 0);
				p.getInventory().setStorageContents(storage);
				return;
			}
	}

}
