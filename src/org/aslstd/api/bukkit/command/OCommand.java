package org.aslstd.api.bukkit.command;

import org.bukkit.command.CommandSender;

/**
 * <p>ECommand interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface OCommand {

	SenderType sender();

	String description();

	String label();

	String defLabel();

	String usage();

	String permission();

	boolean testConditions(CommandSender player);

	String execute(CommandSender sender, ArgParser args);

	int minArgs();

	default void reload() {}

}
