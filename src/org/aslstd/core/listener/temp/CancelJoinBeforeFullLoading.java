package org.aslstd.core.listener.temp;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.core.OpenLib;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import net.kyori.adventure.text.Component;

/**
 * <p>CancelJoinBeforeFullLoading class.</p>
 *
 * @author Snoop1CattZ69
 */
public class CancelJoinBeforeFullLoading implements Listener {

	private static CancelJoinBeforeFullLoading instance;

	private static boolean registered = false;

	/**
	 * <p>register.</p>
	 */
	public static void register() {
		if (instance != null) return;
		instance = new CancelJoinBeforeFullLoading();
		Bukkit.getPluginManager().registerEvents(instance, OpenLib.instance());
		registered = true;
	}

	/**
	 * <p>unregister.</p>
	 */
	public static void unregister() {
		if (registered)
			HandlerList.unregisterAll(instance);
	}

	/**
	 * <p>onJoin.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.AsyncPlayerPreLoginEvent} object
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(AsyncPlayerPreLoginEvent e) {
		e.setLoginResult(Result.KICK_OTHER);
		e.kickMessage(Component.text(Texts.c("&cPlease wait while server will be fully loaded!")));
	}
}
