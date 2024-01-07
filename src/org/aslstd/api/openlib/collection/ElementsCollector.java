package org.aslstd.api.openlib.collection;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.aslstd.api.bukkit.file.yaml.Yaml;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.openlib.lambda.PrdConsumer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ForwardingMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class ElementsCollector<V> extends ForwardingMap<String, V> {

	@Getter @Setter(value = AccessLevel.PROTECTED) private ConcurrentMap<String,V> map;
	@Getter @Setter private File rootFolder;

	@Getter private Constructor<?> constructor;
	private List<String> defaults;
	private PrdConsumer<V> func;
	private PrdConsumer<Collection<V>> finalize;
	private JavaPlugin plugin;

	protected ElementsCollector(ConcurrentMap<String, V> map, Class<V> clazz) {
		try {
			constructor = clazz.getDeclaredConstructor(String.class, Yaml.class);
			constructor.setAccessible(true);
			this.map = map;
		} catch (NoSuchMethodException | SecurityException e) {
			Texts.warn("Constructor " + clazz.getName() + "(java.lang.String, org.aslstd.api.bukkit.yaml.Yaml) was not found");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@SneakyThrows(IllegalStateException.class)
	public void collect() {
		if (this.map == null) throw new IllegalStateException("ElementsCollector not initialized, check issues above");
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

		finalize.accept(Collections.unmodifiableCollection(map.values()));
	}

	@SneakyThrows(IllegalStateException.class)
	public ElementsCollector<V> generateDefaults(@NotNull List<String> paths, JavaPlugin container) {
		if (this.map == null) throw new IllegalStateException("ElementsCollector not initialized, check issues above");
		this.defaults = paths;
		this.plugin = container;
		return this;
	}

	@SneakyThrows(IllegalStateException.class)
	public ElementsCollector<V> executeForObject(PrdConsumer<V> func) {
		if (this.map == null) throw new IllegalStateException("ElementsCollector not initialized, check issues above");
		this.func = func;
		return this;
	}

	@SneakyThrows(IllegalStateException.class)
	public ElementsCollector<V> executeFinally(PrdConsumer<Collection<V>> func) {
		if (this.map == null) throw new IllegalStateException("ElementsCollector not initialized, check issues above");
		this.finalize = func;
		return this;
	}

	@Override
	protected Map<String,V> delegate() { return map; }

	public static <V> ElementsCollector<V> of(@NotNull Class<V> clazz) {
		return new ElementsCollector<>(new ConcurrentHashMap<String, V>(), clazz);
	}
}
