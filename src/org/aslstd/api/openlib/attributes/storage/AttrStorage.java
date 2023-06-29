package org.aslstd.api.openlib.attributes.storage;

import javax.annotation.Nonnull;

import org.aslstd.api.bukkit.equip.EquipSlot;
import org.aslstd.api.bukkit.value.ValuePair;
import org.aslstd.api.openlib.attributes.AttrType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class AttrStorage {

	@Nonnull private AttrType type;
	@Nonnull private ValuePair<Double> base;
	@Getter private ValuePair<Double> calculated;

	private AttrModifiable mods = new AttrModifiable();

	public AttrStorage addModifier(ValuePair<Double> value) {
		mods.add(value);
		return this;
	}

	public AttrStorage removeModifier(EquipSlot slot) {
		mods.removeIf(v -> v.key().equalsIgnoreCase(slot.name()));
		return this;
	}

	public ValuePair<Double> calculate() {
		return (calculated = base.clone().add(mods.calculate()));
	}

}
