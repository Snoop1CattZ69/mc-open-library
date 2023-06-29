package org.aslstd.api.bukkit.equip;

import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 *  To get Id for HAND you must use {link ru.asl.api.openlib.entity.EPlayer#????}
 *
 * @author Snoop1CattZ69
 */
@AllArgsConstructor
@Accessors(fluent = true)
public enum EquipSlot {
	HAND(0), OFF(40), HEAD(39), BODY(38), LEGGS(37), FOOTS(36), ALL(-1);

	@Getter private int id;

	/**
	 * <p>byID.</p>
	 *
	 * @param id a int
	 * @return a {@link org.aslstd.api.openlib.equip.EquipSlot} object
	 */
	public static EquipSlot id(int id) {
		for (final EquipSlot slot : values())
			if (id == slot.id) return slot;
		return null;
	}

	/**
	 * <p>getFromItemType.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @param checkoff a boolean
	 * @return a {@link org.aslstd.api.openlib.equip.EquipSlot} object
	 */
	public static EquipSlot get(Material mat, boolean checkoff) {
		if (ItemStackUtil.isHelmet(mat)) return HEAD;
		if (ItemStackUtil.isChestplate(mat)) return BODY;
		if (ItemStackUtil.isLeggings(mat)) return LEGGS;
		if (ItemStackUtil.isBoots(mat)) return FOOTS;
		if (ItemStackUtil.isShield(mat) && checkoff) return OFF;
		return HAND;
	}

	/**
	 * <p>getStackFromSlot.</p>
	 *
	 * @param slot a {@link org.aslstd.api.openlib.equip.EquipSlot} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static ItemStack get(EquipSlot slot, Player p) {
		return switch(slot) {
			case HEAD -> p.getInventory().getHelmet();
			case BODY -> p.getInventory().getChestplate();
			case LEGGS -> p.getInventory().getLeggings();
			case FOOTS -> p.getInventory().getBoots();
			case HAND -> p.getInventory().getItemInMainHand();
			case OFF -> p.getInventory().getItemInOffHand();
			default -> new ItemStack(Material.AIR);
		};
	}

}
