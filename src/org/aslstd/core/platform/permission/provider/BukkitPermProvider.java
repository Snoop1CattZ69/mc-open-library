package org.aslstd.core.platform.permission.provider;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.aslstd.core.platform.permission.PermProvider;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPermProvider extends PermProvider {
	public BukkitPermProvider(JavaPlugin plugin) { super(plugin); }

	private static ConcurrentMap<UUID, PermissionAttachment> attachments = new ConcurrentHashMap<>();

	@Override
	public void add(Player player, String permission) {

		final PermissionAttachment perm = getPermission(player);

		boolean allowed = true;
		if (permission.startsWith("-")) {
			permission = permission.substring(1);
			allowed = false;
		}

		if (!allowed)
			perm.unsetPermission(permission);
		else
			perm.setPermission(permission, allowed);

	}

	@Override
	public void remove(Player player, String permission) {
		final PermissionAttachment perm = getPermission(player);

		if (permission.startsWith("-"))
			permission = permission.substring(1);

		perm.unsetPermission(permission);
	}

	@Override
	public boolean has(Player player, String permission) {
		if (permission.startsWith("-"))
			permission = permission.substring(1);

		return player.hasPermission(permission);
	}

	private PermissionAttachment getPermission(Player player) {
		if (attachments.containsKey(player.getUniqueId()))
			return attachments.get(player.getUniqueId());
		else {
			final PermissionAttachment perm = createPermission(player);
			attachments.put(player.getUniqueId(), perm);
			return perm;
		}
	}

	private PermissionAttachment createPermission(Player player) {
		return player.addAttachment(plugin);
	}

}
