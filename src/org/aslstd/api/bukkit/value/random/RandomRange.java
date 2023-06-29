package org.aslstd.api.bukkit.value.random;

import java.util.Random;

import org.aslstd.api.bukkit.value.ModifierType;
import org.aslstd.api.bukkit.value.Value;
import org.aslstd.api.bukkit.value.util.NumUtil;

import lombok.Getter;
import lombok.NonNull;

/**
 * <p>RandomRangeValue class.</p>
 *
 * @author Snoop1CattZ69
 */
public class RandomRange implements RandomVal {

	@Getter private boolean 		hasChance;

	@Getter private double 			chance = 100;

	@Getter private ModifierType 	type = ModifierType.POSITIVE;

	@Getter private Value 	firstValue, secondValue;

	@Getter private String 	perLevelValue;

	private boolean isInteger = false;

	/**
	 * <p>Constructor for RandomRangeValue.</p>
	 *
	 * @param chance a double
	 * @param firstValue a {@link String} object
	 * @param secondValue a {@link String} object
	 * @param perLevelValue a {@link String} object
	 * @param type a {@link ModifierType} object
	 */
	public RandomRange(double chance, @NonNull String firstValue, @NonNull String secondValue, String perLevelValue, @NonNull ModifierType type) {
		if (chance > 0 || chance < 100)  { hasChance = true; this.chance = chance; }

		if (type != null)
			this.type = type;
		this.perLevelValue = perLevelValue;

		String[] val = firstValue.split("-");
		final double intCheck_a = NumUtil.parseDouble(val[0]), intCheck_b = NumUtil.parseDouble(val[1]);

		isInteger = Math.abs(intCheck_a) - Math.floor(intCheck_a) <= 1e-8d && Math.abs(intCheck_b) - Math.floor(intCheck_b) <= 1e-8d;

		if (isInteger) {
			firstValue = Math.floor(intCheck_a) + "-" + Math.floor(intCheck_b);
			val = secondValue.split("-");
			secondValue = Math.floor(NumUtil.parseDouble(val[0])) + "-" + Math.floor(NumUtil.parseDouble(val[1]));
		}

		this.firstValue = new Value(firstValue);
		this.secondValue = new Value(secondValue);
	}

	/** {@inheritDoc} */
	@Override
	public Value roll(double lvl) {
		if (!NumUtil.isTrue(chance, 100)) return null;
		else {

			String[] split;
			if (perLevelValue != null && lvl > 1)
				split = firstValue.getAndScale(perLevelValue, lvl-1).split("-");
			else
				split = firstValue.getValue().split("-");

			final double factor = new Random().nextDouble();

			final double fRangeFValue = NumUtil.parseDouble(split[0]);
			final double fRangeSValue = NumUtil.parseDouble(split[1]);

			if (fRangeFValue == 0) return null;

			if (perLevelValue != null && lvl > 1)
				split = secondValue.getAndScale(perLevelValue, lvl-1).split("-");
			else
				split = secondValue.getValue().split("-");

			final double sRangeFValue = NumUtil.parseDouble(split[0]);
			final double sRangeSValue = NumUtil.parseDouble(split[1]);

			final String value = roll(fRangeFValue, sRangeFValue, factor) + "-" + roll(fRangeSValue, sRangeSValue, factor);

			switch(type) {
				case NEGATIVE:
					return new Value("-" + value, type);
				case NEGATIVE_PERCENTS:
					return new Value("-" + value + "%", type);
				case POSITIVE:
					return new Value("+" + value, type);
				case POSITIVE_PERCENTS:
					return new Value("+" + value + "%", type);
				default:
					return new Value(value, type);
			}
		}
	}

	private double roll(double first, double second, double factor) {

		if (first > second) {
			final double temp = second;
			second = first;
			first = temp;
		}

		first = Math.abs(first);
		second = Math.abs(first);

		return first + (second - first) * factor;
	}

}
