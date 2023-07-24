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

import lombok.experimental.UtilityClass;

@UtilityClass
public class Commands {

	private Field cmdMap;
	private SimpleCommandMap scm;
	private Constructor<PluginCommand> pcC;

	{
		try {
			cmdMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			cmdMap.setAccessible(true);
			scm = (SimpleCommandMap) cmdMap.get(Bukkit.getServer());

			pcC = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			pcC.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException e) {
			e.printStackTrace();
			Texts.warn("Error while initializing variables, check error above");
			Texts.warn("Maybe this caused because you're not using the Paper or it's forks.");
		}
	}

	public final void registerCommand(Plugin plugin, CommandHandler command) {
		try {
			final PluginCommand cmd = pcC.newInstance(command.label().toLowerCase(), plugin);

			cmd.setExecutor(command);
			cmd.setTabCompleter(command);
			if (command.aliases() != null)
				cmd.setAliases(Arrays.asList(command.aliases()));
			registerBukkitCommand(cmd);
		} catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public final void unregisterBukkitCommand(Plugin plugin, CommandHandler handler) {
		synchronized (scm) {
			Bukkit.getPluginCommand(handler.label()).unregister(scm);
		}
	}

	private void registerBukkitCommand(PluginCommand cmd) {
		synchronized (scm) {
			scm.register(cmd.getName().toLowerCase(), cmd);
		}
	}

}
