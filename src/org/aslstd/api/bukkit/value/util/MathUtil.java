package org.aslstd.api.bukkit.value.util;

import java.util.Random;

import lombok.experimental.UtilityClass;

/**
 * <p>MathUtil class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@UtilityClass
public class MathUtil {

	/**
	 * <p>randomBoolean.</p>
	 *
	 * @return a boolean
	 */
	public boolean randomBoolean() {
		return new Random().nextDouble() > .5D;
	}

	/**
	 * <p>getIntRandomRange.</p>
	 *
	 * @param min a int
	 * @param max a int
	 * @return a int
	 */
	public int getIntRandomRange(int min, int max) {
		return (int) getRandomRange(min, min);
	}

	/**
	 * <p>getRandomRange.</p>
	 *
	 * @param min a double
	 * @param max a double
	 * @return a double
	 */
	public double getRandomRange(double min, double max) {
		if (min == max) return min;

		if (min >= max) {
			final double temp = max;
			max = min;
			min = temp;
		}

		return min + (max - min) * new Random().nextDouble();
	}

	/**
	 * <p>incrementByPercents.</p>
	 *
	 * @param value a double
	 * @param inrease a double
	 * @return a double
	 */
	public double incrementByPercents(double value, double inrease) {
		return (value + (value * (inrease / 100)));
	}

	/**
	 * <p>decrementByPercents.</p>
	 *
	 * @param value a double
	 * @param decrement a double
	 * @return a double
	 */
	public double decrementByPercents(double value, double decrement) {
		return (value + (value * (decrement / 100)));
	}

	/**
	 * <p>getPercentsOfValue.</p>
	 *
	 * @param value a double
	 * @param percents a double
	 * @return a double
	 */
	public double getPercentsOfValue(double value, double percents) {
		return (value * (percents / 100));
	}



	/**
	 * <p>incrementRangeValue.</p>
	 *
	 * @param value a {@link String} object
	 * @param increment a {@link String} object
	 * @return a {@link String} object
	 */
	public String incrementRangeValue(String value, String increment) {
		final String[] incSplit = increment.split("-");

		if (NumUtil.isNumber(incSplit[0])) {
			if (incSplit.length == 1)
				return incrementRangeValue(value, NumUtil.parseDouble(incSplit[0]));

			final double[] values = NumUtil.parseDouble(value.split("-"));
			final double[] increments = NumUtil.parseDouble(incSplit);

			for (int i = 0 ; i < values.length && i < increments.length ; i++) {
				values[i] += increments[i];
			}

			return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
		}

		return value;
	}

	/**
	 * <p>incrementRangeByPercents.</p>
	 *
	 * @param value a {@link String} object
	 * @param percents a double
	 * @return a {@link String} object
	 */
	public String incrementRangeByPercents(String value, double percents) {
		final double[] values = NumUtil.parseDouble(value.split("-"));
		if (values.length == 1) return value;

		for (int i = 0 ; i < values.length ; i++)
			values[i] = incrementByPercents(values[i], percents);

		return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
	}

	/**
	 * <p>incrementRangeValue.</p>
	 *
	 * @param value a {@link String} object
	 * @param increment a double
	 * @return a {@link String} object
	 */
	public String incrementRangeValue(String value, double increment) {
		final double[] values = NumUtil.parseDouble(value.split("-"));
		if (values.length == 1) return value;

		for (int i = 0 ; i < values.length ; i++)
			values[i] += increment;

		return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
	}

}
