package org.aslstd.api.openlib.attributes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.NamespacedKey;

public class AttrManager {
	private static final Map<NamespacedKey, AttrBase> attributes = new ConcurrentHashMap<>();

	public static final Collection<AttrBase> getAttributes() { return attributes.values(); }

	private static List<AttrBase> sortedList = new ArrayList<>();

	private static int size = -1;

	public static final  List<AttrBase> getSortedList() {
		if (size == attributes.size()) { return sortedList; }

		final ArrayList<AttrBase> list = new ArrayList<>(attributes.values());

		list.sort(Comparator.comparingInt(AttrBase::priority));

		sortedList = list;
		size = attributes.size();
		return sortedList;
	}

	public static final void register(AttrBase attr) {
		if (attr != null && !attributes.containsKey(attr.getKey())) {
			attr.uniquePosition(attributes.size());
			attributes.put(attr.getKey(), attr);
		}
	}

	public static final void register() {
		if (!attributes.isEmpty()) { return; }
		int pos = 0;
		for (final Field f : AttrManager.class.getFields()) {
			if (!f.trySetAccessible()) continue;

			if (AttrBase.class.isAssignableFrom(f.getType())) {
				try {
					register((AttrBase) f.get(null));
					((AttrBase) f.get(null)).priority(pos++);
				} catch (final Exception e) { e.printStackTrace(); }
			}
		}
	}

	public static final AttrBase getByKey(NamespacedKey key) {
		if (attributes.containsKey(key))
			return attributes.get(key);

		return null;
	}

	public static final void reloadAttributes() {
		for (final AttrBase attr : attributes.values()) {
			attr.initCustomSettings();

			attr.getVisualName();
			attr.getCostValue();
			attr.initializeBasicValues();
		}
	}

}
