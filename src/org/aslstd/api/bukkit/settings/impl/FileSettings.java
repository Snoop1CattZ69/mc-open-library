package org.aslstd.api.bukkit.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aslstd.api.bukkit.file.yaml.Yaml;
import org.aslstd.api.bukkit.settings.Settings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>StringSettings class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class FileSettings extends Settings<String> {

	/**
	 * <p>importArray.</p>
	 *
	 * @param array a {@link List} object
	 * @param key a {@link String} object
	 */
	public void importArray(@NotNull List<? extends Object> array, String key) {
		for (int i = 0; i < array.size(); i++)
			settings.put(String.valueOf(key.toLowerCase()) + "." + i, array.get(i).toString());
	}

	/**
	 * <p>removeArray.</p>
	 *
	 * @param key a {@link String} object
	 */
	public void removeArray(@NotNull String key) {
		for (int i = 0; hasKey(String.valueOf(key) + "." + i); i++)
			remove(String.valueOf(key) + "." + i);
	}

	/**
	 * <p>exportArray.</p>
	 *
	 * @param key a {@link String} object
	 * @return a {@link List} object
	 */
	public List<String> exportArray(@NotNull String key) {
		final List<String> keys = new ArrayList<>();
		for (int i = 0 ; hasKey(String.valueOf(key) + "." + i); i++)
			keys.add(settings.get(String.valueOf(key) + "." + i));
		return keys;
	}

	public void importYaml(@NotNull Yaml file) {
		importYaml(file, "");
	}

	public void importYaml(@NotNull Yaml file, @NotNull String section) {
		final Set<String> keys = section.equalsIgnoreCase("") ? file.getSection(section).getKeys(true) : file.getKeys(true);

		if (!section.equalsIgnoreCase(""))
			section = section + ".";

		for (final String key : keys) {
			if (!file.getString(String.valueOf(section) + key).startsWith("MemorySection[path='")
					|| !file.getStringList(String.valueOf(section) + key).isEmpty()) {
				if (!file.getStringList(String.valueOf(section) + key).isEmpty()) {
					importArray(file.getStringList(String.valueOf(section) + key), key);
					continue;
				}
				if (file.getString(String.valueOf(section) + key).equalsIgnoreCase("remove")
						|| file.getString(String.valueOf(section) + key).equalsIgnoreCase("null")) {
					if (settings.containsKey(key))
						settings.remove(key);
					continue;
				}
				settings.put(key.toLowerCase(), file.getString(String.valueOf(section) + key));
			}
		}
	}

	/**
	 * <p>importFromSettings.</p>
	 *
	 * @param settings a {@link Settings} object
	 */
	public void importFromSettings(@NotNull Settings<String> settings) {
		final Set<Map.Entry<String, String>> keys = settings.getKeys();

		for (final Map.Entry<String, String> param : keys) {
			if (param.getValue().equalsIgnoreCase("remove") || param.getValue().equalsIgnoreCase("null")) {
				if (this.settings.containsKey(param.getKey()))
					this.settings.remove(param.getKey());
				continue;
			}

			this.settings.put(param.getKey(), param.getValue());
		}
	}

	public void exportYaml(@NotNull Yaml file) {
		exportYaml(file, "");
	}

	/**
	 * <p>exportToYaml.</p>
	 *
	 * @param file a {@link Yaml} object
	 * @param section a {@link String} object
	 */
	public void exportYaml(@NotNull Yaml file, @Nullable String section) {
		if (section == null) section = "";
		else
			if (!section.equalsIgnoreCase(""))
				section = section + ".";

		for (final Map.Entry<String, String> key : getKeys()) {
			if (key.getKey().matches("^.*\\.[1-9]*"))
				continue;
			if (key.getKey().matches("^.*\\.0$")) {
				file.set(String.valueOf(section) + key.getKey().substring(0, key.getKey().length() - 2), exportArray(key.getKey().substring(0, key.getKey().length() - 2)));
				continue;
			}
			file.set(String.valueOf(section) + key.getKey(), key.getValue());
		}
	}
}
