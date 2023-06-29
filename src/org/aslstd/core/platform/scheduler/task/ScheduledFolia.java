package org.aslstd.core.platform.scheduler.task;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

public class ScheduledFolia implements Scheduled {

	private ScheduledTask task;

	public ScheduledFolia(@NotNull ScheduledTask task) {
		this.task = task;
	}

	@Override
	public Plugin getOwner() {
		return task.getOwningPlugin();
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
