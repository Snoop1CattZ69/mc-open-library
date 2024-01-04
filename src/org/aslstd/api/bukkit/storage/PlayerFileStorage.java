package org.aslstd.api.bukkit.storage;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.aslstd.api.bukkit.file.yaml.Yaml;
import org.aslstd.api.openlib.collection.Stash;
import org.aslstd.api.openlib.util.Obj;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class PlayerFileStorage {

	@Getter private static final ConcurrentMap<JavaPlugin, PlayerFileStorage> databases = new ConcurrentHashMap<>();

	public static @NotNull PlayerFileStorage createDatabase(@NotNull JavaPlugin plugin) {
		return customDatabase(plugin, null);
	}

	public static @NotNull PlayerFileStorage customDatabase(@NotNull JavaPlugin plugin, @Nullable File customFolder) {
		Obj.checkNull(plugin);

		if (databases.containsKey(plugin))
			return databases.get(plugin);
		else {
			PlayerFileStorage db;
			if (customFolder == null)
				db = new PlayerFileStorage(plugin);
			else
				db = new PlayerFileStorage(plugin, customFolder);
			databases.put(plugin, db);
			return db;
		}
	}

	private JavaPlugin plugin;
	private Stash<UUID, Yaml> database;
	private File folder;

	private PlayerFileStorage(JavaPlugin plugin) {
		this(plugin, new File(plugin.getDataFolder() + "/players"));
	}

	private PlayerFileStorage(JavaPlugin plugin, File folder) {
		this.plugin = plugin;
		this.folder = folder;
		database = Stash.of(u -> new Yaml(folder + "/" + u.toString() + ".yml"));
		initDatabase();
	}

	private void initDatabase() {
		if (!folder.exists()) folder.mkdirs();

		for (final File file : folder.listFiles()) {
			if (file.isFile() && Yaml.getFileExtension(file).equalsIgnoreCase("yml")) {
				final Yaml pfile = new Yaml(file, plugin, null);
				final String name = file.getName().substring(0, file.getName().lastIndexOf("."));
				UUID uid;
				try {
					uid = UUID.fromString(name);
				} catch(final Exception e) { continue; }
				if (uid == null) continue;

				database.put(uid, pfile);
			}
		}
	}

	public @NotNull Yaml getPlayerFile(@NotNull OfflinePlayer player) {
		return database.get(player.getUniqueId());
	}

}
