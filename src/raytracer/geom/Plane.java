package raytracer.geom;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.core.def.LazyHitTest;
import raytracer.math.Constants;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec2;
import raytracer.math.Vec3;

import java.util.Objects;

public class Plane extends BBoxedPrimitive {
    private Vec3 cutter;
    private Point jamm;
    private Vec2 uv;

    public Plane(Vec3 normal, Point support) {
        this.cutter = normal.normalized();
        this.jamm = support;
    }

    @Override
    public Hit hitTest(Ray ray, Obj obj, float tmin, float tmax) {
        return new LazyHitTest(obj) {
            private Point point = null;
            private float t;

            @Override
            public float getParameter() {
                return t;
            }

            @Override
            public Point getPoint() {
                if (point == null)
                    point = ray.eval(tmin);
                return point;
            }

            @Override
            protected boolean calculateHit() {
                float denominator = ray.dir().dot(cutter);

                if (Constants.isZero(denominator))
                    return false;

                float numerator = (jamm.sub(ray.base())).dot(cutter);
                t = numerator / denominator;

                if (t < tmin || t > tmax)
                    return false;

                point = ray.eval(t);
                uv = Util.computePlaneUV(cutter, jamm, point);
                return true;
            }

            @Override
            public Vec2 getUV() {
                return uv;
            }

            @Override
            public Vec3 getNormal() {
                return cutter;
            }

        };
    }

    @Override
    public int hashCode() {
        return Objects.hash(cutter, jamm);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Plane plane = (Plane) other;
        return jamm.equals(plane.jamm) && cutter.equals(plane.cutter);
    }
}