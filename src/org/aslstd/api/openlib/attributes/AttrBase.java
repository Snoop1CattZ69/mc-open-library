package org.aslstd.api.openlib.attributes;

import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;
import org.aslstd.api.bukkit.file.yaml.Yaml;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.value.ValuePair;
import org.aslstd.core.OpenLib;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class AttrBase implements Keyed {

	public static final Pattern getRegexPattern(AttrBase stat) { //\\s*([+-]?\\d+\\.?\\d*\\-?\\d*\\.?\\d*[%]?)
		return Pattern.compile(Texts.e(stat.getVisualName().toLowerCase() + ".?\\s*([+-]?\\d+\\.?\\d*\\-?\\d*\\.?\\d*[%]?)"), Pattern.CASE_INSENSITIVE);
	}

	protected Yaml attrConf = null;
	@Getter private int priority = 1;
	@Getter @Setter private int uniquePosition = 0;

	public void priority(int priority) { this.priority = priority; }
	public void priority(Priority priority) { priority(priority.priority()); }

	@Getter @Accessors(fluent = false) private final NamespacedKey key;
	@Getter protected AttrType type;

	@Getter protected String confPath;
	@Getter protected double defValue, defPerLevel;

	protected ValuePair<Double> base;

	public boolean isEnabled() { return attrConf.getBoolean(toString() + ".is-enabled", true, true); }

	public double getAndScale(int modifier) {
		if (type != AttrType.SINGLE)
			return base.first();

		return base.first() + base.second() * modifier;
	}

	public AttrBase(String keyName, String path, double defBase, double defPerLevel) {
		this(keyName, path, defBase, defPerLevel, AttrType.SINGLE);
	}

	public AttrBase(String keyName, String path, double defValue, double defPerLevel, AttrType type) {
		this.key = new NamespacedKey(OpenLib.instance(), keyName);
		this.confPath = path;
		this.defValue = defValue;
		this.defPerLevel = defPerLevel;
		this.type = type;

		if (attrConf == null)
			attrConf = Yaml.of("attr/" + toString() + ".yml", OpenLib.instance());

		initCustomSettings();
		initializeBasicValues();

		getVisualName();
		getCostValue();
		getColorDecorator();
	}

	public String getVisualName() { return attrConf.getString(toString() + ".visual-name", "&7" + WordUtils.capitalizeFully(toString().replaceAll("-", " ")), true); }

	public double getCostValue() { return attrConf.getDouble(toString() + ".cost-value", 0.0D, true); }

	public String getColorDecorator() { return attrConf.getString(toString() + ".suffix-color-decorator", "&7", true); }

	// 0 = minus or plus, 1 = first value, 2 = "-" if needed, or %, 3 = second
	// value, 4 = "%" if needed.
	/**
	 * <p>getVisualTemplate.</p>
	 *
	 * @return a {@link String} object
	 */
	public String getVisualTemplate() {
		if (type == AttrType.RANGE)
			return getVisualName() + ": " + getColorDecorator() + "$0$1$2$3$4";
		else
			return getVisualName() + ": " + getColorDecorator() + "$0$1$2";
	}

	/**
	 * <p>convertToLore.</p>
	 *
	 * @param values a {@link String} object
	 * @return a {@link String} object
	 */
	public String convertToLore(String... values) {
		String converted = getVisualTemplate();
		int $ = 0;

		for (final String dod : values) {
			converted = converted.replace("$" + $, dod);
			$++;
		}

		while (converted.contains("$" + $)) {
			converted = converted.replace("$" + $, "");
			$++;
		}

		return converted;
	}

	public void initCustomSettings() {}

	public void initializeBasicValues() {
		switch(type) {
			case RANGE -> {
				final String[] values = attrConf.getString(toString() + ".range-value", defValue + "-" + defPerLevel, true).replace(" ", "").split("-");
				if (values.length < 2) {
					Texts.warn(toString() + ": found incorrect template, don't set only one value for this stat, you must write 2 values separated them with &a'-'&4 symbol. For example: &a'2.5-5.0'");
					Texts.warn(toString() + ": initialisation skipped.. using " + defValue + "-" + defPerLevel + " as base value");
					base = ValuePair.of(defValue, defPerLevel);
				} else
					try {
						base = ValuePair.of(Double.parseDouble(values[0]), Double.parseDouble(values[1])).checkSwap();
					} catch(final NumberFormatException e) {
						Texts.warn("RANGE value: &5" + toString()+ ": &5" + values[0] + "-" + values[1] + " |  has incorrect symbols, you must write 2 values separated them with &a'-'&4 symbol. For example: &a'2.5-5.0'");
						base = ValuePair.of(defValue, defPerLevel).checkSwap();
					}
			}
			case SINGLE -> base.first(attrConf.getDouble(toString() + ".value", defValue, true));
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return key.getKey();
	}

	@AllArgsConstructor
	public enum Priority {
		AFTER_DAMAGE_CALCULATING(16),
		BEFORE_DAMAGE_CALCULATING(1);

		/**
		 * <p>before.</p>
		 *
		 * @param stat a {@link org.aslstd.api.openlib.attributes.AttrBase} object
		 * @return a int
		 */
		@Getter private int priority;

		public static int before(AttrBase stat) {
			return stat.priority--;
		}

		/**
		 * <p>after.</p>
		 *
		 * @param stat a {@link org.aslstd.api.openlib.attributes.AttrBase} object
		 * @return a int
		 */
		public static int after(AttrBase stat) {
			return stat.priority++;
		}

	}

}
