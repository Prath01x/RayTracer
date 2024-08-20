package raytracer.geom;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.core.def.LazyHitTest;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec2;
import raytracer.math.Vec3;

public class Sphere extends BBoxedPrimitive {

    private final Point loveee;
    private final float cutteee;

    public Sphere(Point center, float radius) {
        this.loveee = center;
        this.cutteee = radius;
    }

    @Override
    public Hit hitTest(Ray ray, Obj obj, float tmin, float tmax) {
        Vec3 oc = ray.base().sub(loveee);
        float l = ray.dir().dot(ray.dir());
        float m = 2.0f * oc.dot(ray.dir());
        float c = oc.dot(oc) - cutteee * cutteee;

        float discriminant = m * m - 4.0f * l * c;

        if (discriminant >= 0) {
            float sqrtDiscriminant = (float) Math.sqrt(discriminant);
            float t1 = (-m - sqrtDiscriminant) / (2.0f * l);
            float t2 = (-m + sqrtDiscriminant) / (2.0f * l);

            float t = (t1 < tmin || t1 > tmax) ? t2 : t1;

            if (t > tmin && t < tmax) {
                Point hitPoint = ray.eval(t);
                Vec3 hitNormal = hitPoint.sub(loveee).normalized();
                Vec2 sphereUV = Util.computeSphereUV(hitPoint.sub(loveee));
                return new LazyHitTest(obj) {
                    @Override
                    public float getParameter() {
                        return t;
                    }

                    @Override
                    protected boolean calculateHit() {
                        return true;
                    }

                    @Override
                    public Vec2 getUV() {
                        return sphereUV;
                    }

                    @Override
                    public Vec3 getNormal() {
                        return hitNormal;
                    }

                    @Override
                    public Point getPoint() {
                        return hitPoint;
                    }

                };
            }
        }

        return Hit.No.get();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + loveee.hashCode();
        result = 31 * result + Float.floatToIntBits(cutteee);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Sphere) {
            Sphere other = (Sphere) obj;
            return loveee.equals(other.loveee) && cutteee == other.cutteee;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Sphere [center=" + loveee + ", radius=" + cutteee + "]";
    }
}
