package org.aslstd.api.openlib.inventory;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
/**
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public interface Chest extends InventoryHolder {

	Map<Integer,String> save();

	void load(Map<Integer,String> map);

	void addItem(ItemStack stack);

	void addItem(ItemStack... stacks);

	void remove(ItemStack stack);

	void remove(Player player, int slot);

	int count(Material material);

	void remove(Material material, int amount);

	default void onClick(InventoryClickEvent e) {}

	default void onDrag(InventoryDragEvent e) {}

	default void onOpen(InventoryOpenEvent e) {}

	default void onClose(InventoryCloseEvent e) {}

	default void open(Player p) {
		p.closeInventory();
		p.openInventory(getInventory());
	}

}
