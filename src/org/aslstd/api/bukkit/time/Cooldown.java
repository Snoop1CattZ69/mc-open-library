package org.aslstd.api.bukkit.time;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cooldown<K> {

	public ConcurrentMap<K,Long> cooldowns = new ConcurrentHashMap<>();

	public Cooldown() {}

	public boolean isEnded(K key) {
		if (!cooldowns.containsKey(key)) return true;
		if (get(key) <= System.currentTimeMillis()) {
			cooldowns.remove(key);
			return true;
		}
		return false;
	}

	public void add(K key, long duration) {
		cooldowns.put(key, System.currentTimeMillis() + duration);
	}

	public void remove(K key) {
		if (!cooldowns.containsKey(key)) return;
		cooldowns.remove(key);
	}

	public long get(K key) {
		if (!cooldowns.containsKey(key)) return 0;
		return cooldowns.get(key);
	}


}
