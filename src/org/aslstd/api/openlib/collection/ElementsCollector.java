package org.aslstd.api.openlib.collection;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Nonnull;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.openlib.lambda.PrConsumer;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ForwardingMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class ElementsCollector<V> extends ForwardingMap<String, V> {

	@Getter @Setter(value = AccessLevel.PROTECTED) private ConcurrentMap<String,V> map;
	@Getter @Setter private File rootFolder;

	@Getter private Constructor<?> constructor;
	private List<String> defaults;
	private PrConsumer<V> func;
	private JavaPlugin plugin;

	protected ElementsCollector(ConcurrentMap<String, V> map, Class<V> clazz) {
		this.map = map;

		try {
			constructor = clazz.getConstructor(String.class, Yaml.class);
		} catch (NoSuchMethodException | SecurityException e) {
			Texts.warn("Constructor " + clazz.getName() + "(String, Yaml) was not found");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void collect() {
		if (rootFolder == null) {
			Texts.warn("rootFolder is null for ElementsStorage, please use rootFolder(File) method before collect()");
			return;
		}

		if (!rootFolder.exists()) {
			rootFolder.mkdirs();
			if (defaults != null)
				defaults.stream().filter(Objects::nonNull).forEach( path -> Yaml.exportFile(path, plugin, rootFolder) );
		}

		final List<File> files = new ArrayList<>(Arrays.asList(rootFolder.listFiles()));

		while(!files.isEmpty()) {
			final File file = files.remove(0);
			if (file != null && file.isDirectory() && file.listFiles() != null)
				files.addAll(Arrays.asList(file.listFiles()));
			else {
				if (!Yaml.getFileExtension(file).equalsIgnoreCase("yml")) continue;
				final Yaml yml = new Yaml(file);

				for (String keyName : yml.getKeys(false)) {
					keyName = keyName.toLowerCase();
					try {
						final V obj = (V)constructor.newInstance(keyName, yml);

						if (func != null) {
							if (func.accept(obj))
								map.put(keyName, obj);
							else
								continue;
						}

						map.put(keyName, obj);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public ElementsCollector<V> generateDefaults(@Nonnull List<String> paths, JavaPlugin container) {
		this.defaults = paths;
		this.plugin = container;
		return this;
	}

	public ElementsCollector<V> executeForObject(PrConsumer<V> func) {
		this.func = func;
		return this;
	}

	@Override
	protected Map<String,V> delegate() { return map; }

	public static <V> ElementsCollector<V> of(@Nonnull Class<V> clazz) {
		return new ElementsCollector<>(new ConcurrentHashMap<String, V>(), clazz);
	}
}
