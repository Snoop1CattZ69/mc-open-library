package org.aslstd.api.bukkit.command;

import org.bukkit.command.CommandSender;

/**
 * <p>Usable interface.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@FunctionalInterface
public interface Executor {

	String execute(CommandSender sender, ArgParser args);

}
