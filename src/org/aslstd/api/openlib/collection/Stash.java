package org.aslstd.api.openlib.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.common.collect.ForwardingMap;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class Stash<K,V> extends ForwardingMap<K,V> {

	private ConcurrentMap<K,V> map;
	private Function<K,V> func;

	@Setter private Consumer<V> removeFunc;

	private Stash(ConcurrentMap<K, V> map, Function<K, V> func) {
		this.map = map;
		this.func = func;
	}

	@Override
	protected Map<K,V> delegate() { return this.map; }

	public static <K,V> Stash<K,V> of(ConcurrentMap<K,V> map) {
		return new Stash<>(map, null);
	}

	public static <K,V> Stash<K,V> of(Function<K,V> func) {
		return new Stash<>(new ConcurrentHashMap<K,V>(),func);
	}

	public static <K,V> Stash<K,V> of(Function<K,V> func, Consumer<V> removeFunc) {
		return new Stash<>(new ConcurrentHashMap<K,V>(),func).removeFunc(removeFunc);
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		final V val = map.get(key);
		if (val != null)
			return val;
		return map.computeIfAbsent((K)key, func);
	}

	@Override
	public V remove(Object key) {
		final V val = map.remove(key);
		if (val != null && removeFunc != null)
			removeFunc.accept(val);
		return val;
	}

	@Override
	public void clear() {
		map.values().forEach(removeFunc);
		map.clear();
	}

}
