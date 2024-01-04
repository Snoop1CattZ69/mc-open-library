package org.aslstd.core.task;

import java.util.Comparator;
import java.util.Set;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.openlib.plugin.OpenPlugin;
import org.aslstd.core.OpenLib;
import org.aslstd.core.listener.temp.CancelJoinBeforeFullLoading;
import org.aslstd.core.platform.scheduler.task.Scheduled;
import org.aslstd.core.service.Listeners;
import org.aslstd.core.update.CheckUpdates;

public class LoadOpenPlugins implements Runnable {

	private static boolean executed;

	public static final void execute(Set<OpenPlugin> plugins) {
		if (executed) return;
		final LoadOpenPlugins task = new LoadOpenPlugins(plugins);

		task.instance = OpenLib.scheduler().scheduleRepeat(OpenLib.instance(), task, 8L, 40L);
		executed = true;
	}

	private final Set<OpenPlugin> plugins;
	private Scheduled instance;

	private LoadOpenPlugins(Set<OpenPlugin> plugins) {
		this.plugins = plugins;
	}

	@SuppressWarnings("deprecation")
	@Override
	synchronized public void run() {
		final long bef = System.nanoTime();
		Texts.sendLB();

		for (final OpenPlugin plugin : plugins.stream().sorted(Comparator.comparingInt(OpenPlugin::getPriority)).toList()) {
			Texts.fine("&6Initialising " + plugin.getName() + " " + plugin.getDescription().getVersion());

			try {
				plugin.init();
			} catch (final Exception e) {
				Texts.warn("Something went wrong while loading " + plugin.getName());
				e.printStackTrace();
				plugins.remove(plugin);
				return;
			}

			CheckUpdates.registerOpenPlugin(plugin);
		}

		//CheckUpdates.Task.runTask();

		Listeners.register();

		final long aft = System.nanoTime();
		Texts.fine("&aAll OpenPlugins succesfuly loaded in " + Texts.format((aft-bef)/1e9) +" sec.");
		Texts.sendLB();

		CancelJoinBeforeFullLoading.unregister();
		instance.cancel();
	}
}

