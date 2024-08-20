package raytracer.math;

public final class Vec3 extends Vec4<Vec3, Vec3> {

    /**
     * zero vector [0, 0, 0].
     */
    public static final Vec3 ZERO = new Vec3(0.0f, 0.0f, 0.0f);

    public static final Vec3 INF = new Vec3(Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);

    /**
     * unit vector in x direction [1, 0, 0].
     */
    public static final Vec3 /* normalized */ X = new Vec3(1.0f, 0.0f, 0.0f);

    /**
     * unit vector in y direction [0, 1, 0].
     */
    public static final Vec3 /* normalized */ Y = new Vec3(0.0f, 1.0f, 0.0f);

    /**
     * unit vector in z direction [0, 0, 1].
     */
    public static final Vec3 /* normalized */ Z = new Vec3(0.0f, 0.0f, 1.0f);

    protected final float sdot;

    /**
     * Creates a three dimensional vector.
     *
     * @param x x translation
     * @param y y translation
     * @param z z translation
     */
    public Vec3(final float x, final float y, final float z) {
        super(x, y, z, 0);
        this.sdot = x * x + y * y + z * z;
    }

    @Override
    protected Vec3 create(final float x, final float y, final float z, final float w) {
        return new Vec3(x, y, z);
    }

    /**
     * Computes a vector orthogonal to both this vector and the given vector.
     * The given vector b may not be linearly dependent on this vector.
     *
     * @param b the second vector
     * @return vecotor that is orthogonal to both this vector and the vector b
     */
    public final Vec3 cross(final Vec3 b) {
        final float nx = y * b.z - z * b.y;
        final float ny = z * b.x - x * b.z;
        final float nz = x * b.y - y * b.x;
        return new Vec3(nx, ny, nz);
    }

    public final float sdot() {
        return sdot;
    }

    /**
     * Computes the (euclidean) length of this vector.
     *
     * @return length of this vector
     */
    public final float norm() {
        return sdot == 1.0f ? 1.0f : (float) Math.sqrt(sdot);
    }

    /**
     * Normalizes this vector, i.e. returns vector with length 1
     * that has the same direction as this vector.
     *
     * @return normalized vector
     */
    public final Vec3 /* normalized */ normalized() {
        final float factor = 1.0f / norm();
        return new Vec3(factor * x, factor * y, factor * z);
    }

    /**
     * Computes the cosine of the angle that is enclosed by this vector and a
     *
     * @param a second given vector
     * @return cosine of the enclosed angle
     */
    public final float angle(final Vec3 a) {
        return dot(a) / (norm() * a.norm());
    }

    /**
     * Reflects this vector at surface with the normal vector "normal".
     *
     * @param normal reflection normal (normalized)
     * @return reflected vector
     */
    public final Vec3 reflect(final Vec3 /* normalized */ normal) {
        return sub(normal.scale(2 * dot(normal)));
    }

    @Override
    public String toString() {
        return String.format("[%f %f %f]", x, y, z);
    }
}
