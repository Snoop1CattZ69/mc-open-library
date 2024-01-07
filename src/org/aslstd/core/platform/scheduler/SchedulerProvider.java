package org.aslstd.core.platform.scheduler;

import org.aslstd.core.platform.scheduler.task.Scheduled;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public interface SchedulerProvider {

	Scheduled schedule(Plugin plugin, Runnable task);

	default Scheduled schedule(Plugin plugin, Entity entity, Runnable task){
		return schedule(plugin, task);
	}

	default Scheduled schedule(Plugin plugin, Location location, Runnable task){
		return schedule(plugin, task);
	}

	Scheduled scheduleLater(Plugin plugin, Runnable task, long delay);

	default Scheduled scheduleLater(Plugin plugin, Entity entity, Runnable task, long delay){
		return scheduleLater(plugin, task, delay);
	}

	default Scheduled scheduleLater(Plugin plugin, Location location, Runnable task, long delay){
		return scheduleLater(plugin, task, delay);
	}

	Scheduled scheduleRepeat(Plugin plugin, Runnable task, long delay, long period);

	default Scheduled scheduleRepeat(Plugin plugin, Entity entity, Runnable task, long delay, long period){
		return scheduleRepeat(plugin, task, delay, period);
	}

	default Scheduled scheduleRepeat(Plugin plugin, Location location, Runnable task, long delay, long period) {
		return scheduleRepeat(plugin, task, delay, period);
	}

	Scheduled async(Plugin plugin, Runnable task);

	Scheduled asyncLater(Plugin plugin, Runnable task, long delay);

	Scheduled asyncRepeat(Plugin plugin, Runnable task, long delay, long period);

}
