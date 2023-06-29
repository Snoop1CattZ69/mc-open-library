package org.aslstd.api.openlib.provider.permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;

public class LuckPermProvider extends PermProvider {

	private static LuckPerms luck;

	LuckPermProvider(JavaPlugin plugin, LuckPerms provider) {
		super(plugin);
		luck = provider;
	}

	@Override
	public void add(Player player, String permission) {
		luck.getUserManager().modifyUser(player.getUniqueId(), user -> {
			user.data().add(PermissionNode.builder(permission).build());
		});
	}

	public void addGroup(Player player, Group group) {
		luck.getUserManager().modifyUser(player.getUniqueId(), user -> {
			user.data().add(InheritanceNode.builder(group).build());
		});
	}

	@Override
	public void remove(Player player, String permission) {
		luck.getUserManager().modifyUser(player.getUniqueId(), user -> {
			user.data().remove(PermissionNode.builder(permission).build());
		});
	}

	public void removeGroup(Player player, Group group) {
		removeGroup(player, group.getName());
	}

	public void removeGroup(Player player, String groupName) {
		luck.getUserManager().modifyUser(player.getUniqueId(), user -> {
			if (hasGroup(player, groupName))
				user.data().remove(InheritanceNode.builder(groupName).build());
		});
	}

	@Override
	public boolean has(Player player, String permission) {
		final User user = luck.getUserManager().getUser(player.getUniqueId());

		if (user == null) return false;

		return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
	}

	public boolean hasGroup(Player player, Group group) {
		return hasGroup(player, group.getName());
	}

	public boolean hasGroup(Player player, String groupName) {
		final User user = luck.getUserManager().getUser(player.getUniqueId());
		if (user == null) return false;

		if (user.getPrimaryGroup().equalsIgnoreCase(groupName))
			return true;

		return user.getInheritedGroups(user.getQueryOptions())
				.stream().anyMatch(g -> g.getName().equalsIgnoreCase(groupName));
	}

}
