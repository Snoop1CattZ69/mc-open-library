package org.aslstd.core.platform.scheduler.provider;

import org.aslstd.core.platform.scheduler.SchedulerProvider;
import org.aslstd.core.platform.scheduler.task.Scheduled;
import org.aslstd.core.platform.scheduler.task.ScheduledBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("deprecation")
public class BukkitSchedulerProvider implements SchedulerProvider {

	@Override
	public Scheduled schedule(Plugin plugin, Runnable task) {
		return new ScheduledBukkit(Bukkit.getScheduler().runTask(plugin, task));
	}

	@Override
	public Scheduled scheduleLater(Plugin plugin, Runnable task, long delay) {
		return new ScheduledBukkit(Bukkit.getScheduler().runTaskLater(plugin, task, delay));
	}

	@Override
	public Scheduled scheduleRepeat(Plugin plugin, Runnable task, long delay, long period) {
		return new ScheduledBukkit(Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period));
	}

	@Override
	public Scheduled async(Plugin plugin, Runnable task) {
		return new ScheduledBukkit(Bukkit.getScheduler().runTaskAsynchronously(plugin, task));
	}

	@Override
	public Scheduled asyncLater(Plugin plugin, Runnable task, long delay) {
		return new ScheduledBukkit(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay));
	}

	@Override
	public Scheduled asyncRepeat(Plugin plugin, Runnable task, long delay, long period) {
		return new ScheduledBukkit(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period));
	}


}
