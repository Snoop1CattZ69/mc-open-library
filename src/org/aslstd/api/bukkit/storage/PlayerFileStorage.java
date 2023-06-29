package org.aslstd.api.bukkit.storage;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.openlib.collection.Stash;
import org.aslstd.api.openlib.util.Obj;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;

public class PlayerFileStorage {

	@Getter private static final ConcurrentMap<JavaPlugin, PlayerFileStorage> databases = new ConcurrentHashMap<>();


	public static @NotNull PlayerFileStorage createDatabase(@NotNull JavaPlugin plugin) {
		Obj.checkNull(plugin);

		if (databases.containsKey(plugin))
			return databases.get(plugin);
		else {
			final PlayerFileStorage db = new PlayerFileStorage(plugin);
			databases.put(plugin, db);
			return db;
		}
	}

	private JavaPlugin plugin;
	private Stash<UUID, Yaml> database;

	private PlayerFileStorage(JavaPlugin plugin) {
		this.plugin = plugin;
		database = Stash.of(u -> Yaml.of("players/" + u + ".yml", plugin));
		initDatabase();
	}

	private void initDatabase() {
		final File folder = new File(plugin.getDataFolder() + "/players");
		if (!folder.exists())
			folder.mkdirs();

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
