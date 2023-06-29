package org.aslstd.api.bukkit.location;

import org.bukkit.Location;

public class Area3D {

	private Vec3 from, to;

	public Area3D(Vec3 fPos, Vec3 sPos) {
		from = Vec3.min(fPos, sPos);
		to = Vec3.max(fPos, sPos);
	}

	public Area3D toOddRadius() {
		final double xd = to.getX()-from.getX()%2;
		final double zd = to.getZ()-from.getZ()%2;

		if (xd < 1 || zd < 1)
			to.add(xd < 1 ? 1 : 0, 0, zd < 1 ? 1 : 0);

		return this;
	}

	public Vec3 getFirstPosition() { return from.clone(); }

	public Vec3 getSecondPosition() { return to.clone(); }

	public boolean isInArea2D(Location loc) { return isInArea2D(Vec3.of(loc)); }

	public boolean isInArea3D(Location loc) { return isInArea3D(Vec3.of(loc)); }

	public boolean isInArea2D(Vec3 point) {
		return 	point.getX() >= from.getX() &&
				point.getX() <= to.getX() &&
				point.getZ() >= from.getZ() &&
				point.getZ() <= to.getZ();
	}

	public boolean isInArea3D(Vec3 point) {
		return  point.getX() >= from.getX() &&
				point.getX() <= to.getX() &&
				point.getY() >= from.getY() &&
				point.getY() <= to.getY() &&
				point.getZ() >= from.getZ() &&
				point.getZ() <= to.getZ();
	}

}
