package org.aslstd.api.bukkit.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.aslstd.api.bukkit.command.ArgParser;
import org.aslstd.api.bukkit.command.Executor;
import org.aslstd.api.bukkit.command.OCommand;
import org.aslstd.api.bukkit.command.SenderType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * <p>BasicCommand class.</p>
 *
 * @author Snoop1CattZ69
 */
@Accessors(fluent = true)
public class CommandNode implements OCommand {

	protected SenderType	senderType	= SenderType.ALL;

	protected Executor	func;

	@Getter private String description, permission, arguments, label, defLabel;
	private CommandHandler handler;

	@Getter private int minArgs;

	protected List<Predicate<CommandSender>> conditions = new ArrayList<>();

	/**
	 * <p>Constructor for BasicCommand.</p>
	 *
	 * @param handler a {@link org.aslstd.api.bukkit.command.impl.CommandHandler} object
	 * @param label a {@link String} object
	 * @param func a {@link org.aslstd.api.bukkit.command.Executor} object
	 */
	public CommandNode(CommandHandler handler, String label, int minArgs, Executor func) {
		defLabel = label;
		final String section = handler.defLabel() + ".subcommands." + label + ".";
		this.label = handler.conf.getString(section + "command-name", label, true);
		this.description = handler.conf.getString(section + "description", description() == null ? label + " command description" : description(), true);
		this.permission = handler.conf.getString(section + "permission", permission() == null ? handler.plugin.getName().toLowerCase() + ".command." + label : permission(), true);
		this.arguments = handler.conf.getString(section + "usage-args", "", true);
		this.senderType = SenderType.of(handler.conf.getString(section + "sender-type", "ALL", true));
		this.func = func;
		this.handler = handler;
		this.minArgs = minArgs;
		conditions.add(p -> p.hasPermission(permission) );
		conditions.add(p -> CommandNode.isValid(p, senderType));
	}

	public CommandNode(CommandHandler handler, String label, int minArgs, Executor func, List<Predicate<CommandSender>> filters) {
		this(handler, label, minArgs, func);
		conditions.addAll(filters);
	}

	/**
	 * <p>getHelp.</p>
	 *
	 * @return a {@link String} object
	 */
	public String getHelp() { return "&6" + usage() + " - " + "&a" + description(); }

	/** {@inheritDoc} */
	@Override
	public String usage() {
		return "/" + handler.label() + " " + label() + " " + arguments;
	}

	/** {@inheritDoc} */
	@Override
	public SenderType sender() { return senderType; }

	/** {@inheritDoc} */
	@Override
	public String execute(CommandSender sender, ArgParser args) {
		if (func == null) return null; return func.execute(sender, args);
	}

	public List<String> tabComplete(CommandSender sender, String[] args) {
		return null;
	}

	@Override
	public boolean testConditions(CommandSender sender) {
		for (final Predicate<CommandSender> filters : conditions)
			if (!filters.test(sender))
				return false;

		return true;
	}

	/**
	 * <p>isValid.</p>
	 *
	 * @param obj a {@link java.lang.Object} object
	 * @param senderType a {@link org.aslstd.api.bukkit.command.SenderType} object
	 * @return a boolean
	 */
	public static boolean isValid(Object obj, SenderType senderType) {
		final boolean isPlayer = obj instanceof Player;
		switch (senderType) {
			case ALL:			return true;
			case CONSOLE_ONLY:	return !isPlayer;
			case PLAYER_ONLY:	return isPlayer;
			default:			return false;
		}
	}

}
