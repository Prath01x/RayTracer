package raytracer.core.def;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.core.Trace;
import raytracer.math.Color;

/**
 * A base class for all intersection acceleration structures
 */
public abstract class Accelerator implements Obj {

	/**
	 * Throws an IllegalStateException
	 */
	@Override
	public Color shade(final Hit hit, final Trace trace) {
		throw new IllegalStateException("shade called on an accelerator");
	}

	/**
	 * Adds an object to the acceleration structure
	 *
	 * @param prim
	 *            The object to add
	 */
	public abstract void add(Obj prim);

}
