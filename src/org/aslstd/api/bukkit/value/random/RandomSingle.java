package org.aslstd.api.bukkit.value.random;

import org.aslstd.api.bukkit.value.ModifierType;
import org.aslstd.api.bukkit.value.Value;
import org.aslstd.api.bukkit.value.util.MathUtil;
import org.aslstd.api.bukkit.value.util.NumUtil;

import lombok.Getter;
import lombok.NonNull;

/**
 * <p>RandomSingleValue class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class RandomSingle implements RandomVal {

	@Getter private boolean 		hasChance = false;

	@Getter private double 			chance = 100, perLevelValue;

	@Getter private ModifierType 	type = ModifierType.POSITIVE;

	@Getter private final Value 	firstValue;
	@Getter private final Value 	secondValue;

	private boolean isInteger = false;

	/**
	 * <p>Constructor for RandomSingleValue.</p>
	 *
	 * @param chance a double
	 * @param firstValue a double
	 * @param secondValue a double
	 * @param perLevelValue a double
	 * @param type a {@link ModifierType} object
	 */
	public RandomSingle(double chance,	double firstValue, double secondValue, double perLevelValue,  @NonNull ModifierType type) {
		if (chance > 0 || chance < 100)  { hasChance = true; this.chance = chance; }
		this.perLevelValue = perLevelValue;

		if (type != null)
			this.type = type;

		isInteger = Math.abs(firstValue) - Math.floor(firstValue) <= 1e-8d;

		if (isInteger) { firstValue = Math.floor(firstValue); secondValue = Math.floor(secondValue); }

		this.firstValue = new Value(firstValue);
		this.secondValue = new Value(secondValue);
	}

	/** {@inheritDoc} */
	@Override
	public Value roll(double lvl) {
		if (!NumUtil.isTrue(chance*10, 1000) && hasChance) return null;
		else {
			final Value result = new Value();
			if (lvl < 1) lvl = 1;

			final double resultValue = MathUtil.getRandomRange(
					NumUtil.parseDouble(firstValue.getAndScale(perLevelValue, lvl-1)),
					NumUtil.parseDouble(secondValue.getAndScale(perLevelValue, lvl-1))
					);

			result.setType(ModifierType.getFromValue(resultValue, type.isPercent()));

			result.setValue(resultValue);

			return resultValue == 0 ? null : result;
		}
	}

}
