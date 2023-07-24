package org.aslstd.core.command;

import java.util.ArrayList;
import java.util.List;

import org.aslstd.api.bukkit.command.OCommand;
import org.aslstd.api.bukkit.command.impl.CommandHandler;
import org.aslstd.api.bukkit.command.impl.CommandNode;
import org.aslstd.api.bukkit.entity.pick.Pick;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.openlib.plugin.hook.Placeholders;
import org.aslstd.core.OpenLib;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

/**
 * <p>
 * CoreCommandHandler class.
 * </p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class CoreCommandHandler extends CommandHandler {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> list = new ArrayList<>();
		if (command.getName().equalsIgnoreCase("mol")) {
			if (args.length == 1) {
				list.add(defaultCommand().label());
				for (final OCommand cmd : getRegisteredCommands())
					list.add(cmd.label());

				return list;
			}
		}
		return null;
	}

	public CoreCommandHandler() {
		super(OpenLib.instance(), "mol");

		attachNode(new CommandNode(this, "help", 0, (s, args) -> {
			Texts.send(s, "&c»------>&5[&6Minecraft Open Plugin Library&5&l]");

			final List<OCommand> commands = new ArrayList<>(getRegisteredCommands());
			commands.add(defaultCommand());

			for (final OCommand command : commands)
				if (command.testConditions(s))
					Texts.send(s, "&6" + command.usage() + " - &2" + command.description()
					+ (s.isOp() || s.hasPermission("*") ? " &f- &5" + command.permission() : ""));

			Texts.send(s, "&c»------>&5[&6Minecraft Open Plugin Library&5&l]");
			return null;
		}))

		.attachNode(new CommandNode(this, "dump", 4, (s, args) -> {
			Texts.dump((Player)s);
			return null;
		}))

		.attachNode(new CommandNode(this, "reload", 0, (s, args) -> {
			OpenLib.instance().reloadPlugin();
			OpenLib.instance().reloadPlugins();
			return null;
		}));

		if (Placeholders.enabled())
			attachNode(new CommandNode(this, "data", 4, (s, args) -> {
				switch (args.arg(0).toLowerCase()) {
					case "player":
						final OfflinePlayer player = args.offline(1);

						if (player == null)
							return "&c[moLib] Player with name/uuid " + args.arg(1) + " was not found";

						String value = args.arg(3);
						final StringBuilder b = new StringBuilder(value);

						if (args.length() > 4) {
							for (int i = 4 ; i < args.length() ; i++) {
								b.append(" ").append(args.arg(i));
							}
						}

						value = PlaceholderAPI.setPlaceholders(player, b.toString());

						if (player.isOnline())
							Pick.of(player.getPlayer()).options().writeData(args.arg(2), value);
						else {
							final Yaml pfile = OpenLib.playerDatabase().getPlayerFile(player);
							pfile.set(args.arg(2), value);
						}

						return "&a[moLib] Successfully added data " + args.arg(2) + ": " + value + " to a player " + player.getName();
					case "custom":
						final Yaml data = Yaml.getCustomStorage(args.arg(1));

						String val = args.arg(3);
						final StringBuilder bu = new StringBuilder(val);

						if (args.length() > 4) {
							for (int i = 3 ; i < args.length() ; i++) {
								bu.append(" ").append(args.arg(i));
							}
						}

						val = PlaceholderAPI.setPlaceholders(null, bu.toString());

						data.set(args.arg(2), val);
						return "&a[moLib] Successfully added data " + args.arg(2) + ": " + val + " to file " + data.getFile().getName();
					default:
						return "&a[moLib] Incorrect usage: /mol data &c<player/custom>&a <name/uid/file> <key> <value>";
				}

			}));
		else
			attachNode(new CommandNode(this, "data", 0, (s, args) -> "&c[moLib] PlaceholderAPI not installed, this command was disabled"));
	}

}
