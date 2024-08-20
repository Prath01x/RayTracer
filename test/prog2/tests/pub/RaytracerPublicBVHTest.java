package prog2.tests.pub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static prog2.tests.TestUtil.DEFAULT_TIMEOUT;

import org.junit.Before;
import org.junit.Test;

import prog2.tests.BVHExercise;
import prog2.tests.PublicTest;
import prog2.tests.RayTracerTestBase;
import raytracer.core.Obj;
import raytracer.core.def.BVHBase;
import raytracer.geom.BBox;
import raytracer.math.Point;
import raytracer.math.Vec3;

public class RaytracerPublicBVHTest extends RayTracerTestBase implements PublicTest, BVHExercise {

	private BVHBase studentBvh;

	@Before
	public void setUp() {
		studentBvh = createStudentBVH();
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testBVH_One() {
		printCurrentMethodName();
		final Obj obj = createSomeObject();
		studentBvh.add(obj);
		studentBvh.buildBVH();
		assertTrue(studentBvh.getObjects().size() == 1);
		assertTrue(studentBvh.getObjects().get(0).equals(obj));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testBVH_SplitDimX() {
		printCurrentMethodName();
		assertEquals("Split dimension X not correct", 0,
				studentBvh.calculateSplitDimension(new Vec3(1.0f, 0.0f, 0.0f)));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testBVH_BBox1() {
		printCurrentMethodName();
		final Obj object = createSomeObject(createSphere(new Point(1, 2, 3), 4));
		final BBox box = object.bbox();
		studentBvh.add(object);
		assertTrue("BVH bbox not equal", box.equals(studentBvh.bbox()));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testBVH_MinMax1() {
		printCurrentMethodName();
		final Obj object = createSomeObject(createSphere(new Point(1, 2, 3), 4));
		final BBox box = object.bbox();
		studentBvh.add(object);
		final Point minMax = studentBvh.calculateMaxOfMinPoints();
		assertTrue("BVH MinMax not equal", box.getMin().equals(minMax));
	}
}
