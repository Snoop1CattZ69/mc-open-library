package org.aslstd.api.openlib.worker;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;

public class CompletableSet {

	@Getter public Set<CompletableFuture<?>> works;

	public CompletableSet(Set<CompletableFuture<?>> works) {
		this.works = ImmutableSet.copyOf(works);
	}

	public boolean allWorksCompleted() {
		for (final CompletableFuture<?> cf : works)
			if (!cf.isDone()) return false;
		return true;
	}

}
