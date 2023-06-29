package org.aslstd.api.openlib.inventory;

import org.aslstd.api.bukkit.items.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * <p>Pane interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface Pane extends InventoryHolder {

	/**
	 * <p>fire.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 */
	void fire(InventoryClickEvent event);

	/**
	 * <p>showTo.</p>
	 *
	 * @param players a {@link org.bukkit.entity.Player} object
	 */
	void showTo(Player... players);

	default void update(Inventory inventory) {
		getPage().display(inventory);
		inventory.getViewers().stream().filter(h -> h instanceof Player).forEach(h -> ((Player)h).updateInventory());
	}

	default void update(Inventory inv, int locX, int locY) {
		getPage().update(inv, locX, locY);
	}

	Page getPage();

	default boolean isReturnItems() {
		return false;
	}

	/**
	 * <p>returnItems.</p>
	 *
	 * @param player a {@link org.bukkit.entity.Player} object
	 * @param event a {@link org.bukkit.event.inventory.InventoryCloseEvent} object
	 */
	default void returnItems(Player player, InventoryCloseEvent event) {
		if (isReturnItems()) {
			if (getPage().getUnlocked().isEmpty()) return;
			getPage().getUnlocked().stream()
			.filter(i -> event.getView().getTopInventory().getItem(i) != null)
			.forEach(i -> InventoryUtil.addItem(event.getView().getTopInventory().getItem(i), player));
		}
	}

}
