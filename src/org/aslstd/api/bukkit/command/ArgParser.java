package org.aslstd.api.bukkit.command;

import java.util.function.Predicate;

import org.aslstd.api.bukkit.entity.util.EntityUtil;
import org.aslstd.api.bukkit.value.util.NumUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ArgParser {

	private String[] args;

	public ArgParser(String[] args) {
		this.args = args;
	}

	public int length() {
		return args.length;
	}

	public @Nullable String arg(int index) {
		return args.length > index ? args[index] : null;
	}

	public @Nullable String argEqual(int index, @NotNull Predicate<String> filter) {
		final String arg = arg(index);
		return filter.test(arg) ? arg : null;
	}

	public @Nullable Player player(int index) {
		return EntityUtil.getOnlinePlayer(arg(index));
	}

	public @Nullable OfflinePlayer offline(int index) {
		return EntityUtil.getPlayer(arg(index));
	}

	public double doubleArg(int index) throws NumberFormatException {
		return Double.parseDouble(arg(index));
	}

	public double doubleOrZero(int index) {
		return NumUtil.parseDouble(arg(index));
	}

	public int intArg(int index) throws NumberFormatException {
		return Integer.parseInt(arg(index));
	}

	public int intOrZero(int index) {
		return NumUtil.parseInteger(arg(index));
	}

	public long longArg(int index) throws NumberFormatException {
		return Long.parseLong(arg(index));
	}

	public long longOrZero(int index) {
		return NumUtil.parseLong(arg(index));
	}

	public @Nullable Boolean bool(int index) {
		final String arg = argEqual(index, s -> s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"));
		return arg == null ? null : Boolean.parseBoolean(arg);
	}

}
