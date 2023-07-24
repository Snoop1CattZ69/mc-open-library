package org.aslstd.api.bukkit.value.random;

import org.aslstd.api.bukkit.value.Value;

/**
 * <p>RandomValue interface.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public interface RandomVal {

	/**
	 * <p>roll.</p>
	 *
	 * @param lvl a double
	 * @return a {@link Value} object
	 */
	Value roll(double lvl);

}
