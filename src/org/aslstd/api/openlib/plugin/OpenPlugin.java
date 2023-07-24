package org.aslstd.api.openlib.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.yaml.OConf;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Abstract OpenPlugin class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public abstract class OpenPlugin extends JavaPlugin {

	private List<OConf> configurations = new ArrayList<>();

	@Getter protected int resourceId = -1;

	@Getter @Setter protected int build;
	@Getter @Setter protected int latestBuild;

	@Getter @Setter protected String latestVersion;

	/**
	 * {@inheritDoc}
	 *
	 * OpenPlugin::preInit() used for initialise features without using ListenerLoader and ModuleLoader
	 *
	 * OpenPlugin::init() will be started automatically from Core when all EJPlugin's will be enabled
	 */
	@Override
	public void onEnable() {
		preInit();

		reloadConfigurations();
	}

	/** {@inheritDoc} */
	@Override
	public void onDisable() {
		super.onDisable();

		HandlerList.unregisterAll(this);
		disable();
	}

	/**
	 * <p>init.</p>
	 */
	public abstract void init();

	/**
	 * <p>getPriority.</p>
	 *
	 * @return a int
	 */
	public int getPriority() { return 10; }

	/**
	 * <p>preInit.</p>
	 */
	public void preInit() {}

	/**
	 * <p>disabling.</p>
	 */
	public void disable() {}

	/**
	 * <p>reloadPlugin.</p>
	 */
	public void reloadPlugin() {}

	/**
	 * <p>loadConfigurations.</p>
	 *
	 * @param configurations a {@link OConf} object
	 */
	public void loadConfigurations(OConf... configurations) {
		Stream.of(configurations).forEach(this::loadConfiguration);
	}
	/**
	 * <p>loadListener.</p>
	 *
	 * @param listener a {@link org.bukkit.event.Listener} object
	 */
	@Deprecated
	public void loadListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	/**
	 * <p>loadConfiguration.</p>
	 *
	 * @param cfg a {@link OConf} object
	 */
	public void loadConfiguration(OConf cfg) {
		if (cfg == null) Texts.debug("null configuration was providen, skipped");
		if (!configurations.contains(cfg))
			configurations.add(cfg);
	}

	/**
	 * <p>reloadConfigurations.</p>
	 */
	public void reloadConfigurations() {
		configurations.forEach(OConf::reload);
	}


}
