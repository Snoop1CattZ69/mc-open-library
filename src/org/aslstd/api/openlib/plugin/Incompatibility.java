package org.aslstd.api.openlib.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.aslstd.api.bukkit.message.Texts;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * <p>Incompatibility class.</p>
 *
 * @author Snoop1CattZ69
 */
@SuppressWarnings("serial")
public class Incompatibility {

	private static final Map<String, String> pluginsIncompat = new HashMap<String, String>() {{
		put("ExecutableItems", "ElephantItems");
		put("ExecutableBlocks", "ElephantItems");
	}};

	/**
	 * <p>check.</p>
	 */
	public static void check() {
		Plugin source, target;
		boolean f = false;

		final Iterator<Map.Entry<String,String>> it = pluginsIncompat.entrySet().iterator();
		for (; it.hasNext(); ) {
			final Map.Entry<String,String> entry = it.next();
			source = Bukkit.getPluginManager().getPlugin(entry.getKey());
			target = Bukkit.getPluginManager().getPlugin(entry.getValue());

			if (source == null || target == null) continue;

			if (!f) {
				Texts.warn("            <---------- CAUTION ---------->");
				f = true;
			}

			Texts.warn(target.getName() + ": Plugin incompatibility found -> " + source.getName());
		}

		if (!it.hasNext() && f) {
			Texts.warn("You will not receive any support due to plugin incompatibility");
			Texts.warn("            <---------- CAUTION ---------->");
			Texts.sendLB();
		}
	}

}
