package prog2.tests.pub;

import static org.junit.Assert.assertTrue;
import static prog2.tests.TestUtil.DEFAULT_TIMEOUT;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import prog2.tests.PublicTest;
import prog2.tests.RayTracerTestBase;
import prog2.tests.ReadObjExercise;
import raytracer.core.def.StandardObj;
import raytracer.geom.Primitive;
import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Vec3;
import raytracer.shade.SingleColor;

public class RaytracerPublicReadObjTest extends RayTracerTestBase implements PublicTest, ReadObjExercise {

	@Before
	public void setUp() {
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testReadSimple() throws FileNotFoundException {
		printCurrentMethodName();
		final ComparableAccelarator student = new ComparableAccelarator();
		final ComparableAccelarator ref = new ComparableAccelarator();
		getSimple(ref);
		readOBJ("obj/simple.obj", student, new SingleColor(Color.GRAY), 1.0f, new Vec3(0, 0, 0));
		assertTrue(
				"Simple Triangle was not read succesfully (or equals in Triangle is broken)",
				student.equals(ref));
	}

	private void getSimple(final ComparableAccelarator ref) {
		Primitive t;

		t = createTriangle(new Point(-1f, 0f, -1f), new Point(1f, 0f, -1f),
				new Point(-1f, 0f, 1f));
		ref.add(new StandardObj(t, new SingleColor(Color.GRAY)));

	}

}
