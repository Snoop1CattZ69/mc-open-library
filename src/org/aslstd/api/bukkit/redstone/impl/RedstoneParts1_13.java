package org.aslstd.api.bukkit.redstone.impl;

import java.util.List;

import org.aslstd.api.bukkit.redstone.Face;
import org.aslstd.api.bukkit.redstone.RedstoneParts;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;

/**
 * <p>RedstoneParts1_13 class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class RedstoneParts1_13 extends RedstoneParts {

	/** {@inheritDoc} */
	@Override
	public List<Block> getBlocks(Block from) {
		final String typeName = from.getType().name();
		if (typeName.contains("LEVER") || typeName.contains("BUTTON")) {
			final Directional data = (Directional) from.getBlockData();
			switch(data.getFacing()) {
				case EAST:
				case NORTH:
				case SOUTH:
				case WEST:
					return WallLeverButton.getBlocks(from, Face.fromString(data.getFacing().name()));
				case UP:
					return CeilingLeverButton.getBlocks(from);
				case DOWN:
					return FloorLeverButton.getBlocks(from);
				default:
					break;
			}
		}

		if (typeName.contains("PRESSURE_PLATE"))
			return PressurePlate.getBlocks(from);

		if (from.getType() == Material.REDSTONE_WALL_TORCH) {
			final Directional data = (Directional) from.getBlockData();
			return WallRedstoneTorch.getBlocks(from, Face.fromString(data.getFacing().name()));
		}

		if (from.getType() == Material.REDSTONE_WIRE)
			return RedstoneWire.getBlocks(from);

		return FloorRedstoneTorch.getBlocks(from, false);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEnabledRedstone(Material mat) {
		switch(mat) {
			case REDSTONE_TORCH:
			case REDSTONE_WALL_TORCH:
			case DAYLIGHT_DETECTOR:
			case REDSTONE_BLOCK:
				return true;
			default:
				return false;
		}
	}

}
