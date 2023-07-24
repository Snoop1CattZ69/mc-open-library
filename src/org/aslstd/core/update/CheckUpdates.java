package org.aslstd.core.update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.value.util.NumUtil;
import org.aslstd.api.openlib.plugin.OpenPlugin;
import org.aslstd.core.OpenLib;
import org.aslstd.core.platform.scheduler.task.Scheduled;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;

import lombok.experimental.UtilityClass;

@SuppressWarnings("deprecation")
@UtilityClass
public class CheckUpdates {

	private List<OpenPlugin> openPlugins = new ArrayList<>();

	public void checkUpdates() {

		for (final OpenPlugin plugin : openPlugins) {

			getVersion(version -> {

				plugin.setLatestVersion(version);
				plugin.setLatestBuild(parseBuild(version, true));

			}, plugin);

		}

	}

	public void sendUpdateMessage(CommandSender p) {
		if (p instanceof ConsoleCommandSender)
			Texts.sendLB();

		final String prefix = ((p instanceof ConsoleCommandSender) ? "&5[&2MOL&5]&f: &3> " : "");

		if (p instanceof Player)
			Texts.send(p, "&c»------>&5 OpenLibrary Update Checker");

		for (final OpenPlugin plugin : openPlugins) {

			final boolean isUpToDate = plugin.getBuild() >= plugin.getLatestBuild();

			if (isUpToDate)
				Texts.send(p, prefix + " &2• " + plugin.getDescription().getFullName() + ": No new updates");
			else
				Texts.send(p, prefix + " &2• " + plugin.getDescription().getFullName() + ": New Update - " + plugin.getLatestVersion());
		}
		Texts.send(p, prefix + "&6You can download new versions here:");
		Texts.send(p, prefix + "&3https://www.spigotmc.org/resources/authors/115181/");

		if (p instanceof Player)
			Texts.send(p, "&c»------>&5 OpenLibrary Update Checker");

		if (p instanceof ConsoleCommandSender)
			Texts.sendLB();
	}

	private void getVersion(final Consumer<String> consumer, OpenPlugin plugin) {
		if (plugin.getResourceId() == -1)
			return;

		OpenLib.scheduler().async(plugin, () -> {
			try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + plugin.getResourceId()).openStream();
					Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext())
					consumer.accept(scanner.next());

			} catch (final IOException exception) {
				Texts.warn("Cannot look for updates (" + plugin.getName() + "): " + exception.getMessage());
			}
		});
	}

	public void registerOpenPlugin(OpenPlugin ejp) {

		final int build = parseBuild(ejp.getDescription().getVersion(), false);
		ejp.setBuild(build);

		if (ejp.getResourceId() == -1)
			return;

		openPlugins.add(ejp);

	}

	private String cleanVersion(String version, boolean fromSite) {
		return version.replaceAll("\\s", "").substring(0, (version.indexOf("[") == -1 ? version.length() : version.indexOf("[")))
				.substring(0, (version.indexOf("-") == -1 ? version.length() : version.indexOf("-")));
	}

	private int parseBuild(String version, boolean fromSite) {
		final String prepaired = cleanVersion(version, fromSite);

		return NumUtil.parseInteger(prepaired.substring(prepaired.lastIndexOf(".")+1));
	}

	public class Task implements Runnable {

		/** Constant <code>task</code> */
		protected Scheduled task = null;

		/**
		 * <p>runTask.</p>
		 */
		public void runTask() {
			if (task == null)
				task = OpenLib.scheduler().asyncRepeat(OpenLib.instance(), new Task(), 20L, 144000L);
		}

		/** {@inheritDoc} */
		@Override
		public void run() {
			checkUpdates();

			sendMessage();
		}

		private void sendMessage() {
			OpenLib.scheduler().asyncLater(OpenLib.instance(), () -> sendUpdateMessage(Bukkit.getConsoleSender()), 40L);
		}

	}
}
