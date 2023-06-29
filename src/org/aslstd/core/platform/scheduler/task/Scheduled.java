package org.aslstd.core.platform.scheduler.task;

import org.bukkit.plugin.Plugin;

public interface Scheduled {

	Plugin getOwner();

	void cancel();

	boolean isCancelled();

}
