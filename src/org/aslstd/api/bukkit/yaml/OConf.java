package org.aslstd.api.bukkit.yaml;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>Abstract OConf class.</p>
 *
 * @author Snoop1CattZ69
 */
public abstract class OConf extends Yaml {

	/**
	 * <p>Constructor for OConf.</p>
	 *
	 * @param fileName a {@link String} object
	 * @param plugin a {@link org.bukkit.plugin.java.JavaPlugin} object
	 */
	public OConf(String fileName, JavaPlugin plugin) {
		super(fileName, plugin);

		loadConfig();
	}

	/**
	 * <p>loadConfig.</p>
	 */
	public abstract void loadConfig();

	/** {@inheritDoc} */
	@Override
	public void reload(){
		super.reload();
		loadConfig();
	}
}
