package org.aslstd.api.bukkit.value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.yaml.Yaml;

import lombok.Getter;

/**
 * <p>Abstract CustomParam class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public abstract class CustomParam {

	@Getter private final String key;
	@Getter private final String visualName;

	@Getter private final Pattern pattern;

	@Getter private final Pattern number;

	/**
	 * <p>isAllowedValue.</p>
	 *
	 * @param value a {@link String} object
	 * @return a boolean
	 */
	protected abstract boolean isAllowedValue(String value);

	/**
	 * <p>Constructor for CustomParam.</p>
	 *
	 * @param key a {@link String} object
	 * @param file a {@link Yaml} object
	 */
	public CustomParam(String key, Yaml file) {
		this.key = key;
		visualName = file.getString("eimodule.util." + key, "&7" + WordUtils.capitalizeFully(toString()), true);

		pattern = Pattern.compile(Texts.e(visualName.toLowerCase()) + ":\\s*([\\wa-zA-Zа-я-А-Я]*)", Pattern.CASE_INSENSITIVE);
		number = Pattern.compile(Texts.e(visualName.toLowerCase() + ".?\\s*([+-]?\\d+\\.?\\d*\\-?\\d*\\.?\\d*[%]?)"), Pattern.CASE_INSENSITIVE);
	}

	/**
	 * <p>getValue.</p>
	 *
	 * @param from a {@link String} object
	 * @return a {@link String} object
	 */
	public final String getValue(String from) {
		String val = null;

		final Matcher match = pattern.matcher(Texts.e(from).toLowerCase());

		if (match.find())
			val = match.group(1);

		return val;
	}

	public final String getDoubleValue(String from) {
		String val = null;

		final Matcher match = number.matcher(Texts.e(from).toLowerCase());

		if (match.find())
			val = match.group(1);

		return val;
	}

	/**
	 * <p>convert.</p>
	 *
	 * @param value a {@link String} object
	 * @return a {@link String} object
	 */
	public final String convert(String value) {
		if (!isAllowedValue(value)) return null;

		return Texts.c(visualName + ": " + value);
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return key.replaceAll("_", "-");
	}

}
