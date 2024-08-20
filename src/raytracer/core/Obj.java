package raytracer.core;

import raytracer.geom.Primitive;
import raytracer.math.Color;

/**
 * Represents a basic scene object
 */
public interface Obj extends Primitive {

	/**
	 * Computes the color for the given hit and trace
	 *
	 * @return The computed color
	 */
	Color shade(Hit hit, Trace trace);
}
