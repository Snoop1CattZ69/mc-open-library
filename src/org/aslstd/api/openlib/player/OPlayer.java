package org.aslstd.api.openlib.player;

import java.util.UUID;

import org.aslstd.api.bukkit.entity.pick.UPlayer;
import org.aslstd.api.bukkit.equip.EquipInventory;
import org.aslstd.api.bukkit.file.yaml.Yaml;
import org.aslstd.api.bukkit.storage.PlayerFileStorage;
import org.aslstd.api.openlib.collection.Stash;
import org.aslstd.core.OpenLib;
import org.bukkit.Bukkit;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public final class OPlayer extends UPlayer {

	@Getter private static Stash<UUID, OPlayer> stash = Stash.of(OPlayer::new, e -> e.options().save());

	@Getter private Options options;

	@Getter private EquipInventory equip;
	@Getter private Yaml dataFile;

	public OPlayer(UUID uid) {
		super(Bukkit.getPlayer(uid));
		dataFile = PlayerFileStorage.getDatabases().get(OpenLib.instance()).getPlayerFile(player);
		options = new Options(this);
		equip = new EquipInventory();
	}

}

