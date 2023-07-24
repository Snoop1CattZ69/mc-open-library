package org.aslstd.api.bukkit.command;

import org.bukkit.command.CommandSender;

/**
 * <p>ECommand interface.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
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
