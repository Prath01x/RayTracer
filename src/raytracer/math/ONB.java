package raytracer.math;

public class ONB {

	private static final float EPS = 0.01f;
	protected final Vec3 /* normalized */u, v, w;

	private ONB(final Vec3 /* normalized */u, final Vec3 /* normalized */v,
			final Vec3 /* normalized */w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	public final Vec3 /* normalized */u() {
		return u;
	}

	public final Vec3 /* normalized */v() {
		return v;
	}

	public final Vec3/* normalized */w() {
		return w;
	}

	public static ONB fromU(final Vec3 u_) {
		final Vec3 /* normalized */u = u_.normalized();
		Vec3 /* normalized */v = u.cross(Vec3.X);
		if (v.sdot < EPS)
			v = u.cross(Vec3.Y);
		final Vec3 /* normalized */w = u.cross(v);
		return new ONB(u, v, w);
	}

	public static ONB fromV(final Vec3 v_) {
		final Vec3 /* normalized */v = v_.normalized();
		Vec3 /* normalized */u = v.cross(Vec3.X);
		if (u.sdot < EPS)
			u = v.cross(Vec3.Y);
		final Vec3 /* normalized */w = u.cross(v);
		return new ONB(u, v, w);
	}

	public static ONB fromW(final Vec3 w_) {
		final Vec3 /* normalized */w = w_.normalized();
		Vec3 /* normalized */u = w.cross(Vec3.X);
		if (u.sdot < EPS)
			u = w.cross(Vec3.Y);
		final Vec3 /* normalized */v = w.cross(u);
		return new ONB(u, v, w);
	}

	public static ONB fromUV(final Vec3 u_, final Vec3 v_) {
		final Vec3 /* normalized */u = u_.normalized();
		final Vec3 /* normalized */w = u.cross(v_).normalized();
		final Vec3 /* normalized */v = w.cross(u);
		return new ONB(u, v, w);
	}

	public static ONB fromVU(final Vec3 v_, final Vec3 u_) {
		final Vec3 /* normalized */v = v_.normalized();
		final Vec3 /* normalized */w = u_.cross(v).normalized();
		final Vec3 /* normalized */u = v.cross(w);
		return new ONB(u, v, w);
	}

	public static ONB fromUW(final Vec3 u_, final Vec3 w_) {
		final Vec3 /* normalized */u = u_.normalized();
		final Vec3 /* normalized */v = w_.cross(u).normalized();
		final Vec3 /* normalized */w = u.cross(v);
		return new ONB(u, v, w);
	}

	public static ONB fromWU(final Vec3 w_, final Vec3 u_) {
		final Vec3 /* normalized */w = w_.normalized();
		final Vec3 /* normalized */v = w.cross(u_).normalized();
		final Vec3 /* normalized */u = v.cross(w);
		return new ONB(u, v, w);
	}

	public static ONB fromVW(final Vec3 v_, final Vec3 w_) {
		final Vec3 /* normalized */v = v_.normalized();
		final Vec3 /* normalized */u = v.cross(w_).normalized();
		final Vec3 /* normalized */w = u.cross(v);
		return new ONB(u, v, w);
	}

	public static ONB fromWV(final Vec3 w_, final Vec3 v_) {
		final Vec3 /* normalized */w = w_.normalized();
		final Vec3 /* normalized */u = v_.cross(w).normalized();
		final Vec3 /* normalized */v = w.cross(u);
		return new ONB(u, v, w);
	}

	public final Vec3 combine(final float r, final float s, final float t) {
		return u.scale(r).combine(s, v).combine(t, w);
	}

	@Override
	public final int hashCode() {
		return u.hashCode() ^ v.hashCode() ^ w.hashCode();
	}

	@Override
	public final boolean equals(final Object obj) {
		if (obj instanceof ONB) {
			final ONB cobj = (ONB) obj;
			return cobj.u.equals(u) && cobj.v.equals(v) && cobj.w.equals(w);
		}
		return false;
	}

}
