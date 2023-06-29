package org.aslstd.api.openlib.attributes;

import org.aslstd.core.OpenLib;
import org.bukkit.NamespacedKey;

public class Attributes {
	private static final NamespacedKey of(String keyName) { return new NamespacedKey(OpenLib.instance(), keyName); }

	public static final NamespacedKey

	HEALTH_MAX 		= of("health-max"),
	HEALTH_REGEN	= of("health-regeneration"),
	HUNGER_MAX 		= of("hunger-max"),

	MANA_MAX		= of("mana-max"),
	MANA_REGEN		= of("mana-regeneration"),

	MOVE_SPEED		= of("movement-speed"),
	EXP_BONUS		= of("experience-bonus"),

	FIST_DAMAGE 	= of("fist-damage"),
	PHYS_DAMAGE 	= of("physical-damage"),
	PROJ_DAMAGE 	= of("projectile-damage"),
	MAGIC_DAMAGE 	= of("magic-damage"),

	KNOCKBACK		= of("knockback"),

	PHYS_DEFENSE 	= of("physical-defense"),
	PROJ_DEFENSE 	= of("projectile-defense"),
	MAGIC_DEFENSE 	= of("magic-defense"),

	DODGE 			= of("dodge"),
	BLOCK 			= of("shield-block"),
	ABSORB			= of("absorbtion"),
	LIFESTEAL 		= of("lifesteal"),
	REFLECT			= of("reflection"),

	CRIT_CHANCE 	= of("critical-chance"),
	CRIT_DAMAGE 	= of("critical-damage"),
	MAG_CRIT_CHANCE = of("magic-critical-chance"),
	MAG_CRIT_DAMAGE = of("magic-critical-damage"),

	PVP_DMG_MOD 	= of("pvp-damage-modifier"),
	PVP_DEF_MOD 	= of("pvp-defense-modifier"),
	PVE_DMG_MOD 	= of("pve-damage-modifier"),
	PVE_DEF_MOD 	= of("pve-defense-modifier"),

	STUN			= of("stun"),
	ROOTS			= of("roots");

}
