package prog2.tests.pub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static prog2.tests.TestUtil.DEFAULT_TIMEOUT;

import org.junit.Before;
import org.junit.Test;

import prog2.tests.HitsExercise;
import prog2.tests.PublicTest;
import prog2.tests.RayTracerTestBase;
import raytracer.core.Hit;
import raytracer.core.def.StandardObj;
import raytracer.geom.Primitive;
import raytracer.math.Color;
import raytracer.math.Constants;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec3;
import raytracer.shade.SingleColor;

public class RaytracerPublicHitsTest extends RayTracerTestBase implements PublicTest, HitsExercise {

	@Before
	public void setUp() {
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPlaneNoHitOrthogonal() {
		printCurrentMethodName();
		final Primitive plane = createPlane(new Vec3(1, 0, 0), new Point(0, 0, 0));
		final Hit hit = plane.hit(new Ray(new Point(0, 0, 0), new Vec3(0, 0, 1)),
				new StandardObj(plane, new SingleColor(Color.GRAY)), 0,
				Float.MAX_VALUE);
		assertFalse(
				"Ray should not hit plane with normal vector orthogonal to ray direction",
				hit.hits());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPlaneHitSimple() {
		printCurrentMethodName();
		final Primitive plane = createPlane(new Vec3(0f, 0f, 1f), new Point(0f, 0f,
				1f));
		final Hit hit = plane.hit(new Ray(new Point(0, 0, 0), new Vec3(0, 0, 1)),
				new StandardObj(plane, new SingleColor(Color.GRAY)), 0,
				Float.MAX_VALUE);
		assertTrue("Ray should hit simple orthogonal plane", hit.hits());
		assertTrue("Hit normal should be (0,0,1)", hit.getNormal().equals(new Vec3(0, 0, 1)));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testSphereHitSimple() {
		printCurrentMethodName();
		final Primitive sphere = createSphere(new Point(0, 0, 42), 10);
		final Hit hit = sphere.hit(new Ray(new Point(0, 0, 0), new Vec3(0, 0, 1)),
				new StandardObj(sphere, new SingleColor(Color.GRAY)), 0,
				Float.MAX_VALUE);
		assertTrue("Ray should hit sphere", hit.hits());
		assertTrue("Hit normal should be (0,0,-1)", hit.getNormal().equals(new Vec3(0, 0, -1)));
		assertTrue("Hit distance should be 32.0", Constants.isEqual(hit.getParameter(), 32));
	}
}
