package org.aslstd.core.expansion;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.aslstd.api.bukkit.entity.pick.Pick;
import org.aslstd.api.bukkit.entity.util.EntityUtil;
import org.aslstd.api.bukkit.file.yaml.Yaml;
import org.aslstd.api.openlib.plugin.hook.Placeholders;
import org.aslstd.core.OpenLib;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.PlaceholderAPI;

public class DataExpansion extends Placeholders {

	public DataExpansion() {
		super(OpenLib.instance(), "moldata");
	}

	@Override
	public @NotNull List<String> getPlaceholders() {
		return Arrays.asList(
				"moldata_<key>",
				"moldata_player_<player-name/uid>_<key>",
				"moldata_custom_<file-name>_<key>"
				);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (identifier.contains("_player-name_") && p != null)
			identifier = identifier.replace("player-name", p.getName());

		if (PlaceholderAPI.containsBracketPlaceholders(identifier))
			identifier = PlaceholderAPI.setBracketPlaceholders(p, identifier);

		final String[] params = identifier.split("_");

		Arrays.asList(params).forEach(Objects::requireNonNull);

		if (params.length == 1) {
			if (p == null) return "[SINGLE] Player was null";

			final String value = Pick.of(p).options().readData(params[0]);
			return value == null ? "value not exist in this player" : value;
		}

		switch(params[0]) {
			case "player":
				final OfflinePlayer player = EntityUtil.getPlayer(params[1]);

				if (player == null) return "[PLAYER] Incorrect params provided";

				if (player.isOnline()) {
					final String value = Pick.of(player.getPlayer()).options().readData(params[2]);

					return value == null ? "value not exist in this player" : value;
				} else {
					final Yaml pfile = OpenLib.playerDatabase().getPlayerFile(player);
					final String val = pfile.getString(params[2], "value not exist in this player", false);
					return val;
				}
			case "custom":
				return Yaml.getCustomStorage(params[1]).getString(params[2], "value not exist in this data-file", false);
		}
		return "[DEFAULT] Incorrect params provided";
	}

}
