package prog2.tests.pub;

import static org.junit.Assert.assertTrue;
import static prog2.tests.TestUtil.DEFAULT_TIMEOUT;

import org.junit.Test;

import prog2.tests.HitsExercise;
import prog2.tests.PublicTest;
import prog2.tests.RayTracerTestBase;
import raytracer.math.Point;
import raytracer.math.Vec3;

public class RaytracerPublicHashcodeTest extends RayTracerTestBase implements PublicTest, HitsExercise {

	private boolean testHashCodes(final int numberObjects, final ObjectEmitter emitter) {
		return testEquality(numberObjects, emitter, new EqualityChecker() {

			@Override
			public boolean checkCorrectEquality(final Object f, final Object s) {
				return f.hashCode() == s.hashCode();
			}
		});
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testSphereHashcode() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + ".");
		assertTrue("Hashcode of spheres should be equal for same data",
				testHashCodes(42, new ObjectEmitter() {

					@Override
					public Object emit(final int id) {
						return createSphere(new Point(1f, 2f, 3f), 42.2f);
					}
				}));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPlaneHashcode1() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + ".");
		assertTrue("Hashcode of plane should be equal for same data",
				testHashCodes(42, new ObjectEmitter() {

					@Override
					public Object emit(final int id) {
						return createPlane(new Vec3(42.0f, 43.0f, 100.0f),
								new Point(-4.0f, 3.0f, -312.2f));
					}
				}));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPlaneHashcode2() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + ".");
		assertTrue("Hashcode of plane should be equal for same data",
				testHashCodes(42, new ObjectEmitter() {

					@Override
					public Object emit(final int id) {
						return createPlane(new Point(0.1f, 0.2f, 0.3f),
								new Point(54.2f, 6454.1f, 41232.f), new Point(
										-81232.f, -43.f, 21.f));
					}
				}));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testTriangleHashcode() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + ".");
		assertTrue("Hashcode of triangles should be equal for same data",
				testHashCodes(42, new ObjectEmitter() {

					@Override
					public Object emit(final int id) {
						return createTriangle(new Point(0.1f, 0.2f, 0.3f),
								new Point(54.2f, 6454.1f, 41232.f), new Point(
										-81232.f, -43.f, 21.f));
					}
				}));
	}

}
