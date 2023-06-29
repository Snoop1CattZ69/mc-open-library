package org.aslstd.api.bukkit.command.impl;

import java.util.ArrayList;
import java.util.List;

import org.aslstd.api.bukkit.command.OCommand;
import org.aslstd.api.bukkit.message.Texts;

class HelpCommand extends CommandNode {

	public HelpCommand(CommandHandler handler){
		super(handler, "help", 0, (s,args) -> {
			Texts.send(s, "&c»------>&5[&6" + handler.plugin.getName() +"&5&l]");
			final List<OCommand> commands = new ArrayList<>(handler.getRegisteredCommands());
			commands.add(handler.defaultCommand());
			for (final OCommand command : commands)
				if (command.testConditions(s))
					Texts.send(s,
							"&6" + command.usage() +
							" - &2" + command.description() +
							(s.isOp() || s.hasPermission("*") ? " &f- &5" + command.permission() : ""));
			Texts.send(s, "&c»------>&5[&6" + handler.plugin.getName() +"&5&l]");
			return null;
		});
	}

}
