package org.aslstd.core.platform.permission;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.aslstd.api.openlib.util.Obj;
import org.aslstd.core.platform.permission.provider.BukkitPermProvider;
import org.aslstd.core.platform.permission.provider.LuckPermProvider;
import org.aslstd.core.platform.permission.provider.VaultPermProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;

import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.permission.Permission;

public abstract class PermProvider {

	private static PermProvider instance;

	@Internal
	public static void initialize(JavaPlugin initializer) {
		if (instance != null) return;

		if (Obj.classExist("net.luckperms.api.LuckPerms")) {
			final RegisteredServiceProvider<LuckPerms> provider = checkService(LuckPerms.class);
			if (provider != null) {
				instance = new LuckPermProvider(initializer, provider.getProvider());
				return;
			}
		}

		if (Obj.classExist("net.milkbowl.vault.permission.Permission")) {
			final RegisteredServiceProvider<Permission> provider = checkService(Permission.class);
			if (provider != null) {
				instance = new VaultPermProvider(initializer, provider.getProvider());
			}
		}
	}

	/**
	 * Requesting a currently registered provider<br>
	 * Do not try to create an instance of any listed providers, just use this method<br>
	 * and link PermProvider to your own variable<br><br>
	 *
	 * If you want to use only <i>LuckPermProvider or VaultPermProvider</i><br>
	 * just make an <i>instanceof</i> check before linking to your variable,<br>
	 *
	 * @param plugin - required only if currently defined provider is Bukkit.
	 * @return PermProvider - one of defined permission provider (Luck, Vault, Bukkit)
	 */
	public static PermProvider request(@Nullable JavaPlugin plugin) {
		if (instance == null)
			return new BukkitPermProvider(plugin);
		return instance;
	}

	private static <P> RegisteredServiceProvider<P> checkService(Class<P> clazz) {
		return Bukkit.getServicesManager().getRegistration(clazz);
	}

	protected JavaPlugin plugin;

	protected PermProvider(@NotNull JavaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Adds a temporary permission for player (while server will be restarted)
	 *
	 * @param player - that will receive permission
	 * @param permission
	 */
	public abstract void add(Player player, @NotNull String permission);

	/**
	 * Adds a list of temporary permissions for player (while server will be restarted)
	 *
	 * @param player - that will receive permission
	 * @param permissions - list of permissions
	 */
	public void add(Player player, @NotNull String... permissions) {
		Stream.of(permissions).forEach(permission -> add(player, permission));
	}

	/**
	 * Adds a list of temporary permissions for player (while server will be restarted)
	 *
	 * @param player - that will receive permission
	 * @param permissions - list of permissions
	 */
	public void add(@Nonnull Player player, @NotNull List<String> permissions) {
		ImmutableList.of(permissions).stream().forEach(permission -> add(player, permission) );
	}

	/**
	 * temporary removes permission for player (while server will be restarted)
	 *
	 * @param player - that will receive permission
	 * @param permission
	 */
	public abstract void remove(Player player, String permission);

	/**
	 * temporary removes a list of permissions for player (while server will be restarted)
	 *
	 * @param player - that will receive permission
	 * @param permissions - list of permissions
	 */
	public void remove(Player player, @NotNull String... permissions) {
		Stream.of(permissions).forEach(permission -> remove(player, permission));
	}

	/**
	 * temporary removes a list of permissions for player (while server will be restarted)
	 *
	 * @param player - that will receive permission
	 * @param permissions - list of permissions
	 */
	public void remove(Player player, @NotNull List<String> permissions) {
		ImmutableList.of(permissions).forEach(permission -> remove(player, permission) );
	}

	/**
	 * Checks if player has permission
	 *
	 * @param player - that will receive permission
	 * @param permission
	 * @return true if player has permission
	 */
	public abstract boolean has(Player player, String permission);

	/**
	 * Checks if player has all listed permissions
	 *
	 * @param player - that will receive permission
	 * @param permissions - list of permissions
	 * @return true if all permission checks is passed, false otherwise
	 */
	public boolean has(Player player, @NotNull String... permissions) {
		for (final String permission : permissions)
			if (!has(player, permission))
				return false;
		return true;
	}

	/**
	 * Checks if player has all listed permissions
	 *
	 * @param player - that will receive permission
	 * @param permissions - list of permissions
	 * @return true if all permission checks is passed, false otherwise
	 */
	public boolean has(Player player, @NotNull List<String> permissions) {
		for (final String permission : ImmutableList.copyOf(permissions))
			if (!has(player, permission))
				return false;
		return true;
	}

}
