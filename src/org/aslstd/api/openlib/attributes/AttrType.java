package org.aslstd.api.openlib.attributes;

/**
 * Enum collection of attributes calculating types.
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)

 */
public enum AttrType {
	/**
	 * <h1>Means:</h1>Attribute uses two values for calculating (<i><b>lore view attr: 2-3</b></i>)<br><br>
	 *
	 * Every time attribute calculates (<i>for ex.Physical-Damage</i>)<br>
	 * result value will be between <b>first</b> and second <b>value</b>;
	 */
	RANGE,
	/**
	 * <h1>Means:</h1>Attribute will be used flatly without random or scaling.
	 */
	SINGLE;
}
