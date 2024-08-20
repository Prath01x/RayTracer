package raytracer.geom;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.core.def.LazyHitTest;
import raytracer.math.Constants;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec2;
import raytracer.math.Vec3;

class Triangle extends BBoxedPrimitive {

	private final Point m;
	private final Vec3 u, v, n;

	public Triangle(final Point a, final Point b, final Point c) {
		super(BBox.create(a.min(b, c), a.max(b, c)));
		this.m = a;
		this.u = b.sub(a);
		this.v = c.sub(a);
		this.n = v.cross(u).normalized();
	}

	@Override
	public Hit hitTest(final Ray ray, final Obj obj, final float tmin, final float tmax) {
        /* We use an anonymous class:
         * instead of implementing a class that extends LazyHitTest 
         * we implement all abstract methods of LazyHitTest on-the-fly.
         * This is possible since we don't need the class anywhere else and don't need to refer to it anywhere by name.
         */
		return new LazyHitTest(obj) {
            /* 
            * The abstract class LazyHitTest itself is not specific to the geometric object triangle.
            * This implementation of the class on the other hand is specific to it.
            */
			private Point point = null;
			private float r, s, t;

			@Override
			public float getParameter() {
				return r;
			}

			@Override
			public Point getPoint() {
				if (point == null)
					point = ray.eval(r).add(n.scale(0.0001f));
				return point;
			}

			@Override
			protected boolean calculateHit() {
				final Vec3 /* normalized */dir = ray.dir();
				final Vec3 pvec = dir.cross(v);
				final float det = pvec.dot(u);

				if (Constants.isZero(det))
					return false;

				final float invDet = 1 / det;
				final Vec3 tvec = ray.base().sub(m);

				s = tvec.dot(pvec) * invDet;
				if (s < 0.0 || s > 1.0)
					return false;

				final Vec3 qvec = tvec.cross(u);
				t = dir.dot(qvec) * invDet;
				if (t < 0.0 || (s + t) > 1.0)
					return false;

				if (t < tmin || t > tmax)
					return false;

				r = v.dot(qvec) * invDet;
				return r >= Constants.EPS;
			}

			@Override
			public Vec2 getUV() {
				return new Vec2(s, t);
			}

			@Override
			public Vec3 getNormal() {
				return n;
			}

		};
	}

	@Override
	public int hashCode() {
		return m.hashCode() ^ u.hashCode() ^ v.hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Triangle) {
			final Triangle cobj = (Triangle) other;
			return cobj.m.equals(m) && cobj.u.equals(u) && cobj.v.equals(v);
		}
		return false;
	}

}
