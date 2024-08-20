package raytracer.core;

import java.util.Collection;

import raytracer.math.Color;
import raytracer.math.Ray;

/**
 * Represents a scene
 */
public interface Scene {

	/**
	 * Returns the background color
	 *
	 * @return The background color
	 */
	Color getBackground();

	/**
	 * Returns the used camera
	 *
	 * @return The used camera
	 */
	Camera getCamera();

	/**
	 * Returns the collection of light sources
	 *
	 * @return The collection of light sources
	 */
	Collection<LightSource> getLightSources();

	/**
	 * Computes an hit (if possible) with the given ray and the scene
	 *
	 * @param ray
	 *            The ray used for intersection computation
	 * @return The computed hit
	 */
	Hit hit(Ray ray);

}
