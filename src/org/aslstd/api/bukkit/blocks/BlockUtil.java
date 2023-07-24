package org.aslstd.api.bukkit.blocks;

import java.util.ArrayList;
import java.util.List;

import org.aslstd.api.bukkit.location.Vec3;
import org.bukkit.block.Block;

/**
 * <p>BlockUtil class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class BlockUtil {

	/**
	 * <p>getBlocksCuboid.</p>
	 *
	 * @param target a {@link org.bukkit.block.Block} object
	 * @param radius a int
	 * @return a {@link List} object
	 */
	public static List<Block> getBlocksCuboid(Block target, int radius) {
		final List<Block> blocks = new ArrayList<>();

		if ((radius%2) == 0)
			radius-=1;

		if (radius > 1)
			radius /= 2;

		for (int x = -radius ; x < (radius+1) ; x++)
			for (int y = -radius ; y < (radius+1) ; y++)
				for (int z = -radius ; z < (radius+1) ; z++)
					blocks.add(target.getWorld().getBlockAt(Vec3.of(x,y,z).merge(target.getLocation())));

		return blocks;
	}

	/**
	 * <p>getBlocksFlat.</p>
	 *
	 * @param target a {@link org.bukkit.block.Block} object
	 * @param radius a int
	 * @return a {@link List} object
	 */
	public static List<Block> getBlocksSquare(Block target, int radius) {
		final List<Block> blocks = new ArrayList<>();

		if ((radius%2) == 0)
			radius-=1;

		if (radius > 1)
			radius /= 2;

		for (int x = -radius ; x < (radius+1) ; x++)
			for (int z = -radius ; z < (radius+1) ; z++)
				blocks.add(target.getWorld().getBlockAt(Vec3.of(x,0,z).merge(target.getLocation())));

		return blocks;

	}

}
