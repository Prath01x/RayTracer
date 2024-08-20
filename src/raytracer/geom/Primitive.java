package raytracer.geom;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.math.Ray;

/**
 * Represents a basic primitive of a scene
 */
public interface Primitive {

	/**
	 * Computes the bounding box for this primitive
	 *
	 * @return The bounding box for this primitive
	 */
	BBox bbox();

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
	Hit hit(Ray ray, Obj obj, float tmin, float tmax);

}
