package org.aslstd.api.bukkit.location;


/**
 * <p>Direction class.</p>
 *
 * @author Snoop1CattZ69
 */
public enum Direction {
	XZ(Vec3.of(1,0,1)),
	XZR(Vec3.of(1,0,-1)),
	ZX(Vec3.of(-1,0,1)),
	ZXR(Vec3.of(-1,0,-1)),
	X(Vec3.of(1,0,0)),
	Y(Vec3.of(0,1,0)),
	Z(Vec3.of(0,0,1)),
	NULL;

	Vec3 vec = Vec3.of(0,0,0);

	Direction() {}
	Direction(Vec3 vec) { this.vec = vec; }

	/**
	 * <p>getVector3D.</p>
	 *
	 * @return a {@link org.aslstd.api.bukkit.location.Vec3} object
	 */
	public Vec3 getVector3D() { return vec.clone(); }

}
