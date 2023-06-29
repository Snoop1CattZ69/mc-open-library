package org.aslstd.api.openlib.event.equipment;

import org.aslstd.api.bukkit.equip.EquipSlot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>EquipChangeEvent class.</p>
 *
 * @author Snoop1CattZ69
 */
@AllArgsConstructor
public class PrepareEquipEvent extends Event {
	public static void call(EquipSlot slot, ItemStack item, Player player) {
		Bukkit.getServer().getPluginManager().callEvent(new PrepareEquipEvent(slot, item, player));
	}

	private static final HandlerList HANDLERS = new HandlerList();
	/** {@inheritDoc} */
	@Override public HandlerList getHandlers() { return HANDLERS; }
	/**
	 * <p>getHandlerList.</p>
	 *
	 * @return a {@link org.bukkit.event.HandlerList} object
	 */
	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter private EquipSlot equipSlot;
	@Getter private ItemStack itemStack;
	@Getter private Player player;

}
