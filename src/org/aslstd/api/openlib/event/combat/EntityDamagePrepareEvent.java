package org.aslstd.api.openlib.event.combat;

import org.aslstd.api.openlib.event.combat.CombatEvent.CombatType;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * <p>EntityDamagePrepareEvent class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@AllArgsConstructor
public class EntityDamagePrepareEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	/** {@inheritDoc} */
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	/**
	 * <p>getHandlerList.</p>
	 *
	 * @return a {@link org.bukkit.event.HandlerList} object
	 */
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Getter @NonNull private Entity attacker;

	@Getter @NonNull private DamageCause cause;

	@Getter @Setter private double damage;

	@Getter @NonNull private CombatType type;

	@Getter private boolean ranged;

}
