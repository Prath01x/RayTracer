package raytracer.math;

/**
 * Represents a single ray
 */
public class Ray {

	private final Point base;
	private final Vec3 /* normalized */dir;
	private final Vec3 invDir;

	/**
	 * Creates a new ray
	 *
	 * @param base
	 *            The original of the ray
	 * @param direction
	 *            The direction of the ray (normalized)
	 */
	public Ray(final Point base, final Vec3 /* normalized */direction) {
		this.base = base;
		this.dir = direction;
		this.invDir = dir.inv();
	}

	/**
	 * Returns the direction of the ray
	 *
	 * @return The direction of the ray
	 */
	public final Vec3 /* normalized */dir() {
		return dir;
	}

	/**
	 * Returns the origin of the ray
	 *
	 * @return The origin of the ray
	 */
	public final Point base() {
		return base;
	}

	/**
	 * Returns the inverse of the direction vector
	 *
	 * @return The inverse of the direction vector
	 */
	public final Vec3 invDir() {
		return invDir;
	}

	/**
	 * Computes the point on the ray for the given distance
	 *
	 * @param r
	 *            The distance
	 * @return The point on the ray for the given distance
	 */
	public final Point eval(final float r) {
		return base.combine(r, dir);
	}

	/**
	 * Reflects a ray at point p with the given normal
	 *
	 * @param p
	 *            The point p
	 * @param normal
	 *            The normal used for reflection
	 * @return The reflected ray
	 */
	public final Ray reflect(final Point p, final Vec3 /* normalized */normal) {
		final Vec3 /* normalized */nDir = dir.reflect(normal);
		return new Ray(p, nDir);
	}

	@Override
	public final int hashCode() {
		return base.hashCode() ^ dir.hashCode();
	}

	@Override
	public final boolean equals(final Object obj) {
		if (obj instanceof Ray) {
			final Ray cobj = (Ray) obj;
			return cobj.base.equals(base) && cobj.dir.equals(dir);
		}
		return false;
	}

}
