package raytracer.core;

import raytracer.math.Point;
import raytracer.math.Vec2;
import raytracer.math.Vec3;

/**
 * Represents a hit (the result of an intersection computation)
 */
public interface Hit {

	/**
	 * The default hit represents a hit which hits something or not
	 */
	public static class Default implements Hit {
		private final boolean res;

		protected Default(final boolean res) {
			this.res = res;
		}

		@Override
		public final Vec3 getNormal() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final float getParameter() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final Point getPoint() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final Vec2 getUV() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final Obj get() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean hits() {
			return res;
		}
	}

	/**
	 * The static hit representing a hit that has hit something
	 */
	public static final class Yes {
		private static final Hit YES = new Default(true);

		/**
		 * Returns the singleton instance of YES
		 *
		 * @return The singleton instance of YES
		 */
		public static final Hit get() {
			return YES;
		}
	}

	/**
	 * The static hit representing a hit that hasn't hit anything
	 */
	public static final class No {
		private static final Hit NO = new Default(false);

		/**
		 * Returns the singleton instance of NO
		 *
		 * @return The singleton instance of NO
		 */
		public static final Hit get() {
			return NO;
		}
	}

	/**
	 * Returns true if the hit has hit something
	 *
	 * @return True if the hit has hit something
	 */
	boolean hits();

	/**
	 * Returns the distance of the hit
	 *
	 * @return The distance of the hit
	 */
	float getParameter();

	/**
	 * Returns the hit point
	 *
	 * @return The hit point
	 */
	Point getPoint();

	/**
	 * Returns the normal
	 *
	 * @return The normal
	 */
	Vec3 getNormal();

	/**
	 * Returns UV texture coordinates
	 *
	 * @return UV texture coordinates
	 */
	Vec2 getUV();

	/**
	 * Returns the hit object
	 *
	 * @return The hit object
	 */
	Obj get();

}
