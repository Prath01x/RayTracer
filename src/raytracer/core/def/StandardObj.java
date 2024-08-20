package raytracer.core.def;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.core.Shader;
import raytracer.core.Trace;
import raytracer.geom.BBox;
import raytracer.geom.Primitive;
import raytracer.math.Color;
import raytracer.math.Ray;

/**
 * Represents a standard object
 */
public class StandardObj implements Obj {

	private final Primitive primitive;
	private final Shader shader;

	/**
	 * Creates a new standard object
	 *
	 * @param primitive
	 *            The internal primitive
	 * @param shader
	 *            The internal shader
	 */
	public StandardObj(final Primitive primitive, final Shader shader) {
		this.primitive = primitive;
		this.shader = shader;
	}

	/**
	 * Returns the computed color using the internal shader
	 */
	@Override
	public Color shade(final Hit hit, final Trace trace) {
		return shader.shade(hit, trace);
	}

	/**
	 * Returns the bounding box of the encapsulated primitive
	 */
	@Override
	public BBox bbox() {
		return primitive.bbox();
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
	@Override
	public Hit hit(final Ray ray, final Obj obj, final float tmin, final float tmax) {
		return primitive.hit(ray, this, tmin, tmax);
	}

	@Override
	public int hashCode() {
		return primitive.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof StandardObj) {
			return ((StandardObj) obj).primitive.equals(primitive);
		}
		return false;
	}

}
