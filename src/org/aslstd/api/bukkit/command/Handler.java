package org.aslstd.api.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

/**
 * <p>CommandHandler interface.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public interface Handler extends CommandExecutor, TabCompleter {

	String label();

	/**
	 * <p>getDefaultCommand.</p>
	 *
	 * @return a {@link org.aslstd.api.bukkit.command.OCommand} object
	 */
	OCommand defaultCommand();

	/**
	 * <p>registerCommand.</p>
	 *
	 * @param command a {@link org.aslstd.api.bukkit.command.OCommand} object
	 */
	Handler attachNode(OCommand command);

	default String[] aliases() {
		return new String[0];
	}

	default boolean equalsThis(Command cmd) {
		return cmd.getName().equalsIgnoreCase(label());
	}
}
