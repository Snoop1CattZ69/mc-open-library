package org.aslstd.api.openlib.worker;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.aslstd.api.openlib.collection.CompletableSet;
import org.aslstd.core.OpenLib;

public class ThreadWorker {

	protected BlockingQueue<WorkerTask<?>> works = new LinkedBlockingQueue<>();

	private boolean locked = false;

	public ThreadWorker() {}

	public void queueTask(WorkerTask<?> task) {
		if (task == null) return;
		works.add(task);
	}

	public void queueTask(WorkerTask<?> task, boolean invokeImmediatly) {
		queueTask(task);
		if (invokeImmediatly)
			invokeAll();
	}

	public CompletableSet invokeAll() {
		final Set<CompletableFuture<?>> set = new LinkedHashSet<>();

		while (!works.isEmpty())
			set.add(invoke(works.poll()));

		return new CompletableSet(set);
	}

	public CompletableFuture<?> invoke(WorkerTask<?> work) {
		if (locked)
			return OpenLib.workers().submitTask(() -> { while(locked) {} return true; })
					.thenApply(b -> OpenLib.workers().submitTask(work.getTask()) );

		return OpenLib.workers().submitTask(work.getTask());
	}

	public void ping(long millis) {
		if (!works.isEmpty() ) {
			locked = true;
			OpenLib.workers().execute(() -> { try { TimeUnit.MILLISECONDS.sleep(millis); } catch (final InterruptedException e) {} pong(); } );
		}
	}

	public void pong() {
		if (!works.isEmpty())
			locked = false;
	}

	public boolean isIdle() {
		return works.isEmpty() && !locked;
	}



}
