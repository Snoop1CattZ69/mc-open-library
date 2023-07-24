package org.aslstd.api.bukkit.value.random;

import org.aslstd.api.bukkit.value.Value;
import org.aslstd.api.bukkit.value.util.MathUtil;

/**
 * <p>RandomBooleanValue class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class RandomBool implements RandomVal {

	/** {@inheritDoc} */
	@Override
	public Value roll(double lvl) {
		return new Value(String.valueOf(MathUtil.randomBoolean()));
	}

}
