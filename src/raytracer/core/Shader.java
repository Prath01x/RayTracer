package raytracer.core;

import raytracer.math.Color;

/**
 * Represents a basic shader
 */
public interface Shader {

	/**
	 * Computes the shaded color for the given hit and the trace
	 *
	 * @param hit
	 *            The hit to use
	 * @param trace
	 *            The trace to use
	 * @return The computed color
	 */
	Color shade(Hit hit, Trace trace);

}
