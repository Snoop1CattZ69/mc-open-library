package org.aslstd.api.bukkit.entity.pick;

import org.aslstd.api.openlib.player.OPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class Pick {

	public static OPlayer of(Player p) {
		return OPlayer.stash().get(p.getUniqueId());
	}

	public static UEntity of(LivingEntity e) {
		if (e instanceof final Player p)
			return of(p);
		return new UEntity(e);
	}

}
