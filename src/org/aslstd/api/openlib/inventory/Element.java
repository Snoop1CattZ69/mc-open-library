package org.aslstd.api.openlib.inventory;

import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.aslstd.api.openlib.inventory.element.SimpleElement;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * <p>Element interface.</p>
 *
 * @deprecated Will be removed after new inventory framework will be completed
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
@Deprecated(since = "1.0.1", forRemoval = true)
public interface Element {
	/**
	 * @return an Empty instance of SimpleElement
	 */
	static Element empty() { return new SimpleElement(new ItemStack(Material.AIR), true); }

	/**
	 * <p>accept.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 */
	void accept(InventoryClickEvent event);

	/**
	 * <p>getIcon.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	ItemStack getIcon();

	default boolean equals(Element element) {
		if (element instanceof SimpleElement)
			return this.equals(element.getIcon());
		else return false;
	}

	default boolean equals(ItemStack icon) {
		return ItemStackUtil.compareDisplayName(getIcon(), icon);
	}

	default void placeOn(Inventory inventory, int locX, int locY) {
		inventory.setItem(locX + locY * 9, getIcon().clone());
	}

	default void update(Inventory inventory, int locX, int locY) {
		placeOn(inventory,locX,locY);
	}

}
