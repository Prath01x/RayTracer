package raytracer.geom;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.math.Ray;

/**
 * Represents a primitive object which has a bounding box
 */
public abstract class BBoxedPrimitive implements Primitive {

	private BBox bbox;

	/**
	 * Initializes the boxed primitive with an empty bounding box
	 */
	protected BBoxedPrimitive() {
		this.bbox = BBox.INF;
	}

	/**
	 * Initializes the boxed primitive with the given bounding box
	 *
	 * @param bbox
	 *            The bounding box to use
	 */
	protected BBoxedPrimitive(final BBox bbox) {
		this.bbox = bbox;
	}

	/**
	 * Sets the bounding box
	 *
	 * @param bbox
	 *            The bounding box to use
	 */
	protected final void setBBox(final BBox bbox) {
		this.bbox = bbox;
	}

	/**
	 * Returns the bounding box of this primitive
	 *
	 * @Return The bounding box of this primitive
	 */
	@Override
	public final BBox bbox() {
		return bbox;
	}

	/**
	 * Computes a hit point with the given parameters. First an intersection
	 * with the bounding box is tested and if this intersection succeeds the
	 * internal object is intersected with the given parameters.
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
	@Override
	public final Hit hit(final Ray ray, final Obj obj, final float tmin, final float tmax) {
		if (bbox.hit(ray, tmin, tmax).hits())
			return hitTest(ray, obj, tmin, tmax);
		return Hit.No.get();
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
	public abstract Hit hitTest(Ray ray, Obj obj, float tmin, float tmax);

}
