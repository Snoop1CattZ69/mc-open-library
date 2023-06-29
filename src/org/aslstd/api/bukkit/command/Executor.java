package org.aslstd.api.bukkit.command;

import org.bukkit.command.CommandSender;

/**
 * <p>Usable interface.</p>
 *
 * @author Snoop1CattZ69
 */
@FunctionalInterface
public interface Executor {

	String execute(CommandSender sender, ArgParser args);

}
