package raytracer.geom;

import raytracer.math.ONB;
import raytracer.math.Point;
import raytracer.math.Vec2;
import raytracer.math.Vec3;

public final class Util {
	private Util() {}


	/**
	 * Calculates texture coordinates for a plane
	 *
	 * @param n     The normal of the plane
	 * @param supp  The support point of the plane
	 * @param p     The point to calculate the texture coordinates for
	 * @return      The texture coordinates
	 */
	public static Vec2 computePlaneUV(final Vec3 /* normalized */ n, final Point supp, final Point p) {
		final Vec3 v    = p.sub(supp);
		final ONB  base = ONB.fromW(n);
		return new Vec2(v.dot(base.u()), v.dot(base.v()));
	}

	/**
	 * Calculates texture coordinates for a sphere
	 *
	 * @param radial  A vector from the center of the sphere to where to calculate the texture coordinate for
	 * @return        The texture coordinates
	 */
	public static Vec2 computeSphereUV(final Vec3 radial) {
		final double phi   = Math.acos(radial.z());
		final double theta = Math.atan2(radial.y(), radial.x());
		final float  u     = (float)(phi / (2 * Math.PI));
		final float  v     = (float)((Math.PI - theta) / Math.PI);
		return new Vec2(u, v);
	}
}
