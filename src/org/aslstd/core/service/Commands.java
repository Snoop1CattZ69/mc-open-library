package org.aslstd.core.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.aslstd.api.bukkit.command.impl.CommandHandler;
import org.aslstd.api.bukkit.message.Texts;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

public class Commands {

	public static final void registerCommand(Plugin plugin, CommandHandler command) {
		try {
			final Constructor<PluginCommand> constr = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			constr.setAccessible(true);

			final PluginCommand cmd = constr.newInstance(command.label().toLowerCase(), plugin);
			cmd.setExecutor(command);
			cmd.setTabCompleter(command);
			if (command.aliases() != null)
				cmd.setAliases(Arrays.asList(command.aliases()));
			registerBukkitCommand(cmd);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}


	private static void registerBukkitCommand(PluginCommand cmd) {
		try {
			final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f.setAccessible(true);
			SimpleCommandMap scm;
			synchronized (scm = (SimpleCommandMap) f.get(Bukkit.getServer())) {

				scm.register(cmd.getName().toLowerCase(), cmd);

			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
			Texts.warn("Error registering Bukkit command alias");
			ex.printStackTrace();
		}
	}

}
