package org.aslstd.api.openlib.event.register;

import org.aslstd.api.openlib.player.OPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * <p>OPlayerRegisteredEvent class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public final class OPlayerRegisteredEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() { return HANDLERS; }

	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter private OPlayer player;

	/**
	 * <p>Constructor for OPlayerRegisteredEvent.</p>
	 *
	 * @param who a {@link OPlayer.aslstd.api.openlib.entity.EPlayer} object
	 */
	public OPlayerRegisteredEvent(OPlayer who) {
		player = who;
	}

}
