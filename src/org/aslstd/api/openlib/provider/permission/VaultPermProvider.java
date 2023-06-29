package org.aslstd.api.openlib.provider.permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

public class VaultPermProvider extends PermProvider {

	private static Permission perm;

	VaultPermProvider(JavaPlugin plugin, Permission provider) {
		super(plugin);
		perm = provider;
	}

	@Override
	public void add(Player player, String permission) {
		perm.playerAdd(player, permission);
	}

	@Override
	public void remove(Player player, String permission) {
		perm.playerRemove(player, permission);
	}

	@Override
	public boolean has(Player player, String permission) {
		return perm.playerHas(player, permission);
	}

}
