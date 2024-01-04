package org.aslstd.api.openlib.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import javax.annotation.Nullable;

public class WorkerService {
	private static boolean lock;

	protected ScheduledExecutorService milkyWay;
	protected ExecutorService andromeda;
	public boolean andromedaAvailable() {
		return andromeda != null;
	}

	private ThreadWorker[] workers;

	public WorkerService(int poolSize) {
		if (lock)
			throw new SecurityException("You can't create more than one worker service.");
		milkyWay = Executors.newSingleThreadScheduledExecutor(new WorkerFactory("milkyWay"));
		if (poolSize > 1)
			andromeda = Executors.newFixedThreadPool(poolSize-1, new WorkerFactory("andromeda"));
		workers = new ThreadWorker[poolSize-1];
		for (int i = 0 ; i < workers.length ; i++)
			workers[i] = new ThreadWorker();
		lock = true;
	}

	public @Nullable CompletableFuture<ThreadWorker> getWorker() {
		return CompletableFuture.supplyAsync(() -> {
			ThreadWorker availableWorker = null;
			int i = 0;
			while (availableWorker == null) {
				if (i == workers.length) i = 0;

				if (workers[i].isIdle()) availableWorker = workers[i];
			}
			return availableWorker;
		});
	}

	public @Nullable <V> ScheduledFuture<V> scheduleTask(Callable<V> task) {
		return scheduleDelayedTask(task, 0);
	}

	public @Nullable <V> ScheduledFuture<V> scheduleDelayedTask(Callable<V> task, long millis) {
		if (task == null) return null;
		return milkyWay.schedule(task, millis < 0 ? 0 : millis, TimeUnit.MILLISECONDS);
	}

	public @Nullable ScheduledFuture<?> scheduleRepetitive(Runnable task, long delay, long period) {
		if (task == null) return null;
		return milkyWay.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
	}

	public @Nullable ScheduledFuture<?> schedule(Runnable task) {
		return scheduleDelayed(task, 0);
	}

	public @Nullable ScheduledFuture<?> scheduleDelayed(Runnable task, long millis) {
		if (task == null) return null;
		return milkyWay.schedule(task, millis < 0 ? 0 : millis, TimeUnit.MILLISECONDS);
	}

	public @Nullable <T> CompletableFuture<T> submitTask(Supplier<T> task) {
		if (task == null) return null;
		return CompletableFuture.supplyAsync(task, andromeda);
	}

	public @Nullable CompletableFuture<Void> execute(Runnable task) {
		if (task == null) return null;
		return CompletableFuture.runAsync(task, andromeda);
	}

	public void shutdown() {
		if (milkyWay != null && !milkyWay.isShutdown()) milkyWay.shutdown();
		if (andromeda != null && !andromeda.isShutdown()) andromeda.shutdown();
	}

	static class WorkerFactory implements ThreadFactory {
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		WorkerFactory(String namePrefix) {
			final SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			this.namePrefix = "pool-ejcore-" + namePrefix + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
			if (t.isDaemon()) t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}

}
