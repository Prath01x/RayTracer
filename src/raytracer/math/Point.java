package raytracer.math;

public final class Point extends Vec4<Point, Vec3> {

    /**
     * A point at the origin.
     */
    public static final Point ORIGIN = new Point(0, 0, 0);

    /**
     * Creates a point at the given coordinates.
     *
     * @param x x-position
     * @param y y-position
     * @param z z-position
     */
    public Point(final float x, final float y, final float z) {
        super(x, y, z, 1.0f);
    }

    @Override
    protected Point create(final float x, final float y, final float z, final float w) {
        return new Point(x, y, z);
    }

    /**
     * Computes a vector from the base p to this point.
     *
     * @param p base point
     * @return vector from p to this
     */
    public final Vec3 sub(final Point p) {
        return new Vec3(x - p.x, y - p.y, z - p.z);
    }

    @Override
    public String toString() {
        return String.format("[%f %f %f]", x, y, z);
    }
}
