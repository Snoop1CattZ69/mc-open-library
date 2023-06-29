package org.aslstd.api.openlib.worker;

import java.util.function.Supplier;

import lombok.Getter;

public class WorkerTask<V> {

	@Getter private volatile boolean finished = false;

	@Getter private Supplier<V> task;

	@Getter private V result;

	public WorkerTask(Supplier<V> task) {
		this.task = task;
	}

	public void complete(V result) {
		this.finished = true;
	}

}
