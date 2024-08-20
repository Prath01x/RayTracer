package raytracer.math;

public abstract class Vec4<T extends Vec4<?, ?>, A extends Vec4<?, ?>> {

    protected final float x, y, z, w;

    protected Vec4(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    protected Vec4(final T v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    protected abstract T create(float x, float y, float z, float w);

    /**
     * Returns the i-th entry (x=0, y=1, z=2, w=3) of this vector.
     *
     * @param i index of the entry
     * @return value of the i-ths entry
     */
    public final float get(final int i) {
        assert 0 <= i && i < 4;
        switch (i) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            case 3:
                return w;
        }
        throw new IllegalArgumentException(
                "Vector component index out of range");
    }

    public final float x() {
        return x;
    }

    public final float y() {
        return y;
    }

    public final float z() {
        return z;
    }

    public final float w() {
        return w;
    }

    public final boolean isNan() {
        return Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z)
                || Float.isNaN(w);
    }

    public final boolean isInfinity() {
        return Float.isInfinite(x) || Float.isInfinite(y)
                || Float.isInfinite(z) || Float.isInfinite(w);
    }

    public final boolean isFinite() {
        return !isNan() && !isInfinity();
    }

    public final int isNeg(final int i) {
        return get(i) < 0 ? 1 : 0;
    }

    public final float dot(final float vx, final float vy, final float vz, final float vw) {
        return x * vx + y * vy + z * vz + w * vw;
    }

    public final float dot(final Vec4<?, ?> v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    public final float dot(final Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public final T combine(final float r, final A b) {
        return create(x + r * b.x, y + r * b.y, z + r * b.z, w + r * b.w);
    }

    public final T combine(final float s, final float r, final A b) {
        return create(s * x + r * b.x, s * y + r * b.y, s * z + r * b.z, s * w
                + r * b.w);
    }

    public final T blend(final float r, final A b) {
        return combine(r, 1 - r, b);
    }

    /**
     * Scales vector by the given factor.
     *
     * @param r scaling factor
     * @return scaled vector
     */
    public final T scale(final float r) {
        return create(r * x, r * y, r * z, r * w);
    }

    /**
     * Computes the element-wise sum of this vector and the given vector b.
     *
     * @param b the second vector
     * @return element-wise sum of this and the given vector b
     */
    public final T add(final A b) {
        return create(x + b.x, y + b.y, z + b.z, w + b.w);
    }

    /**
     * Subtracts the given vector element-wise from this vector.
     *
     * @param b the second vector
     * @return element-wise difference of this and the given vector b
     */
    public final T sub(final A b) {
        return create(x - b.x, y - b.y, z - b.z, w - b.w);
    }

    /**
     * Computes the element-wise product of this vector and the given vector b.
     *
     * @param b the second vector
     * @return element-wise product of this and the given vector b
     */
    public final T mul(final A b) {
        return create(x * b.x, y * b.y, z * b.z, w * b.w);
    }

    public final T avg(final A v, final int n) {
        return blend(n / (n + 1.0f), v);
    }

    public final T inv() {
        return create(1.0f / x, 1.0f / y, 1.0f / z, 1.0f / w);
    }

    public final T neg() {
        return scale(-1);
    }

    @Override
    public String toString() {
        return String.format("[%f %f %f %f]", x, y, z, w);
    }

    public final T avg(final T v) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;

        final float scale = 1.0f / 2;
        x *= scale;
        y *= scale;
        z *= scale;
        w *= scale;

        return create(x, y, z, w);
    }

    public final T avg(final T v, final T v2) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;

        x += v2.x;
        y += v2.y;
        z += v2.z;
        w += v2.w;

        final float scale = 1.0f / 3;
        x *= scale;
        y *= scale;
        z *= scale;
        w *= scale;

        return create(x, y, z, w);
    }

    /**
     * Computes the element-wise minimum of this vector and the given vector b.
     *
     * @param b the second vector
     * @return element-wise minimum of this and the given vector b
     */
    public final T min(final T v) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        if (v.x < x)
            x = v.x;
        if (v.y < y)
            y = v.y;
        if (v.z < z)
            z = v.z;
        if (v.w < w)
            w = v.w;

        return create(x, y, z, w);
    }

    /**
     * Computes the element-wise minimum of this vector and the given vectors.
     *
     * @param v the second vector
     * @param w the third vector
     * @return element-wise minimum of this and the given vectors
     */
    public final T min(final T v, final T v2) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        if (v.x < x)
            x = v.x;
        if (v.y < y)
            y = v.y;
        if (v.z < z)
            z = v.z;
        if (v.w < w)
            w = v.w;

        if (v2.x < x)
            x = v2.x;
        if (v2.y < y)
            y = v2.y;
        if (v2.z < z)
            z = v2.z;
        if (v2.w < w)
            w = v2.w;

        return create(x, y, z, w);
    }

    /**
     * Computes the element-wise maximum of this vector and the given vector b.
     *
     * @param b the second vector
     * @return element-wise maximum of this and the given vector b
     */
    public final T max(final T v) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        if (v.x > x)
            x = v.x;
        if (v.y > y)
            y = v.y;
        if (v.z > z)
            z = v.z;
        if (v.w > w)
            w = v.w;

        return create(x, y, z, w);
    }

    /**
     * /**
     * Computes the element-wise maximum of this vector and the given vectors.
     *
     * @param v the second vector
     * @param w the third vector
     * @return element-wise maximum of this and the given vector b
     */
    public final T max(final T v, final T v2) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        if (v.x > x)
            x = v.x;
        if (v.y > y)
            y = v.y;
        if (v.z > z)
            z = v.z;
        if (v.w > w)
            w = v.w;

        if (v2.x > x)
            x = v2.x;
        if (v2.y > y)
            y = v2.y;
        if (v2.z > z)
            z = v2.z;
        if (v2.w > w)
            w = v2.w;

        return create(x, y, z, w);
    }

    @Override
    public final int hashCode() {
        return Float.floatToIntBits(x) ^ Float.floatToIntBits(y)
                ^ Float.floatToIntBits(z) ^ Float.floatToIntBits(w)
                ^ 0x75CA01D3;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof Vec4<?, ?>) {
            final Vec4<?, ?> cobj = (Vec4<?, ?>) obj;
            final boolean f = isNan();
            final boolean s = cobj.isNan();
            if (f && !s || !f && s)
                return false;
            return (f && s)
                    || (!f && !s && (Constants.isEqual(cobj.x, x)
                            && Constants.isEqual(cobj.y, y)
                            && Constants.isEqual(cobj.z, z) && Constants
                                    .isEqual(cobj.w, w)));
        }
        return false;
    }
}
