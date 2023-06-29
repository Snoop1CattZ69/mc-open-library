package org.aslstd.api.openlib.event.register;

import org.aslstd.api.openlib.player.OPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * <p>EPlayerRegisteredEvent class.</p>
 *
 * @author Snoop1CattZ69
 */
public final class EPlayerRegisteredEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() { return HANDLERS; }

	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter private OPlayer player;

	/**
	 * <p>Constructor for EPlayerRegisteredEvent.</p>
	 *
	 * @param who a {@link OPlayer.aslstd.api.openlib.entity.EPlayer} object
	 */
	public EPlayerRegisteredEvent(OPlayer who) {
		player = who;
	}

}
