package org.aslstd.core.platform.scheduler.provider;

import java.util.concurrent.TimeUnit;

import org.aslstd.core.platform.scheduler.task.Scheduled;
import org.aslstd.core.platform.scheduler.task.ScheduledFolia;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;

public class FoliaSchedulerProvider implements SchedulerProvider {

	private RegionScheduler region = Bukkit.getServer().getRegionScheduler();

	private final GlobalRegionScheduler globalRegion = Bukkit.getServer().getGlobalRegionScheduler();

	private final AsyncScheduler asyncScheduler = Bukkit.getServer().getAsyncScheduler();

	@Override
	public Scheduled schedule(Plugin plugin, Runnable task) {
		return new ScheduledFolia(globalRegion.run(plugin, ignore -> task.run()));
	}

	@Override
	public Scheduled schedule(Plugin plugin, Entity entity, Runnable task) {
		return new ScheduledFolia(entity.getScheduler().run(plugin, ignore -> task.run(), null));
	}

	@Override
	public Scheduled schedule(Plugin plugin, Location location, Runnable task) {
		return new ScheduledFolia(region.run(plugin, location, ignore -> task.run()));
	}

	@Override
	public Scheduled scheduleLater(Plugin plugin, Runnable task, long delay) {
		return new ScheduledFolia(globalRegion.runDelayed(plugin, ignore -> task.run(), delay));
	}

	@Override
	public Scheduled scheduleLater(Plugin plugin, Entity entity, Runnable task, long delay) {
		return new ScheduledFolia(entity.getScheduler().runDelayed(plugin, ignore -> task.run(), null, delay));
	}

	@Override
	public Scheduled scheduleLater(Plugin plugin, Location location, Runnable task, long delay) {
		return new ScheduledFolia(region.runDelayed(plugin, location, ignore -> task.run(), delay));
	}

	@Override
	public Scheduled scheduleRepeat(Plugin plugin, Runnable task, long delay, long period) {
		return new ScheduledFolia(globalRegion.runAtFixedRate(plugin, ignore -> task.run(), delay, period));
	}

	@Override
	public Scheduled scheduleRepeat(Plugin plugin, Entity entity, Runnable task, long delay, long period) {
		return new ScheduledFolia(entity.getScheduler().runAtFixedRate(plugin, ignore -> task.run(), null, delay, period));
	}

	@Override
	public Scheduled scheduleRepeat(Plugin plugin, Location location, Runnable task, long delay, long period) {
		return new ScheduledFolia(region.runAtFixedRate(plugin, location, ignore -> task.run(), delay, period));
	}

	@Override
	public Scheduled async(Plugin plugin, Runnable task) {
		return new ScheduledFolia(asyncScheduler.runNow(plugin, ignore -> task.run()));
	}

	@Override
	public Scheduled asyncLater(Plugin plugin, Runnable task, long delay) {
		return new ScheduledFolia(asyncScheduler.runDelayed(plugin, ignore -> task.run(), delay*50, TimeUnit.MILLISECONDS));
	}

	@Override
	public Scheduled asyncRepeat(Plugin plugin, Runnable task, long delay, long period) {
		return new ScheduledFolia(asyncScheduler.runAtFixedRate(plugin, ignore -> task.run(), delay*50, period*50, TimeUnit.MILLISECONDS));
	}

}
