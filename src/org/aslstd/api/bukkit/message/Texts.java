package org.aslstd.api.bukkit.message;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aslstd.api.bukkit.entity.pick.Pick;
import org.aslstd.api.bukkit.file.yaml.Yaml;
import org.aslstd.api.bukkit.settings.Settings;
import org.aslstd.api.openlib.player.OPlayer;
import org.aslstd.core.OpenLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Setter;
import lombok.experimental.UtilityClass;

/**
 * <p>EText class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@SuppressWarnings("deprecation")
@UtilityClass
public class Texts {

	/** Constant <code>enableConsoleColoring=true</code> */
	public boolean enableConsoleColoring = true;

	/** Constant <code>COLOR_CHAR='\u00A7'</code> */
	public final char COLOR_CHAR = '\u00A7';

	/** Constant <code>lineBreak="&amp;5#################################"{trunked}</code> */
	public final String lineBreak = "&5######################################################################";
	/** Constant <code>halfLineBreak="&amp;5#################################"{trunked}</code> */
	public final String halfLineBreak = "&5###################################";

	public final String prefix = "MOL";

	@Setter private boolean debug = false;
	@Setter private boolean consoleFeedback = true;

	/**
	 * <p>warn.</p>
	 *
	 * @param msg a {@link String} object
	 */
	public void warn(String msg) { warn(msg, prefix); }

	/**
	 * <p>fine.</p>
	 *
	 * @param msg a {@link String} object
	 */
	public void fine(String msg) { fine(msg, prefix); }

	/**
	 * <p>debug.</p>
	 *
	 * @param msg a {@link String} object
	 */
	public void debug(String msg) { debug(msg, prefix); }

	/**
	 * <p>warn.</p>
	 *
	 * @param msg a {@link String} object
	 * @param prefix a {@link String} object
	 */
	public void warn(String msg, String prefix) { send("&5[&2"+ prefix +"&5]&f: #cf083d> " + msg); }

	/**
	 * <p>fine.</p>
	 *
	 * @param msg a {@link String} object
	 * @param prefix a {@link String} object
	 */
	public void fine(String msg, String prefix) { send("&5[&2"+ prefix +"&5]&f: &3> #28a2c7" + msg); }

	/**
	 * <p>debug.</p>
	 *
	 * @param msg a {@link String} object
	 * @param prefix a {@link String} object
	 */
	public void debug(String msg, String prefix) { if (debug) sendRawWithNull("&5[&2"+ prefix +"&5]&f: &3> #ccc737", msg); }

	/** Constant <code>df</code> */
	public DecimalFormat df;

	public void dumpFile(Settings<?> settings, JavaPlugin plugin) {
		Dumper.dump(settings, plugin);
	}

	public void dumpConsole(Settings<?> settings) {
		Dumper.dump(settings, null);
	}

	public void dump(Player p) {
		final OPlayer pl = Pick.of(p);
		dumpFile(pl.options().data(), OpenLib.instance());
		dumpFile(pl.options().tempData(), OpenLib.instance());
		dumpFile(pl.options().tempStore(), OpenLib.instance());
	}

	static {
		df = new DecimalFormat();

		df.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("en", "US")));
		// Default decimal separator: '.'
		df.getDecimalFormatSymbols().setDecimalSeparator('.');
		// Default decimals will rounded to 2 digits
		df.applyPattern("0.0#");

		// Don't change this
		df.setNegativePrefix("-");
		df.setPositivePrefix("");

		df.setRoundingMode(RoundingMode.CEILING);
	}

	/**
	 * Round decimals to 2 digits after separator
	 *
	 * @param value a double
	 * @return a {@link String} object
	 */
	public String format(double value) { return df.format(value); }

	/**
	 * Send raw message to console.
	 *
	 * @param msg a {@link java.lang.Object} object
	 */
	public void sendRaw(Object msg) {
		if (msg != null)
			Bukkit.getConsoleSender().sendMessage(msg.toString());
	}

	public void sendRawWithNull(String suff, Object msg) {
		Bukkit.getConsoleSender().sendMessage(c(msg == null ? suff + "null" : suff + msg.toString()));
	}

	/**
	 * Send array with raw messages to console
	 * {@link org.aslstd.api.bukkit.message.Texts#sendRaw(Object)}
	 *
	 * @param msg an array of {@link java.lang.Object} objects
	 */
	public void send(Object[] msg) { for(final Object obj : msg) sendRaw(obj); }

	/**
	 * Send message to CommandSender (Player or Console)
	 *
	 * @param receiver can be {@link org.bukkit.entity.Player} or {@link org.bukkit.command.ConsoleCommandSender}
	 * @param msg a {@link String} object
	 */
	public void send(Object receiver, String msg) {
		send(receiver,msg,true);
	}

	public void send(Object receiver, String msg, boolean colored) {
		if (receiver instanceof Player)
			((Player)receiver).sendMessage(colored ? c(msg) : e(msg));
		if (receiver instanceof ConsoleCommandSender)
			if (consoleFeedback)
				sendRaw(colored ? c(msg) : e(msg));
	}

	/**
	 * Send formatted message to console
	 *
	 * @param msg a {@link String} object
	 */
	public void send(String msg) {
		sendRaw(enableConsoleColoring ? c(msg) : e(msg));
	}

	/**
	 * Send line with 64 '#' red chars
	 */
	public void sendLB() { send(lineBreak); }

	/**
	 * Send line with 32 '#' red chars
	 */
	public void sendHLB() { send(halfLineBreak); }

	/**
	 * Colorizes all "&amp;" and hex colors
	 *
	 * @param msg a {@link String} object
	 * @return a {@link String} object
	 */
	public String c(String msg) {
		return ChatColor.translateAlternateColorCodes('&', translateHexColorCodes(msg));
	}

	/**
	 * Strips all '§' color codes
	 *
	 * @param msg a {@link String} object
	 * @return a {@link String} object
	 */
	public String s(String msg) { return ChatColor.stripColor(msg); }

	/**
	 * Erases all '&amp;' and '§amp;' color codes
	 *
	 * @param msg a {@link String} object
	 * @return a {@link String} object
	 */
	public String e(String msg) { return s(c(msg)); }

	/**
	 * Trims first argument and return all others
	 *
	 * @param args an array of {@link String} objects
	 * @return an array of {@link String} objects
	 */
	public String[] trimArgs(String[] args) {
		if (args.length == 0) return new String[0];
		final String[] ret = new String[args.length - 1];
		System.arraycopy(args, 1, ret, 0, args.length - 1);
		return ret;
	}

	private String translateHexColorCodes(String message) {
		final Pattern hexPattern = Pattern.compile("\\#([A-Fa-f0-9]{6})");
		final Matcher matcher = hexPattern.matcher(message);
		final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

		while (matcher.find())
		{
			final String group = matcher.group(1);
			matcher.appendReplacement(buffer, COLOR_CHAR + "x"
					+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
					+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
					+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
					);
		}
		return matcher.appendTail(buffer).toString();
	}

	/**
	 * <p>getSpaced.</p>
	 *
	 * @param args a {@link java.lang.Object} object
	 * @return a {@link String} object
	 */
	public String getSpaced(Object... args) {
		final StringBuffer buff = new StringBuffer();

		for (int i = 0 ; i < args.length ; i++) {

			if (args[i] instanceof Double) {
				args[i] = (int) Math.floor((Double)args[i]);
			}

			if (args[i] != null)
				buff.append(args[i] + (i != args.length-1 ? " " : ""));
		}

		return buff.toString();
	}

	@UtilityClass
	class Dumper {
		void dump(Settings<?> settings, JavaPlugin plugin) {
			if (plugin == null)
				for (final Map.Entry<String, ?> entry : settings.settings.entrySet())
					Texts.warn(String.valueOf(entry.getKey()) + ": &a" + entry.getValue());
			else {
				final Yaml dump = new Yaml(new File(plugin.getDataFolder() + "/dump." + System.currentTimeMillis() + "." + settings.toString() + ".yml"));

				for (final Entry<String,?> entry : settings.settings.entrySet())
					if (entry.getKey() != null || !entry.getKey().equalsIgnoreCase(""))
						dump.set(entry.getKey(), entry.getValue());
			}
		}
	}

}
