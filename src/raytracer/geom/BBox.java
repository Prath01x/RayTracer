package raytracer.geom;

import raytracer.core.Hit;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec3;

/**
 * Represents a bounding box object
 */
public class BBox {

	/**
	 * The infinity (infinitely large) bounding box
	 */
	public static final BBox INF = new BBox(Point.ORIGIN.sub(Vec3.INF),
			Point.ORIGIN.add(Vec3.INF));

	/**
	 * The empty bounding box
	 */
	public static final BBox EMPTY = new BBox(Point.ORIGIN.add(Vec3.INF),
			Point.ORIGIN.sub(Vec3.INF));

	private final Point[] pp;

	public static final int MIN = 0;
	public static final int MAX = 1;

	private BBox(final Point a, final Point b) {
		pp = new Point[] { a, b };
	}

	/**
	 * Creates a new bounding box
	 *
	 * @param a
	 *            One of the two points that should be surrounded by the box
	 * @param b
	 *            One of the two points that should be surrounded by the box
	 * @return The resulting bounding box surrounding the two points
	 */
	public static BBox create(final Point a, final Point b) {
		final Point min = a.min(b);
		final Point max = a.max(b);

		return new BBox(min, max);
	}

	/**
	 * Computes a bounding box surrounding the two given bounding boxes
	 *
	 * @param a
	 *            The first bounding box
	 * @param b
	 *            The second bounding box
	 * @return A bounding box surrounding the two given bounding boxes
	 */
	public static BBox surround(final BBox a, final BBox b) {
		final Point min = a.pp[MIN].min(b.pp[MIN]);
		final Point max = a.pp[MAX].max(b.pp[MAX]);
		return new BBox(min, max);
	}

	/**
	 * Checks whether the given box intersects the current once
	 *
	 * @param b
	 *            The other bounding box
	 * @return True if the two boxes intersect with each other
	 */
	public final boolean intersects(final BBox b) {
		for (int i = 0; i != 3; ++i) {
			if (!(pp[MIN].get(i) < b.pp[MAX].get(i) && b.pp[MIN].get(i) < pp[MAX]
					.get(i)))
				return false;
		}
		return true;
	}

	/**
	 * Returns the minimum of the box
	 *
	 * @return The minimum of the box
	 */
	public final Point getMin() {
		return pp[MIN];
	}

	/**
	 * Returns the maximum of the box
	 *
	 * @return The maximum of the box
	 */
	public final Point getMax() {
		return pp[MAX];
	}

	/**
	 * Computes a hit point with the given parameters
	 *
	 * @param ray
	 *            The ray to compute the intersection with
	 * @param tmin
	 *            The minimum distance
	 * @param tmax
	 *            The maximum distance
	 * @return The computed hit
	 */
	public Hit hit(final Ray ray, float tmin, float tmax) {
		final Vec3 /* normalized */dir = ray.dir();
		final Vec3 invDir = ray.invDir();
		final Point base = ray.base();
		for (int i = 0; i < 3; i++) {
			final int neg = dir.isNeg(i);
			final float ba = base.get(i);
			final float inv = invDir.get(i);
			final float t0 = (pp[neg].get(i) - ba) * inv;
			final float t1 = (pp[1 - neg].get(i) - ba) * inv;
			if (t0 > tmin)
				tmin = t0;
			if (t1 < tmax)
				tmax = t1;
			if (tmin > tmax)
				return Hit.No.get();
		}

		return Hit.Yes.get();
	}

	@Override
	public final int hashCode() {
		int result = 0xf6a2810d;
		for (final Point p : pp)
			result ^= p.hashCode();
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (obj instanceof BBox) {
			final BBox cobj = (BBox) obj;
			// sanity check
			if (cobj.pp.length != pp.length)
				return false;
			for (int i = 0; i < pp.length; ++i)
				if (!cobj.pp[i].equals(pp[i]))
					return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s-%s", pp[MIN], pp[MAX]);
	}
}
