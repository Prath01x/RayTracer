package raytracer.shade;

import raytracer.core.Hit;
import raytracer.core.Shader;
import raytracer.core.Trace;
import raytracer.math.Color;

public class SingleColor implements Shader {

	private final Color color;

	/**
	 * Generates a simple single-colored texture.
	 *
	 * @param color The color of this surface.
	 */
	public SingleColor(final Color color) {
		this.color = color;
	}

	/**
	 * Computes the shaded color for the given hit and the trace
	 *
	 * @param hit
	 *            The hit to use
	 * @param trace
	 *            The trace to use
	 * @return The computed color
	 */
	@Override
	public Color shade(final Hit hit, final Trace trace) {
		return color;
	}

}
