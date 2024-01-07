package org.aslstd.api.bukkit.settings.impl;

import java.util.List;
import java.util.Map;

import org.aslstd.api.bukkit.settings.Settings;

public class DataSettings extends Settings<Object> implements Cloneable {

	public static final DataSettings of(FileSettings data) {
		final FileSettings clone = new FileSettings();
		clone.importFromSettings(data);

		final DataSettings result = new DataSettings();

		final Map<String,List<String>> arrays = clone.exportArrays();
		arrays.entrySet().forEach(e -> {
			result.setValue(e.getKey(), e.getValue());
			clone.removeArray(e.getKey());
		});

		clone.getKeys().forEach(e -> result.setValue(e.getKey(), e.getValue()));

		return result;
	}

	// TODO make toFileSettings with object serialization.

	@Override
	protected DataSettings clone() {
		final DataSettings result = new DataSettings();

		settings.entrySet().forEach(e -> result.setValue(e.getKey(), e.getValue()));

		return result;
	}

}
