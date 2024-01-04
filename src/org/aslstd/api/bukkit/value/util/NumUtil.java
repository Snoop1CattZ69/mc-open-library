package org.aslstd.api.bukkit.value.util;

import java.util.Random;

import lombok.experimental.UtilityClass;

/**
 * <p>ValueUtil class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@UtilityClass
public class NumUtil {

	/**
	 * <p>isNegative.</p>
	 *
	 * @param value a {@link String} object
	 * @return a boolean
	 */
	public boolean isNegative(String value) {
		if (isNumber(value) && Double.parseDouble(value) < 0) return true;

		if (value.startsWith("-")) return true;
		return false;
	}

	/**
	 * <p>isNumber.</p>
	 *
	 * @param value a {@link String} object
	 * @return a boolean
	 */
	public boolean isNumber(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	public boolean isNumberRange(String value) {
		if (value.contains("-")) {
			final String[] s = value.split("-");
			if (s.length <= 1 || s.length > 2) return false;

			return isNumber(s[0]) && isNumber(s[1]);
		} else
			return isNumber(value);
	}

	/**
	 * <p>isTrue.</p>
	 *
	 * @param chance a double
	 * @param random a int
	 * @return a boolean
	 */
	public boolean isTrue(double chance, int random) {
		return chance >= new Random().nextInt(random);
	}

	/**
	 * <p>isString.</p>
	 *
	 * @param value a {@link String} object
	 * @return a boolean
	 */
	public boolean isString(String value) {
		return !isNumber(value);
	}

	/**
	 * <p>isPercent.</p>
	 *
	 * @param value a {@link String} object
	 * @return a boolean
	 */
	public boolean isPercent(String value) {
		if (isString(value)) return value.contains("%");
		return false;
	}

	/**
	 * <p>parseLong.</p>
	 *
	 * @param value a {@link String} object
	 * @return a {@link java.lang.Long} object
	 */
	public Long parseLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (final NumberFormatException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * <p>parseDouble.</p>
	 *
	 * @param value a {@link String} object
	 * @return a {@link Double} object
	 */
	public Double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (final NumberFormatException e) {
			e.printStackTrace();
			return 0.D;
		}
	}

	/**
	 * <p>parseDouble.</p>
	 *
	 * @param values a {@link String} object
	 * @return an array of {@link double} objects
	 */
	public double[] parseDouble(String... values) {
		if (values.length < 1 || values[0] == null || !isNumber(values[0])) return new double[] { 0d };
		final double[] result = new double[values.length];

		for (int i = 0 ; i < values.length ; i++) {
			result[i] = parseDouble(values[i]);
		}

		return result;
	}

	/**
	 * <p>parseInteger.</p>
	 *
	 * @param value a {@link String} object
	 * @return a {@link Integer} object
	 */
	public Integer parseInteger(String value) {
		final Long req = parseLong(value);

		return (req < Integer.MIN_VALUE ? Integer.MIN_VALUE :
			req > Integer.MAX_VALUE ? Integer.MAX_VALUE :
				req.intValue());
	}

	/**
	 * <p>parseShort.</p>
	 *
	 * @param value a {@link String} object
	 * @return a {@link Short} object
	 */
	public Short parseShort(String value) {
		final Integer req = parseInteger(value);

		return (req < Short.MIN_VALUE ? Short.MIN_VALUE :
			req > Short.MAX_VALUE ? Short.MAX_VALUE :
				req.shortValue());
	}

	/**
	 * <p>parseFloat.</p>
	 *
	 * @param value a {@link String} object
	 * @return a {@link Float} object
	 */
	public Float parseFloat(String value) {
		final Double req = parseDouble(value);

		return (req < Float.MIN_VALUE ? Float.MIN_VALUE :
			req > Float.MAX_VALUE ? Float.MAX_VALUE :
				req.floatValue());
	}

}
