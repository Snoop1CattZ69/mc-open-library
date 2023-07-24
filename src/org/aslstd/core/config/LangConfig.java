package org.aslstd.core.config;

import org.aslstd.api.bukkit.yaml.OConf;
import org.aslstd.api.openlib.plugin.OpenPlugin;

/**
 * <p>LangConfig class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class LangConfig extends OConf {

	public String NAME_DURABILITY, DURABILITY_SUFFIX_COLOR_DECORATOR;
	public String ERR_CONSOLE, ERR_NO_PERMISSION, ERR_ARG_CANT_BE_NULL, ERR_PLAYER_ONLINE;

	/**
	 * <p>Constructor for LangConfig.</p>
	 *
	 * @param fileName a {@link String} object
	 * @param plugin a {@link org.aslstd.api.openlib.plugin.OpenPlugin} object
	 */
	public LangConfig(String fileName, OpenPlugin plugin) {
		super(fileName, plugin);
	}

	/** {@inheritDoc} */
	@Override
	public void loadConfig() {
		ERR_CONSOLE = this.getString("err.console-error", "&4Join to server for using this command.", true);
		ERR_NO_PERMISSION = this.getString("err.no-permission", "&4You don't have permission to do this.", true);
		ERR_ARG_CANT_BE_NULL = this.getString("err.arg-null", "&4Args can't be null", true);
		ERR_PLAYER_ONLINE = this.getString("err.player-online", "&4Offline player can't be used in commands", true);

		NAME_DURABILITY = this.getString("util.durability", "&7Durability", true);
		DURABILITY_SUFFIX_COLOR_DECORATOR = this.getString("util.durability-suffix-color-decorator", "&7", true);
	}

}
