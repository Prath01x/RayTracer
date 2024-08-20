package raytracer.core;

import raytracer.math.Color;
import raytracer.math.Point;

/**
 * Represents a light source object
 */
public interface LightSource {

	/**
	 * Returns the location of the light source
	 *
	 * @return The location of the light source
	 */
	Point getLocation();

	Color getColor();
}
