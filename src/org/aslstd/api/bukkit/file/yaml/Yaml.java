package org.aslstd.api.bukkit.file.yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.core.OpenLib;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;

/**
 * Basic wrapper for default .yml files
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class Yaml {

	protected YamlConfiguration	yaml	= new YamlConfiguration();
	protected File				file;

	/**
	 * <p>Constructor for Yaml.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param plugin a {@link org.bukkit.plugin.java.JavaPlugin} object
	 * @param extendedPath a {@link String} object
	 */
	public Yaml(@NotNull File file, @Nullable JavaPlugin plugin, @Nullable String extendedPath) {
		this.file = file;
		width(yaml, Integer.MAX_VALUE);
		try {
			if (fileExists())
				load();
			else {

				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();

				if (plugin != null && plugin.getResource(file.getName()) != null) {
					final String ex = extendedPath;
					if (ex == null)
						exportFile(file.getName(), plugin, plugin.getDataFolder());
					else
						exportFile(file.getName(), plugin, plugin.getDataFolder() + "/" + ex);
					load();
				}
				else {
					file.createNewFile();
					load();
				}
			}
		} catch (IOException | InvalidConfigurationException e) {
			file = null;
			yaml = null;
			e.printStackTrace();
		}
	}

	/**
	 * <p>Constructor for Yaml.</p>
	 *
	 * @param path a {@link String} object
	 * @param plugin a {@link org.bukkit.plugin.java.JavaPlugin} object
	 */
	public Yaml(@NotNull String path, @Nullable JavaPlugin plugin) { this(new File(path), plugin, null); }

	/**
	 * <p>Constructor for Yaml.</p>
	 *
	 * @param path a {@link String} object
	 */
	public Yaml(@NotNull String path) { this(new File(path), null, null); }

	/**
	 * <p>Constructor for Yaml.</p>
	 *
	 * @param file a {@link java.io.File} object
	 */
	public Yaml(@NotNull File file) { this(file, null, null); }

	/**
	 * @see YamlConfiguration#contains(String)
	 *
	 * @param path - section path
	 */
	public boolean contains(String path) {
		return yaml.contains(path);
	}

	/**
	 * @see File#exists()
	 *
	 * @param true if file already exist
	 */
	protected boolean fileExists() { return file.exists(); }

	/**
	 * @see YamlConfiguration#getBoolean(String)
	 *
	 * @param path - section path
	 * @return stored boolean value
	 */
	public boolean getBoolean(String path) {
		return yaml.getBoolean(path);
	}

	/**
	 * @see YamlConfiguration#getBoolean(String, boolean)
	 *
	 * @param path - section path
	 * @param def - default value if not present
	 * @param restore - regenerate default value in config if not present
	 * @return stored boolean value
	 */
	public boolean getBoolean(String path, boolean def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return yaml.getBoolean(path);
	}

	/**
	 * @see YamlConfiguration#getDouble(String)
	 *
	 * @param path - section path
	 * @return stored double value
	 */
	public double getDouble(String path) {
		return yaml.getDouble(path);
	}

	/**
	 * @see YamlConfiguration#getDouble(String, double)
	 *
	 * @param path - section path
	 * @param def - default value if not present
	 * @param restore - regenerate default value in config if not present
	 * @return stored double value
	 */
	public double getDouble(String path, double def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getDouble(path);
	}

	/**
	 * @see YamlConfiguration#getDoubleList(String)
	 *
	 * @param path - section path
	 * @return {@link List} with stored double values or empty list if values not present
	 */
	public @NotNull List<Double>  getDoubleList(String path) { return yaml.getDoubleList(path); }

	/**
	 * @return a {@link java.io.File} linked with this configuration
	 */
	public @NotNull File getFile() { return file; }

	/**
	 * @see YamlConfiguration#getFloat(String)
	 *
	 * @param path - section path
	 * @return stored float value
	 */
	public float getFloat(String path) {
		final double request = this.getDouble(path);
		return request <= Float.MIN_VALUE ? Float.MIN_VALUE : request >= Float.MAX_VALUE ? Float.MAX_VALUE : Double.valueOf(request).floatValue();
	}

	/**
	 * @see YamlConfiguration#getFloat(String, double)
	 *
	 * @param path - section path
	 * @param def - default value if not present
	 * @param restore - regenerate default value in config if not present
	 * @return stored float value
	 */
	public float getFloat(String path, float def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getFloat(path);
	}

	/**
	 * @see YamlConfiguration#getFloatList(String)
	 *
	 * @param path - section path
	 * @return {@link List} with stored float values or empty list if values not present
	 */
	public @NotNull List<Float>   getFloatList(String path) { return yaml.getFloatList(path); }

	/**
	 * <p>getInt.</p>
	 *
	 * @param path a {@link String} object
	 * @return a int
	 */
	public int getInt(String path) {
		final long request = this.getLong(path);
		return request <= Integer.MIN_VALUE ? Integer.MIN_VALUE : request >= Integer.MAX_VALUE ? Integer.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	/**
	 * <p>getInt.</p>
	 *
	 * @param path a {@link String} object
	 * @param def a int
	 * @param restore a boolean
	 * @return a int
	 */
	public int getInt(String path, int def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getInt(path);
	}

	/**
	 * @see YamlConfiguration#getIntegerList(String)
	 *
	 * @param path - section path
	 * @return {@link List} with stored integer values or empty list if values not present
	 */
	public @NotNull List<Integer> getIntList(String path) { return yaml.getIntegerList(path); }

	/**
	 * <p>getKeys.</p>
	 *
	 * @param deep a boolean
	 * @return a {@link java.util.Set} object
	 */
	public @NotNull Set<String> getKeys(boolean deep) {
		return yaml.getKeys(deep);
	}

	/**
	 * <p>getLong.</p>
	 *
	 * @param path a {@link String} object
	 * @return a long
	 */
	public long getLong(String path) {
		return yaml.getLong(path);
	}

	/**
	 * <p>getLong.</p>
	 *
	 * @param path a {@link String} object
	 * @param def a long
	 * @param restore a boolean
	 * @return a long
	 */
	public long getLong(String path, long def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getLong(path);
	}

	/**
	 * @see YamlConfiguration#getLongList(String)
	 *
	 * @param path - section path
	 * @return {@link List} with stored long values or empty list if values not present
	 */
	public @NotNull List<Long>    getLongList(String path) { return yaml.getLongList(path); }

	/**
	 * <p>getSection.</p>
	 *
	 * @param section a {@link String} object
	 * @return a {@link org.bukkit.configuration.ConfigurationSection} object
	 */
	public ConfigurationSection getSection(String section) {
		return yaml.getConfigurationSection(section);
	}

	/**
	 * <p>getShort.</p>
	 *
	 * @param path a {@link String} object
	 * @return a short
	 */
	public short getShort(String path) {
		final long request = this.getLong(path);
		return request <= Short.MIN_VALUE ? Short.MIN_VALUE : request >= Short.MAX_VALUE ? Short.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	/**
	 * <p>getShort.</p>
	 *
	 * @param path a {@link String} object
	 * @param def a short
	 * @param restore a boolean
	 * @return a short
	 */
	public short getShort(String path, short def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getShort(path);
	}

	/**
	 * @see YamlConfiguration#getShortList(String)
	 *
	 * @param path - section path
	 * @return {@link List} with stored short values or empty list if values not present
	 */
	public @NotNull List<Short> getShortList(String path) { return yaml.getShortList(path); }

	/**
	 * <p>getString.</p>
	 *
	 * @param path a {@link String} object
	 * @return a {@link String} object
	 */
	public @Nullable String getString(String path) {
		return yaml.getString(path);
	}

	/**
	 * <p>getString.</p>
	 *
	 * @param path a {@link String} object
	 * @param def a {@link String} object
	 * @param restore a boolean
	 * @return a {@link String} object
	 */
	public @NotNull String getString(String path, @NotNull String def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getString(path);
	}
	/**
	 * <p>getStringList.</p>
	 *
	 * @param path a {@link String} object
	 * @return a {@link List} object
	 */
	public @NotNull List<String>  getStringList(String path) {
		return yaml.getStringList(path);
	}
	/**
	 * <p>load.</p>
	 *
	 * @throws java.io.FileNotFoundException if any.
	 * @throws java.io.IOException if any.
	 * @throws org.bukkit.configuration.InvalidConfigurationException if any.
	 */
	@Internal
	public void load() throws FileNotFoundException, IOException, InvalidConfigurationException { yaml.load(file); }
	/**
	 * <p>reload.</p>
	 */
	public void reload() {
		try {
			load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * <p>save.</p>
	 */
	@Internal
	public void save() { try { yaml.save(file); } catch (final IOException e) { e.printStackTrace(); } }
	/**
	 * <p>set.</p>
	 *
	 * @param path a {@link String} object
	 * @param value a {@link java.lang.Object} object
	 */
	public void set(@NotNull String path, @Nullable Object value) {
		yaml.set(path, value);
		save();
	}

	/**
	 * <p>getFileExtension.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link String} object
	 */
	@Internal
	public static @NotNull String getFileExtension(File file) {
		final String fileName = file.getName();

		if (fileName.lastIndexOf(".") > 0) return fileName.substring(fileName.lastIndexOf(".") + 1);
		else return "";
	}

	/**
	 * @param path - a file name without file extension.
	 * @return Yaml - a file from "plugins/MC-OpenLibrary/data/<b>path</b>.yml
	 */
	public static Yaml getCustomStorage(@NotNull String path) {
		return new Yaml(new File("plugins/" + OpenLib.instance().getName() + "/data/" + path + ".yml"));
	}

	/**
	 * @param path - a file name with extension.
	 * @param plugin - a plugin what used for data folder
	 * @return Yaml - a file from "plugins/plugin.getDataFolder()/path"
	 */
	public static Yaml of(@NotNull String path, @Nullable JavaPlugin plugin) {
		return new Yaml(plugin.getDataFolder() + "/" + path, plugin);
	}

	/**
	 *
	 * @param path - a file name with extension.
	 * @return a file from "plugins/ejCore/path" if ejCore is enabled<br>
	 * otherwise returns new Yaml(path)
	 */
	public static @NotNull Yaml of(@NotNull String path) {
		final JavaPlugin plugin = OpenLib.instance();

		if (plugin != null && plugin.isEnabled())
			return of(path, plugin);
		else
			return new Yaml(path);
	}

	/**
	 * <p>hasFileInJar.</p>
	 *
	 * @param jarPath a {@link String} object
	 * @param from a {@link org.bukkit.plugin.java.JavaPlugin} object
	 * @return a boolean
	 */
	public static boolean hasFileInJar(@NotNull String jarPath, @NotNull JavaPlugin from) {
		return from.getClass().getResourceAsStream("/" + jarPath) != null;
	}

	public static void exportFile(@NotNull String jarPath, @NotNull JavaPlugin from, @NotNull String toFolder) {
		exportFile(jarPath, from, new File(toFolder));
	}

	/**
	 * <p>exportFile.</p>
	 *
	 * @param jarPath a {@link String} object
	 * @param from a {@link org.bukkit.plugin.java.JavaPlugin} object
	 * @param toFolder a {@link java.io.File} object
	 */
	public static void exportFile(@NotNull String jarPath, @NotNull JavaPlugin from, @NotNull File toFolder) {
		if (from.getClass().getResourceAsStream("/" + jarPath) != null) {
			final String[] split = jarPath.split("/");

			final File toFile = new File(toFolder.getPath(), split[split.length-1]);
			if (toFile.exists()) return;

			try (
					FileOutputStream out = new FileOutputStream(toFile);
					InputStream in = from.getClass().getResourceAsStream("/" + jarPath)
					) {
				if (!toFile.exists())
					toFile.createNewFile();

				final byte[] buffer = new byte[1024];

				int read;
				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}

				Texts.fine("Default file " + split[split.length-1] + " was exported!");
			} catch (final IOException e) {
				e.printStackTrace();
				return;
			}

		} else Texts.warn("File " + jarPath + " does not exist in jar file, Skipped");
	}

	private static void width(YamlConfiguration yaml, int width) {
		try {
			yaml.options().width(width);
		} catch(final NoSuchMethodError ex) {
			Field f;

			try {
				f = yaml.getClass().getField("yamlOptions");
				f.setAccessible(true);

				final DumperOptions opt = (DumperOptions)f.get(yaml);
				opt.setWidth(width);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ignore) {}
		}
	}

}
