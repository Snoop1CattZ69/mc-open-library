package org.aslstd.api.bukkit.value;

import org.aslstd.api.bukkit.value.util.NumUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Value class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class Value {

	@Getter private String value;

	@Getter @Setter private ModifierType type = ModifierType.POSITIVE;

	@Getter @Setter private String keyName;

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link String} object
	 */
	public void setValue(String value) {
		this.value = value.replaceAll("%", "");
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link Number} object
	 */
	public void setValue(Number value) {
		this.value = String.valueOf(value);
	}

	/**
	 * <p>getAndScale.</p>
	 *
	 * @param scale a double
	 * @param lvl a double
	 * @return a {@link String} object
	 */
	public String getAndScale(double scale, double lvl) {
		if (value == null) return null;

		return String.valueOf((NumUtil.parseDouble(value) + (scale * lvl)));
	}

	/**
	 * <p>getAndScale.</p>
	 *
	 * @param scale a {@link String} object
	 * @param lvl a double
	 * @return a {@link String} object
	 */
	public String getAndScale(String scale, double lvl) {
		if (value == null) return null;

		final String[] sc = scale.split("-");
		final String[] val = value.split("-");

		if (sc.length < 2)
			return (NumUtil.parseDouble(val[0]) + NumUtil.parseDouble(sc[0]) * lvl) + "-" + (NumUtil.parseDouble(val[1]) + NumUtil.parseDouble(sc[0]) * lvl);
		else
			return (NumUtil.parseDouble(val[0]) + NumUtil.parseDouble(sc[0]) * lvl) + "-" + (NumUtil.parseDouble(val[1]) + NumUtil.parseDouble(sc[1]) * lvl);
	}

	/**
	 * <p>Constructor for Value.</p>
	 */
	public Value() {}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link String} object
	 */
	public Value(String value) {
		setValue(value);
	}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link Number} object
	 */
	public Value(Number value) {
		setValue(value);
	}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link String} object
	 * @param type a {@link ModifierType} object
	 */
	public Value(String value, ModifierType type) {
		this(value);
		setType(type);
	}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link Number} object
	 * @param type a {@link ModifierType} object
	 */
	public Value(Number value, ModifierType type) {
		this(value);
		setType(type);
	}

}
