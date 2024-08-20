package raytracer.math;

/**
 * Constant helper utility
 */
public class Constants {

	/**
	 * The static epsilon helping to deal with floating point precision errors
	 */
	public static final float EPS = 0.00001f;

	/**
	 * Returns true if the given value is close to zero
	 *
	 * @param f
	 *            The value to check
	 * @return True if the given value is close to zero
	 */
	public static final boolean isZero(final float f) {
		return -EPS <= f && f <= EPS;
	}

	/**
	 * Returns true if the given values are equal (according to epsilon)
	 *
	 * @param f
	 *            The first value
	 * @param s
	 *            The second value
	 * @return The result of the comparison
	 */
	public static final boolean isEqual(final float f, final float s) {
		return isEqual(f, s, EPS);
	}

	/**
	 * Returns true if the given values are equal (according to epsilon)
	 *
	 * @param f
	 *            The first value
	 * @param s
	 *            The second value
	 * @param eps
	 *            The epsilon to use
	 * @return The result of the comparison
	 */
	public static final boolean isEqual(final float f, final float s, final float eps) {
		return Math.abs(f - s) <= eps;
	}

}
