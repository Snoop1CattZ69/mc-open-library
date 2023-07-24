package org.aslstd.api.bukkit.value.random;

import org.aslstd.api.bukkit.value.Value;
import org.aslstd.api.bukkit.value.util.MathUtil;

/**
 * <p>RandomBooleanValue class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class RandomBool implements RandomVal {

	/** {@inheritDoc} */
	@Override
	public Value roll(double lvl) {
		return new Value(String.valueOf(MathUtil.randomBoolean()));
	}

}
