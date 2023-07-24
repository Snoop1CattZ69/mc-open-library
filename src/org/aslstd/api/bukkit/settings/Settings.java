package org.aslstd.api.bukkit.settings;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Settings class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class Settings<T> {
	public ConcurrentMap<String, T> settings = new ConcurrentHashMap<>();

	/**
	 * <p>getSettingsSize.</p>
	 *
	 * @return a int
	 */
	public int getSettingsSize() {
		return this.settings.size();
	}

	/**
	 * <p>hasKey.</p>
	 *
	 * @param key a {@link String} object
	 * @return a boolean
	 */
	public boolean hasKey(String key) {
		for (final String k : this.settings.keySet()) {
			if (k.equals(key))
				return true;
		}
		return false;
	}

	/**
	 * <p>setValue.</p>
	 *
	 * @param key a {@link String} object
	 * @param value a T object
	 */
	public void setValue(String key, T value) {
		this.settings.put(key, value);
	}

	/**
	 * <p>getValue.</p>
	 *
	 * @param key a {@link String} object
	 * @return a T object
	 */
	public T getValue(String key) {
		return this.settings.get(key);
	}

	/**
	 * <p>hasValue.</p>
	 *
	 * @param key a {@link String} object
	 * @return a boolean
	 */
	public boolean hasValue(String key) {
		return (this.settings.containsKey(key) && this.settings.get(key) != null);
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param key a {@link String} object
	 */
	public T remove(String key) {
		if (hasKey(key))
			return this.settings.remove(key);
		return null;
	}

	/**
	 * <p>getKeys.</p>
	 *
	 * @return a {@link Set} object
	 */
	public Set<Map.Entry<String, T>> getKeys() {
		return this.settings.entrySet();
	}

	/**
	 * <p>removeKey.</p>
	 *
	 * @param keyPart a {@link String} object
	 */
	public void removeKey(String keyPart) {
		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey().contains(keyPart))
				remove(entry.getKey());
		}
	}

	/**
	 * <p>getKey.</p>
	 *
	 * @param keyPart a {@link String} object
	 * @return a {@link List} object
	 */
	public List<Map.Entry<String, T>> getKey(String keyPart) {
		final List<Map.Entry<String, T>> list = new LinkedList<>();
		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey().contains(keyPart))
				list.add(entry);
		}
		return list;
	}
}
