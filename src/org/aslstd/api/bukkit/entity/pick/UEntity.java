package org.aslstd.api.bukkit.entity.pick;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UEntity {

	@Getter
	protected LivingEntity entity;

	/**
	 * @return Entity display name
	 */
	public String displayName() { return entity.getCustomName() != null ? entity.getCustomName() : entity.getType().name(); }

	/**
	 * @return ItemStask what weared in entity main hand
	 */
	public @Nullable ItemStack hand() { return entity.getEquipment().getItemInMainHand(); }

	/**
	 * @return ItemStask what weared in entity off hand
	 */
	public @Nullable ItemStack offhand() { return entity.getEquipment().getItemInOffHand(); }

	/**
	 * @return ItemStask what weared on entity head
	 */
	public @Nullable ItemStack head() { return entity.getEquipment().getHelmet(); }

	/**
	 * @return ItemStask what weared on entity chest
	 */
	public @Nullable ItemStack body() { return entity.getEquipment().getChestplate(); }

	/**
	 * @return ItemStask what weared on entity leggs
	 */
	public @Nullable ItemStack leggs() { return entity.getEquipment().getLeggings(); }

	/**
	 * @return ItemStask what weared on entity foots
	 */
	public @Nullable ItemStack foots() { return entity.getEquipment().getBoots(); }

	public Location location() { return entity.getLocation(); }

	public AttributeInstance maxHealth() { return attribute(Attribute.GENERIC_MAX_HEALTH); }

	public AttributeInstance knockbackResist() { return attribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE); }

	public AttributeInstance speed() { return attribute(Attribute.GENERIC_MOVEMENT_SPEED); }

	public AttributeInstance flyingSpeed() { return attribute(Attribute.GENERIC_FLYING_SPEED); }

	public AttributeInstance damage() { return attribute(Attribute.GENERIC_ATTACK_DAMAGE); }

	public AttributeInstance knockback() { return attribute(Attribute.GENERIC_ATTACK_KNOCKBACK); }

	public AttributeInstance attackSpeed() { return attribute(Attribute.GENERIC_ATTACK_SPEED); }

	public AttributeInstance armor() { return attribute(Attribute.GENERIC_ARMOR); }

	public AttributeInstance toughness() { return attribute(Attribute.GENERIC_ARMOR_TOUGHNESS); }

	public AttributeInstance luck() { return attribute(Attribute.GENERIC_LUCK); }

	private AttributeInstance attribute(Attribute attr) { return entity.getAttribute(attr); }

	public void heal(double amount) {
		if (amount <= 0) {
			entity.setHealth(entity.getHealth()-amount);
			return;
		}

		final AttributeInstance attr = maxHealth();
		final double currentHealth = entity.getHealth();

		if (currentHealth + amount >= attr.getBaseValue())
			entity.setHealth(attr.getBaseValue());
		else
			entity.setHealth(currentHealth + amount);
	}

	public void changeMaxHealth(double newValue) {
		final AttributeInstance attr = maxHealth();

		final double healthModifier = entity.getHealth() / attr.getValue();

		attr.setBaseValue(newValue);

		if (newValue * healthModifier >= newValue)
			entity.setHealth(newValue);
		else
			entity.setHealth(newValue * healthModifier);
		healthChanged(newValue);
	}

	protected void healthChanged(double newValue) {}
}
