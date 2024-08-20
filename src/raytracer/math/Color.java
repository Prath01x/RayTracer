package raytracer.math;

/**
 * Represents a single color consiting of RGB values
 */
public final class Color extends Vec4<Color, Color> {

	public static final Color BLACK   = new Color(0, 0, 0);
	public static final Color RED     = new Color(1, 0, 0);
	public static final Color GREEN   = new Color(0, 1, 0);
	public static final Color BLUE    = new Color(0, 0, 1);
	public static final Color YELLOW  = new Color(1, 1, 0);
	public static final Color MAGENTA = new Color(1, 0, 1);
	public static final Color CYAN    = new Color(0, 1, 1);
	public static final Color GRAY    = new Color(0.5f, 0.5f, 0.5f);
	public static final Color WHITE   = new Color(1, 1, 1);

	/**
	 * Creates a new color
	 *
	 * @param r
	 *            The red part
	 * @param g
	 *            The green part
	 * @param b
	 *            The blue part
	 */
	public Color(final float r, final float g, final float b) {
		super(sat(r), sat(g), sat(b), 0);
	}

	@Override
	protected Color create(final float r, final float g, final float b, final float a) {
		return new Color(r, g, b);
	}

	private static float sat(final float x) {
		if (x > 1.0f)
			return 1.0f;
		if (x < 0.0f)
			return 0.0f;
		return x;
	}

	/**
	 * Converts the color into a single RGB integer value
	 *
	 * @return The color as single RGB integer value
	 */
	public int rgb() {
		final int r = (int) (x * 255.0);
		final int g = (int) (y * 255.0);
		final int b = (int) (z * 255.0);
		return (r << 16) | (g << 8) | b;
	}

	@Override
	public String toString() {
		return String.format("[%f %f %f]", x, y, z);
	}
}
