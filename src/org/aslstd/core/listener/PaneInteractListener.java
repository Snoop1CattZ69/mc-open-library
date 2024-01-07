package org.aslstd.core.listener;

import org.aslstd.api.bukkit.items.InventoryUtil;
import org.aslstd.api.openlib.inventory.Chest;
import org.aslstd.api.openlib.inventory.Pane;
import org.aslstd.api.openlib.plugin.BukkitListener;
import org.aslstd.api.openlib.plugin.Named;
import org.aslstd.core.OpenLib;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * <p>PaneInteractListener class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@Named(key = "paneInteract")
public class PaneInteractListener implements BukkitListener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPaneClick(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return;

		if (event.getInventory().getHolder() instanceof Chest) {
			((Chest)event.getInventory().getHolder()).onClick(event);
			return;
		}

		if (event.getInventory().getHolder() instanceof Pane) {
			if (event.getClickedInventory() instanceof PlayerInventory) {
				if (event.isShiftClick()) event.setCancelled(true);
				return;
			}

			final Player whoClicked = (Player)event.getWhoClicked();

			event.setCancelled(true);

			((Pane) event.getInventory().getHolder()).fire(event);

			if (event.isCancelled()) {
				if (event.getCursor() != null) {
					InventoryUtil.addItem(event.getCursor(), whoClicked);
					event.getView().setCursor(null);
				}

				final ItemStack curr = event.getCurrentItem();

				event.setCurrentItem(null);
				event.getView().setItem(event.getRawSlot(), curr);

				OpenLib.scheduler().schedule(OpenLib.instance(), whoClicked, () -> whoClicked.updateInventory());
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPageOpen(InventoryOpenEvent event) {
		if (event.getInventory().getHolder() instanceof Chest)
			((Chest)event.getInventory().getHolder()).onOpen(event);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPageClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() instanceof Pane) {
			((Pane) event.getInventory().getHolder()).returnItems((Player) event.getPlayer(), event);
			return;
		}

		if (event.getInventory().getHolder() instanceof Chest) {
			((Chest)event.getInventory().getHolder()).onClose(event);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPaneDrag(InventoryDragEvent event) {
		if (event.getInventory().getHolder() instanceof Pane) {
			event.setCancelled(true);
			return;
		}

		if (event.getInventory().getHolder() instanceof Chest) {
			((Chest)event.getInventory().getHolder()).onDrag(event);
		}
	}

}
