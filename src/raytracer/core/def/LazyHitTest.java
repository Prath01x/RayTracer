package raytracer.core.def;

import raytracer.core.Hit;
import raytracer.core.Obj;

/**
 * Provides the capability to compute the actual hit lazily
 */
public abstract class LazyHitTest implements Hit {

	private boolean hits = false;
	private boolean calculated = false;
	private final Obj obj;

	protected LazyHitTest(final Obj obj) {
		this.obj = obj;
	}

	/**
	 * Computes whether the object is hit
	 *
	 * @return True if the object is hit, false otherwise
	 */
	protected abstract boolean calculateHit();

	/**
	 * Returns true if the object is hit, false otherwise
	 *
	 * @return True if the object is hit, false otherwise
	 */
	@Override
	public final boolean hits() {
		if (!calculated) {
			calculated = true;
			hits       = calculateHit();
		}
		return hits;
	}

	/**
	 * Returns the hit object
	 */
	@Override
	public final Obj get() {
		return obj;
	}

}
