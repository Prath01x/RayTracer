package raytracer.core;

import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec3;

/**
 * Represents a single trace through the scene. The idea of the trace is to keep
 * track of the generation of the spawned trace. If this number exceeds the
 * constant DEPTH the trace is terminated.
 */
public abstract class Trace {

	/**
	 * The maximum depth of a trace
	 */
	public static final int DEPTH = 4;

	/**
	 * The internal terminator of the trace
	 */
	private static final class Terminator extends Trace {
		public Terminator(final int gen, final Ray ray, final Scene scene) {
			super(gen, ray, scene);
		}

		@Override
		public Hit getHit() {
			return Hit.No.get();
		}

		@Override
		public Color shade() {
			return Color.BLACK;
		}
	}

	private static final class NormalTrace extends Trace {
		private final Hit hit;

		public NormalTrace(final int gen, final Ray ray, final Scene scene) {
			super(gen, ray, scene);
			this.hit = scene.hit(ray);
		}

		@Override
		public Hit getHit() {
			return hit;
		}

		@Override
		public Color shade() {
			return hit.hits() ? hit.get().shade(hit, this) : getScene().getBackground();
		}
	}

	private final int gen;
	private final Ray ray;
	private final Scene scene;

	private Trace(final int gen, final Ray ray, final Scene scene) {
		this.gen = gen;
		this.ray = ray;
		this.scene = scene;
	}

	private static final Trace create(final int gen, final Ray ray, final Scene scene) {
		if (gen > DEPTH)
			return new Terminator(gen, ray, scene);
		else
			return new NormalTrace(gen, ray, scene);
	}

	/**
	 * Returns the generation of the trace
	 *
	 * @return The generation of the trace
	 */
	public int getGen() {
		return gen;
	}

	/**
	 * Returns the used ray
	 *
	 * @return The used ray
	 */
	public Ray getRay() {
		return ray;
	}

	/**
	 * Returns the current scene
	 *
	 * @return The current scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Spawns a new trace from the given point p with the given direction dir
	 *
	 * @param p
	 *            The origin of the new trace
	 * @param dir
	 *            The direction of the new trace (normalized)
	 * @return A new spawned trace
	 */
	public Trace spawn(final Point p, final Vec3 /* normalized */dir) {
		return create(gen + 1, new Ray(p, dir), scene);
	}

	/**
	 * Returns a primary trace (with generation 0)
	 *
	 * @param scene
	 *            The used scene
	 * @param ray
	 *            The initial ray
	 * @return A primary trace (with generation 0)
	 */
	public static Trace primary(final Scene scene, final Ray ray) {
		return new NormalTrace(0, ray, scene);
	}

	/**
	 * Returns the hit of the trace.
	 * 
	 * @return The hit.
	 */
	public abstract Hit getHit();

	/**
	 * Computes the color resulting from a possible intersection with the scene.
	 * If a hit point could not be determined the background color of the scene
	 * is returned.
	 *
	 * @return The color resulting from a possible intersection with the scene.
	 *         If a hit point could not be determined the background color of
	 *         the scene is returned.
	 */
	public abstract Color shade();
}
