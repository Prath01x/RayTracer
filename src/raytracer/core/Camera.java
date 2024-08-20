package raytracer.core;

import raytracer.math.Ray;

/**
 * Represents the basic camera interface
 */
public interface Camera {

	/**
	 * Casts a ray through the pixels x and y
	 *
	 * @return The casted ray
	 */
	Ray cast(float x, float y);

}
