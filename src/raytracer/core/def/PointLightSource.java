package raytracer.core.def;

import raytracer.core.LightSource;
import raytracer.math.Color;
import raytracer.math.Point;

/**
 * Represents a spherical light source emitting light in all directions
 */
public class PointLightSource implements LightSource {

	private final Color color;
	private final Point m;

	/**
	 * Creates a new light source
	 *
	 * @param m
	 *            The position of the light source
	 * @param r
	 *            The radius of the sphere
	 * @param color
	 *            The color of the light source
	 */
	public PointLightSource(final Point m, final Color color) {
		this.color = color;
		this.m = m;
	}

	/**
	 * Returns the location of the light source
	 *
	 * @return The location of the light source
	 */
	@Override
	public Point getLocation() {
		return m;
	}

	/**
	 * Returns the color of the light source
	 */
	public Color getColor() {
		return color;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ m.hashCode() ^ color.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof PointLightSource)
			return super.equals(obj)
					&& ((PointLightSource) obj).color.equals(color);
		return false;
	}

}
