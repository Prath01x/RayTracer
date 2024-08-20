package raytracer.math;

public final class Vec2 extends Vec4<Vec2, Vec2> {

    /**
     * Creates a two-dimensional vector.
     *
     * @param u the x-translation
     * @param v the y-translation
     */
    public Vec2(final float u, final float v) {
        super(u, v, 0, 0);
    }

    @Override
    protected Vec2 create(final float x, final float y, final float z, final float w) {
        return new Vec2(x, y);
    }

    @Override
    public String toString() {
        return String.format("[%f %f]", x, y);
    }
}
