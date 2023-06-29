package org.aslstd.core.config;

import java.util.Collections;
import java.util.List;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.yaml.OConf;
import org.aslstd.api.openlib.plugin.OpenPlugin;

/**
 * <p>EConfig class.</p>
 *
 * @author Snoop1CattZ69
 */
public class EConfig extends OConf {

	public boolean	ONE_HP_BAR, ENABLE_CONSOLE_COLORS;
	public int		HEALTH_PER_BAR, UPDATE_PERIOD;
	public boolean	DEBUG_RUNNING,
	CONSOLE_FEEDBACK,
	LESS_CONSOLE,
	ENABLE_ATTACK_COOLDOWN,
	CHECK_UPDATE,
	PLAYER_ATTRIBUTES_ENABLED,
	MODULES_BY_DEFAULT;

	public List<String> PLAYER_DATA_DEFAULTS;

	/**
	 * <p>Constructor for EConfig.</p>
	 *
	 * @param fileName a {@link String} object
	 * @param plugin a {@link org.aslstd.api.openlib.plugin.OpenPlugin} object
	 */
	public EConfig(String fileName, OpenPlugin plugin) {
		super(fileName, plugin);
	}

	/** {@inheritDoc} */
	@Override
	public void loadConfig() {
		ENABLE_CONSOLE_COLORS = this.getBoolean("enable-console-colors", true, true);
		ONE_HP_BAR = this.getBoolean("health-bar.fix-to-one-line", false, true);
		HEALTH_PER_BAR = this.getInt("health-bar.health-per-bar", 20, true);

		DEBUG_RUNNING = this.getBoolean("debug.enable-debug", true, true);
		Texts.setDebug(DEBUG_RUNNING);
		CONSOLE_FEEDBACK = this.getBoolean("debug.console-feedback", true, true);
		Texts.setConsoleFeedback(CONSOLE_FEEDBACK);
		LESS_CONSOLE = this.getBoolean("debug.less-console-messages", false, true);
		ENABLE_ATTACK_COOLDOWN = this.getBoolean("enable-attack-cooldown", true, true);
		CHECK_UPDATE = this.getBoolean("check-updates", true, true);
		UPDATE_PERIOD = this.getInt("check-period", 28800, true);
		PLAYER_ATTRIBUTES_ENABLED = this.getBoolean("modules.player-attributes-enabled", false, true);
		MODULES_BY_DEFAULT = this.getBoolean("modules.enable-by-default", true, true);

		if (!contains("player-data-default"))
			set("player-data-default", Collections.EMPTY_LIST);
		PLAYER_DATA_DEFAULTS = getStringList("player-data-default");
	}

}
