package org.aslstd.api.bukkit.command.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aslstd.api.bukkit.command.SenderType;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.openlib.plugin.OpenPlugin;

class ReloadCommand extends CommandNode {
	private static Method reloadPlugin;

	static {
		try {
			reloadPlugin = OpenPlugin.class.getDeclaredMethod("reloadPlugin");
		} catch (final NoSuchMethodException | SecurityException e) {
			Texts.warn("OpenPlugin class not finded, maybe you has installed an built-in version of this API. Default reload command cannot be initialised.");
		}
	}

	public ReloadCommand(CommandHandler handler) {
		super(handler, "reload", 0, (s,args) -> {
			if (OpenPlugin.class.isAssignableFrom(handler.plugin.getClass())) {
				try {
					reloadPlugin.invoke(handler.plugin);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					Texts.warn("Something went wrong while executing reloadPlugin method for plugin " + handler.plugin.getName() + ", maybe you has issues with OpenLib below");
					e.printStackTrace();
				}
			} else
				Texts.warn("Plugin " + handler.plugin.getName() + " tried to use built-in OpenLib reload command, but not extends from OpenPlugin class");

			return null;
		});
		senderType = SenderType.ALL;
	}

}
