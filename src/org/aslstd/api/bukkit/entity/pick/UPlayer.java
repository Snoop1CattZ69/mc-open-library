package org.aslstd.api.bukkit.entity.pick;

import java.util.List;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.openlib.provider.permission.PermProvider;
import org.aslstd.core.OpenLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import lombok.Getter;

public class UPlayer extends UEntity {

	@Getter protected Player player;

	public UPlayer(Player player) {
		super(player);
		this.player = player;

		defaults();
	}

	private void defaults() {
		player.setHealthScaled(true);
	}

	/** {@inheritDoc} */
	@Override public String displayName() { return player.getDisplayName(); }

	public String name() { return player.getName(); }

	public void send(String message) { Texts.send(player, message); }

	public void execute(Consumer<Player> function) { function.accept(player); }

	public void dispatch(String command) { Bukkit.dispatchCommand(player, command); }

	/** {@inheritDoc} */
	@Override public ItemStack hand() { return player.getInventory().getItemInMainHand(); }

	/** {@inheritDoc} */
	@Override public ItemStack offhand() { return player.getInventory().getItemInOffHand(); }

	/** {@inheritDoc} */
	@Override public ItemStack head() { return player.getInventory().getHelmet(); }

	/** {@inheritDoc} */
	@Override public ItemStack body() { return player.getInventory().getChestplate(); }

	/** {@inheritDoc} */
	@Override public ItemStack leggs() { return player.getInventory().getLeggings(); }

	/** {@inheritDoc} */
	@Override public ItemStack foots() { return player.getInventory().getBoots(); }

	/** @see PermProvider#add(Player, String) */
	public void addPermissision(String permission) { OpenLib.permission().add(player, permission); }

	/** @see PermProvider#add(Player, String...) */
	public void addPermissions(String... permissions) { OpenLib.permission().add(player, permissions); }

	/** @see PermProvider#add(Player, List) */
	public void addPermissions(List<String> permissions) { OpenLib.permission().add(player, permissions); }

	/** @see PermProvider#remove(Player, String) */
	public void removePermission(String permission) { OpenLib.permission().remove(player, permission); }

	/** @see PermProvider#remove(Player, String...) */
	public void removePermissions(String... permissions) { OpenLib.permission().remove(player, permissions); }

	/** @see PermProvider#remove(Player, List) */
	public void removePermissions(List<String> permissions) { OpenLib.permission().remove(player, permissions); }

	/** @see PermProvider#has(Player, String) */
	public boolean hasPermission(String permission) { return OpenLib.permission().has(player, permission); }

	/** @see PermProvider#has(Player, String...) */
	public boolean hasPermissions(String... permissions) { return OpenLib.permission().has(player, permissions); }

	/** @see PermProvider#has(Player, List) */
	public boolean hasPermissions(List<String> permissions) { return OpenLib.permission().has(player, permissions); }

	@Override
	protected void healthChanged(double newValue) {
		if (OpenLib.config().ONE_HP_BAR)
			player.setHealthScale(newValue / OpenLib.config().HEALTH_PER_BAR * 20.0D);
		else
			player.setHealthScale(20.0D);
	}

}
