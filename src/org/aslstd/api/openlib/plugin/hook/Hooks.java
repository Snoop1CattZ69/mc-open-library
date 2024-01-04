package org.aslstd.api.openlib.plugin.hook;

import org.bukkit.Bukkit;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class Hooks {

	@Getter private static final boolean placeholderapi = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");


}
