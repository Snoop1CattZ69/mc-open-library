package org.aslstd.core.listener;

import org.aslstd.api.bukkit.entity.pick.Pick;
import org.aslstd.api.bukkit.equip.EquipSlot;
import org.aslstd.api.bukkit.location.Vec3;
import org.aslstd.api.openlib.event.equipment.PrepareEquipEvent;
import org.aslstd.api.openlib.event.player.PlayerBlockMoveEvent;
import org.aslstd.api.openlib.event.register.OPlayerRegisteredEvent;
import org.aslstd.api.openlib.player.OPlayer;
import org.aslstd.api.openlib.plugin.BukkitListener;
import org.aslstd.api.openlib.plugin.Named;
import org.aslstd.core.OpenLib;
import org.aslstd.core.update.CheckUpdates;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * <p>PlayerListener class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@Named(key = "playerJoin")
public class PlayerListener implements BukkitListener {

	/**
	 * <p>registerEPlayer.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.PlayerJoinEvent} object
	 */
	@EventHandler
	public void registerOPlayer(PlayerJoinEvent e) {
		OpenLib.scheduler().schedule(OpenLib.instance(), e.getPlayer(), () -> {
			final OPlayer p = Pick.of(e.getPlayer());

			for (final EquipSlot slot : EquipSlot.values())
				Bukkit.getServer().getPluginManager().callEvent(new PrepareEquipEvent(slot, EquipSlot.get(slot, e.getPlayer()), e.getPlayer()));

			for (final String param : OpenLib.config().PLAYER_DATA_DEFAULTS) {
				final String[] data = param.split(":");
				if (data.length < 2) return;

				if (!p.options().checkData(data[0]))
					p.options().writeData(data[0], data[1]);
			}

			Bukkit.getServer().getPluginManager().callEvent(new OPlayerRegisteredEvent(p));
		});

		if (e.getPlayer().isOp() || e.getPlayer().hasPermission("*"))
			CheckUpdates.sendUpdateMessage(e.getPlayer());
	}

	/**
	 * <p>unregisterEPlayer.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.PlayerQuitEvent} object
	 */
	@EventHandler
	public void unregisterOPlayer(PlayerQuitEvent e) {
		OPlayer.stash().remove(e.getPlayer().getUniqueId());
	}

	/**
	 * <p>throwPlayerBlockMove.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.PlayerMoveEvent} object
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void throwPlayerBlockMove(PlayerMoveEvent e) {
		if (!e.isCancelled())
			if (Vec3.blockEquals(e.getFrom(), e.getTo())) {
				final PlayerBlockMoveEvent event = new PlayerBlockMoveEvent(e.getPlayer(),e.getFrom(),e.getTo());
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					e.setCancelled(true);
					return;
				}
				if (event.isLocChanged())
					e.setTo(e.getTo().add(event.getTo().getX(), event.getTo().getY(), event.getTo().getZ()));
			}
	}

}
