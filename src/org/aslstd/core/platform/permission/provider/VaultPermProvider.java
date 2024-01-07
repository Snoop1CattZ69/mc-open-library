package org.aslstd.core.platform.permission.provider;

import org.aslstd.core.platform.permission.PermProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

public class VaultPermProvider extends PermProvider {

	private static Permission perm;

	public VaultPermProvider(JavaPlugin plugin, Permission provider) {
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
