package org.aslstd.core.platform.scheduler.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class ScheduledBukkit implements Scheduled {

	private BukkitTask task;

	public ScheduledBukkit(@NotNull BukkitTask task) {
		this.task = task;
	}

	@Override
	public Plugin getOwner() {
		return task.getOwner();
	}

	@Override
	public void cancel() {
		task.cancel();
	}

	@Override
	public boolean isCancelled() {
		return task.isCancelled();
	}

}
