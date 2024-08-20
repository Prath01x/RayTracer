package raytracer.core.def;

import java.util.Collection;

import raytracer.core.Camera;
import raytracer.core.Hit;
import raytracer.core.LightSource;
import raytracer.core.Scene;
import raytracer.geom.Primitive;
import raytracer.math.Color;
import raytracer.math.Ray;

/**
 * Represents a basic scene
 */
public class StandardScene implements Scene {

	private final Collection<LightSource> lights;
	private final Primitive accel;
	private final Color background = Color.BLACK;
	private final Camera camera;

	/**
	 * Creates a new standard scene
	 *
	 * @param cam
	 *            The camera to use
	 * @param lights
	 *            The lights to use
	 * @param accel
	 *            The acceleration structure or simple primitive to use
	 */
	public StandardScene(final Camera cam, final Collection<LightSource> lights,
			final Primitive accel) {
		this.accel = accel;
		this.camera = cam;
		this.lights = lights;
	}

	/**
	 * Returns the background color
	 *
	 * @return The background color
	 */
	@Override
	public Color getBackground() {
		return background;
	}

	/**
	 * Returns the used camera
	 *
	 * @return The used camera
	 */
	@Override
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Returns the collection of light sources
	 *
	 * @return The collection of light sources
	 */
	@Override
	public Collection<LightSource> getLightSources() {
		return lights;
	}

	/**
	 * Computes an hit (if possible) with the given ray and the scene
	 *
	 * @param ray
	 *            The ray used for intersection computation
	 * @return The computed hit
	 */
	@Override
	public Hit hit(final Ray ray) {
		return accel.hit(ray, null, 0, Float.POSITIVE_INFINITY);
	}

}
