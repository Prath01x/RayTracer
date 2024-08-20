package raytracer.core.def;

import java.util.LinkedList;
import java.util.List;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.geom.BBox;
import raytracer.math.Ray;

/**
 * Represents a simple acceleration structure (which is a structure providing no
 * acceleration at all: O(n))
 */
public class SimpleAccelerator extends Accelerator {

	private final List<Obj> prims = new LinkedList<Obj>();

	/**
	 * Returns the infinity bounding box
	 */
	@Override
	public BBox bbox() {
		return BBox.INF;
	}

	/**
	 * Adds an object to the acceleration structure
	 *
	 * @param prim
	 *            The object to add
	 */
	@Override
	public void add(final Obj prim) {
		prims.add(prim);
	}

	/**
	 * Computes a hit point with the given parameters
	 *
	 * @param ray
	 *            The ray to compute the intersection with
	 * @param obj
	 *            The object to compute the intersection with
	 * @param tmin
	 *            The minimum distance
	 * @param tmax
	 *            The maximum distance
	 * @return The computed hit
	 */
	public Hit hit(final Ray ray, final Obj obj, final float tmin, float tmax) {
		Hit nearest = Hit.No.get();
		for (final Obj p : prims) {
			final Hit hit = p.hit(ray, p, tmin, tmax);
			if (hit.hits()) {
				final float t = hit.getParameter();
				if (t < tmax) {
					nearest = hit;
					tmax = t;
				}
			}
		}

		return nearest;
	}

}
