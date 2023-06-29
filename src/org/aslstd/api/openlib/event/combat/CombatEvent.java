package org.aslstd.api.openlib.event.combat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * <p>CombatEvent class.</p>
 *
 * @author Snoop1CattZ69
 */
@AllArgsConstructor()
public class CombatEvent extends Event implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	/** {@inheritDoc} */
	@Override public HandlerList getHandlers() { return HANDLERS; }
	/**
	 * <p>getHandlerList.</p>
	 *
	 * @return a {@link org.bukkit.event.HandlerList} object
	 */
	public static HandlerList getHandlerList() { return HANDLERS; }

	@Getter @NonNull private Entity attacker;
	@Getter @NonNull private Entity receiver;

	@Getter @NonNull private DamageCause cause;

	@Getter @Setter private double damage;

	@Getter @NonNull private CombatType type;

	@Getter private boolean ranged;

	@Getter @Setter private boolean cancelled = false;

	public enum CombatType {
		PLAYER_TO_PLAYER, ENTITY_TO_PLAYER, PLAYER_TO_ENTITY, ENTITY_TO_ENTITY;

		@SuppressWarnings("null")
		public static CombatType from(Entity receiver, Entity attacker) {
			if (attacker == null && receiver == null)
				return ENTITY_TO_ENTITY;

			if (attacker == null && receiver != null)
				if (receiver.getType() == EntityType.PLAYER) {
					return ENTITY_TO_PLAYER;
				} else
					return ENTITY_TO_ENTITY;

			if (attacker != null && receiver == null)
				if (attacker.getType() == EntityType.PLAYER) {
					return PLAYER_TO_ENTITY;
				} else
					return ENTITY_TO_ENTITY;

			if (attacker.getType() == EntityType.PLAYER && receiver.getType() == EntityType.PLAYER)
				return CombatType.PLAYER_TO_PLAYER;

			if (attacker.getType() != EntityType.PLAYER && receiver.getType() == EntityType.PLAYER)
				return CombatType.ENTITY_TO_PLAYER;

			if (attacker.getType() == EntityType.PLAYER && receiver.getType() != EntityType.PLAYER)
				return CombatType.PLAYER_TO_ENTITY;

			return CombatType.ENTITY_TO_ENTITY;
		}
	}

}
