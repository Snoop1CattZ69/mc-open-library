package org.aslstd.api.openlib.inventory.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * <p>PageReturnItemsEvent class.</p>
 *
 * @deprecated Will be removed after new inventory framework will be completed
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
@Deprecated(since = "1.0.1", forRemoval = true)
public class PageReturnItemsEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	/** {@inheritDoc} */
	@Override
	public HandlerList getHandlers() {
		return PageReturnItemsEvent.HANDLERS;
	}

	/**
	 * <p>getHandlerList.</p>
	 *
	 * @return a {@link org.bukkit.event.HandlerList} object
	 */
	public static HandlerList getHandlerList() {
		return PageReturnItemsEvent.HANDLERS;
	}


}
