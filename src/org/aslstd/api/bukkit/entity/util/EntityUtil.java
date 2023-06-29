package org.aslstd.api.bukkit.entity.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.aslstd.api.bukkit.location.Area3D;
import org.aslstd.api.bukkit.location.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * <p>EntityUtil class.</p>
 *
 * @author Snoop1CattZ69
 */
public final class EntityUtil {

	/**
	 * <p>getNearbyEntities.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param x a double
	 * @param y a double
	 * @param z a double
	 * @return a {@link List} object
	 */
	public static List<Entity> getNearbyEntities(Player p, double x, double y, double z) {
		final List<Entity> entities = new ArrayList<>();

		final Area3D area = getAreaAroundPlayer(p, x, y, z);

		for (final Entity entity : p.getWorld().getLivingEntities())
			if (area.isInArea2D(entity.getLocation()))
				entities.add(entity);

		return entities;
	}

	/**
	 * <p>getNearbyPlayers.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param x a double
	 * @param y a double
	 * @param z a double
	 * @return a {@link List} object
	 */
	public static List<Player> getNearbyPlayers(Player p, double x, double y, double z) {
		final List<Player> players = new ArrayList<>();

		final Area3D area = getAreaAroundPlayer(p, x, y, z);

		for (final Player player : p.getWorld().getPlayers())
			if (area.isInArea2D(player.getLocation()))
				players.add(player);

		return players;
	}

	/**
	 * <p>getAreaAroundPlayer.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param x a double
	 * @param y a double
	 * @param z a double
	 * @return a {@link org.aslstd.api.bukkit.location.Area3D} object
	 */
	public static Area3D getAreaAroundPlayer(Player p, double x, double y, double z) {
		final Vec3 origin = Vec3.of(x/2,y/2,z/2);

		return new Area3D(Vec3.of(p.getLocation()).substract(origin), Vec3.of(p.getLocation()).add(origin));
	}

	public static Player getOnlinePlayer(String name) {
		return Bukkit.getPlayerExact(name);
	}

	public static OfflinePlayer getPlayer(String search) {
		OfflinePlayer player = Bukkit.getPlayerExact(search);
		if (player == null)
			for (final OfflinePlayer ofp : Bukkit.getOfflinePlayers()) {
				final String name = ofp.getName();
				final UUID uid = ofp.getUniqueId();

				if (name != null && name.equalsIgnoreCase(search)) {
					player = ofp; break;
				}

				if (uid != null && uid.toString().equalsIgnoreCase(search)) {
					player = ofp; break;
				}
			}
		return player;
	}


	public static OfflinePlayer getPlayerByUUID(String uid) {
		return getPlayerByUUID(uid, false);
	}

	public static OfflinePlayer getPlayerByUUID(String uid, boolean repeat) {
		OfflinePlayer player;
		try {
			final UUID forSearch = UUID.fromString(uid);
			player = Bukkit.getOfflinePlayer(forSearch);
			if (player == null && repeat)
				for (final OfflinePlayer ofp : Bukkit.getOfflinePlayers()) {
					final UUID puid = ofp.getUniqueId();

					if (puid != null && puid.equals(forSearch)) {
						player = ofp; break;
					}
				}

		} catch (final Exception e) { return null; }

		return player;
	}

}
