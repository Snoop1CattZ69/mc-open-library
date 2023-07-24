package org.aslstd.api.bukkit.command.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aslstd.api.bukkit.command.ArgParser;
import org.aslstd.api.bukkit.command.Handler;
import org.aslstd.api.bukkit.command.OCommand;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.core.service.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * <p>Abstract BasicCommandHandler class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@Accessors(fluent = true)
public class CommandHandler implements Handler {

	protected Map<String, OCommand> commands = new HashMap<>();

	/**
	 * <p>getRegisteredCommands.</p>
	 *
	 * @return a {@link java.util.Collection} object
	 */
	public Collection<OCommand> getRegisteredCommands() { return ImmutableSet.copyOf(commands.values()); }

	@Getter private OCommand defaultCommand;

	@Getter protected Yaml conf;
	@Getter private String label;
	@Getter private String defLabel;
	protected JavaPlugin plugin;
	@Getter protected String[] aliases;

	/**
	 * <p>Constructor for BasicCommandHandler.</p>
	 *
	 * @param plugin a {@link org.aslstd.api.bukkit.plugin.OpenPlugin} object
	 * @param label a {@link String} object
	 */
	public CommandHandler(JavaPlugin plugin, String label) {
		this.defLabel = label;
		this.plugin = plugin;
		conf = Yaml.of("commands.yml", plugin);
		final String temp = conf.getString(defLabel + ".command-aliases");
		if (temp != null) {
			aliases = temp.replaceAll("\\s+", "").split(",");
		}
		this.label = conf.getString(defLabel + ".command-label", label, true);
	}

	public void reload() {
		Commands.unregisterBukkitCommand(plugin, this);
		conf.reload();

		final String temp = conf.getString(defLabel + ".command-aliases");
		if (temp != null) {
			aliases = temp.replaceAll("\\s+", "").split(",");
		}

		synchronized (commands) {
			commands.values().forEach(OCommand::reload);
		}

		register();
	}

	public CommandHandler register() {
		Commands.registerCommand(plugin, this);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		OCommand cmd = null;

		if (args.length == 0 || commands.get(args[0]) == null)
			cmd = defaultCommand;
		else {
			cmd = commands.get(args[0]);
			args = Texts.trimArgs(args);
		}

		if (cmd.permission() == null || cmd.testConditions(sender) || sender.isOp()) {
			if (args.length >= cmd.minArgs()) {
				final String feedback = cmd.execute(sender, new ArgParser(args));
				if (feedback != null)
					Texts.send(sender, feedback);
			} else
				Texts.send(sender, "Not enough args, usage: " + cmd.usage());
		} else
			sender.sendMessage("Unknown command!");

		return true;
	}

	@Override public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> list = new ArrayList<>();
		if (command.getName().equalsIgnoreCase(label)) {
			if (args.length == 1) {
				list.add(defaultCommand().label());
				for (final OCommand cmd : getRegisteredCommands())
					list.add(cmd.label());

				return list;
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public CommandHandler attachNode(OCommand command) {
		if (defaultCommand == null) { defaultCommand = command; return this; }

		commands.put(command.defLabel(), command);
		return this;
	}

	public CommandHandler attachHelp() { attachNode(new HelpCommand(this)); return this; }

	public CommandHandler attachReload() { attachNode(new ReloadCommand(this)); return this; }

}
