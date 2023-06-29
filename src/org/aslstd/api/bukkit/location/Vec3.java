package org.aslstd.api.bukkit.location;

import javax.annotation.Nullable;

import org.aslstd.api.bukkit.value.util.NumUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vec3 implements Cloneable {

	@Getter private double x = 0, y = 0, z = 0;

	@Getter private World world;

	private Vec3(double x, double y, double z) {
		this.x = x; this.y = y; this.z = z;
	}

	public static Vec3 of(double x, double y, double z) {
		return new Vec3(x, y, z);
	}

	public static Vec3 of(double x, double y, double z, World world) {
		return new Vec3(x, y, z, world);
	}

	public static Vec3 of(Location loc) {
		return Vec3.of(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld());
	}

	public static @Nullable Vec3 of(String loc) {
		if (loc == null) return null;
		final String[] prep = loc.split(";");
		if (prep == null || prep.length < 3) return null;

		final Vec3 vec = Vec3.of(0,0,0);
		if (prep.length >= 4)
			vec.world = Bukkit.getWorld(prep[3]);
		try {
			return vec.add(Vec3.of(NumUtil.parseInteger(prep[0]),NumUtil.parseInteger(prep[1]),NumUtil.parseInteger(prep[2]))).clone();
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	public static Vec3 center(Vec3 fst, Vec3 scd) {
		return fst.clone().add(scd).divide(2d);
	}

	public static Vec3 centerRounded(Vec3 fst, Vec3 scd) {
		return center(fst, scd).round();
	}

	public static Vec3 max(Vec3 fst, Vec3 scd) {
		return Vec3.of(
				Math.max(fst.getX(), scd.getX()),
				Math.max(fst.getY(), scd.getY()),
				Math.max(fst.getZ(), scd.getZ())
				);
	}

	public static Vec3 min(Vec3 fst, Vec3 scd) {
		return Vec3.of(
				Math.min(fst.getX(), scd.getX()),
				Math.min(fst.getY(), scd.getY()),
				Math.min(fst.getZ(), scd.getZ())
				);
	}

	public static boolean blockEquals(Location from, Location to) {
		return from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ();
	}

	public double distance(Location loc) {
		final double x = this.x-loc.getX();
		final double y = this.y-loc.getY();
		final double z = this.z-loc.getZ();

		return Math.sqrt(x * x + y * y + z * z);
	}

	public double distance(Vec3 vec) {
		final double x = this.x-vec.x;
		final double y = this.y-vec.y;
		final double z = this.z-vec.z;

		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vec3 round() { x = Math.ceil(x); y = Math.ceil(y); z = Math.ceil(z); return this; }

	public Vec3 x(int x) { this.x = x; return this; }

	public Vec3 y(int y) { this.y = y; return this; }

	public Vec3 z(int z) { this.z = z; return this; }

	public Vec3 multiply(double mod) { x *= mod; y *= mod; z *= mod; return this; }

	public Vec3 divide(double mod) { x /= mod; y /= mod; z /= mod; return this; }

	public Vec3 incrementX() { return incrementX(1); }

	public Vec3 incrementX(int x) { this.x += x; return this; }

	public Vec3 incrementY() { return incrementY(1); }

	public Vec3 incrementY(int y) { this.y += y; return this; }

	public Vec3 incrementZ() { return incrementZ(1); }

	public Vec3 incrementZ(int z) { this.z += z; return this; }

	public Vec3 decrementX() { return decrementX(1); }

	public Vec3 decrementX(int x) { this.x -= x; return this; }

	public Vec3 decrementY() { return decrementY(1); }

	public Vec3 decrementY(int y) { this.y -= y; return this; }

	public Vec3 decrementZ() { return decrementZ(1); }

	public Vec3 decrementZ(int z) { this.z -= z; return this; }

	public Vec3 add(double x, double y, double z) { this.x += x; this.y += y; this.z += z; return this;  }

	public Vec3 add(Vec3 vec) { x += vec.x; y += vec.y; z += vec.z; return this;  }

	public Vec3 substract(double x, double y, double z) { this.x -= x; this.y -= y; this.z -= z; return this;  }

	public Vec3 substract(Vec3 vec) { x -= vec.x; y -= vec.y; z -= vec.z; return this; }

	public Location merge(Location loc) {
		loc.setX(loc.getX()+x);
		loc.setY(loc.getY()+y);
		loc.setZ(loc.getZ()+z);

		return loc;
	}

	public Vec3 reverse() {x *= -1; y *= -1; z *= -1; return this; }

	/*@Override
	public boolean equals(Object obj) {

		if (obj instanceof Location) {
			final Location loc = (Location) obj;
			return loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z;
		}

		if (obj instanceof Vector3D) return toString().equalsIgnoreCase(obj.toString());

		return super.equals(obj);
	} */

	public Location location(Location playerLocation) {
		final Location copy = playerLocation;
		copy.setX(x+0.5);
		copy.setY(y);
		copy.setZ(z+0.5);

		return copy;
	}

	public Location location() {
		return new Location(world, x, y, z);
	}

	@Override
	public Vec3 clone() {
		return Vec3.of(x,y,z,world);
	}

	@Override
	public String toString() {
		return x + ";" + y + ";" + z + (world == null ? "" : ";" + world.getName());
	}

	public String toIntString() {
		return ((int) x) + ";" + ((int) y) + ";" + ((int) z) + (world == null ? "" : ";" + world.getName());
	}
}
