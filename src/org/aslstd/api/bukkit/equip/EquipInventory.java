package org.aslstd.api.bukkit.equip;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

/**
 * <p>EquipInventory class.</p>
 *
 * @author Snoop1CattZ69
 */
public class EquipInventory {

	/*
	 * |=========================|
	 * | 0 = hand  | 1 = offhand |
	 * | 2 = head  | 3 = body    |
	 * | 4 = leggs | 5 = foots   |
	 * |=========================|
	 */
	public EquipInventory() {}

	private HashMap<Integer,ItemStack> equip = new HashMap<>();

	public ItemStack hand() { return this.get(EquipSlot.HAND); }
	public ItemStack off() { return this.get(EquipSlot.OFF); }
	public ItemStack head() { return this.get(EquipSlot.HEAD); }
	public ItemStack body() { return this.get(EquipSlot.BODY); }
	public ItemStack leggs() { return this.get(EquipSlot.LEGGS); }
	public ItemStack foots() { return this.get(EquipSlot.FOOTS); }

	public EquipInventory hand(ItemStack stack) { this.set(EquipSlot.HAND, stack); return this; }
	public EquipInventory off(ItemStack stack) { this.set(EquipSlot.OFF, stack); return this; }
	public EquipInventory head(ItemStack stack) { this.set(EquipSlot.HEAD, stack); return this; }
	public EquipInventory body(ItemStack stack) { this.set(EquipSlot.BODY, stack); return this; }
	public EquipInventory leggs(ItemStack stack) { this.set(EquipSlot.LEGGS, stack); return this; }
	public EquipInventory foots(ItemStack stack) { this.set(EquipSlot.FOOTS, stack); return this; }

	public ItemStack get(EquipSlot slot) { return this.get(slot.id()); }
	private ItemStack get(int id) { return equip.get(id); }

	public void set(EquipSlot slot, ItemStack equip) { this.set(slot.id(), equip); }
	private void set(int id, ItemStack equip) { this.equip.put(id, equip); }

	public void remove(EquipSlot slot) { remove(slot.id()); }
	private void remove(int id) { this.equip.remove(id); }

	public void unequipAll() {
		for (final EquipSlot slot : EquipSlot.values())
			equip.remove(slot.id());
	}

}
