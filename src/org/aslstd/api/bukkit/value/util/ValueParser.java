package org.aslstd.api.bukkit.value.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aslstd.api.bukkit.value.ModifierType;
import org.aslstd.api.bukkit.value.random.RandomRange;
import org.aslstd.api.bukkit.value.random.RandomSingle;
import org.aslstd.api.bukkit.value.random.RandomVal;

/**
 * <p>ValueGenerator class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class ValueParser {

	private static final Pattern singlePattern = Pattern.compile("\\s*(chance\\s*([0-9]*[.,]?[0-9]+)%\\s*)?from\\s*([-+]?([0-9]*[.,]?[0-9]+%?))\\s*to\\s*((([-+]?([0-9]*[.,]?[0-9]+%?))))\\s*((per\\s*level\\s*([-+]?([0-9]*[.,]?[0-9]+%?))))?\\s*");
	private static final Pattern rangePattern  = Pattern.compile("\\s*(chance\\s*([0-9]*[.,]?[0-9]+)%\\s*)?from\\s*([-+]?([0-9]*[.,]?[0-9]+)(-([0-9]*[.,]?[0-9]+)))\\s*to\\s*([-+]?([0-9]*[.,]?[0-9]+)(-([0-9]*[.,]?[0-9]+)))\\s*(per\\s*level\\s*([-+]?([0-9]*[.,]?[0-9]+)(-([0-9]*[.,]?[0-9]+))))?\\s*");

	/**
	 * <p>getRandomHolder.</p>
	 *
	 * @param from a {@link String} object
	 * @return a {@link RandomVal} object
	 */
	public static RandomVal getRandomValue(String from) {
		RandomVal val = getSingleValue(from);

		if (val == null)
			val = getRangeValue(from);

		return val;
	}

	/**
	 * <p>getSingleValue.</p>
	 *
	 * @param from a {@link String} object
	 * @return a {@link RandomSingle} object
	 */
	public static RandomSingle getSingleValue(String from) {
		final Matcher match = singlePattern.matcher(from.toLowerCase());

		if (!match.find()) return null;
		final String[] params = new String[] { match.group(2), match.group(3), match.group(7), match.group(11) };

		final double chance = params[0] != null ? NumUtil.parseDouble(params[0]) : 100d;
		final String firstValue = params[1];
		final String secondValue = params[2];
		final double perLevelValue = params[3] != null ? NumUtil.parseDouble(params[3]) : 0d;

		final RandomSingle result = new RandomSingle(chance, NumUtil.parseDouble(firstValue.replaceAll("%", "")), NumUtil.parseDouble(secondValue.replaceAll("%", "")), perLevelValue, ModifierType.getFromValue(firstValue));

		return result;
	}

	/**
	 * <p>getRangeValue.</p>
	 *
	 * @param from a {@link String} object
	 * @return a {@link RandomRange} object
	 */
	public static RandomRange getRangeValue(String from) {
		final Matcher match = rangePattern.matcher(from.toLowerCase());

		if (!match.find()) return null;
		final String[] params = new String[] { match.group(2), match.group(3), match.group(7), match.group(12) };

		final double chance = params[0] != null ? NumUtil.parseDouble(params[0]) : 100;
		final String firstValue = params[1];
		final String secondValue = params[2];
		final String perLevelValue = params[3];

		if (perLevelValue != null && firstValue.split("-").length < 1) throw new IllegalArgumentException("Incorrect FIRST RANGE argument: " + from);

		if (perLevelValue != null && secondValue.split("-").length < 1) throw new IllegalArgumentException("Incorrect SECOND RANGE argument: " + from);

		if (perLevelValue != null && perLevelValue.split("-").length < 1) throw new IllegalArgumentException("Incorrect PER LEVEL RANGE argument" + from);

		final RandomRange result = new RandomRange(chance, firstValue, secondValue, perLevelValue, ModifierType.getFromValue(firstValue));

		return result;
	}

}
