package prog2.tests.pub;

import static org.junit.Assert.assertTrue;
import static prog2.tests.TestUtil.DEFAULT_TIMEOUT;

import org.junit.Before;
import org.junit.Test;

import prog2.tests.PublicTest;
import prog2.tests.RayTracerTestBase;
import prog2.tests.ShaderExercise;
import raytracer.math.Color;
import raytracer.shade.SingleColor;

public class RaytracerPublicShaderTest extends RayTracerTestBase implements PublicTest, ShaderExercise {

	@Before
	public void setUp() {
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPhongBlack() {
		printCurrentMethodName();
		checkDefaultPhong(new SingleColor(Color.MAGENTA), Color.BLACK, 0f, 0f,
				0f, Color.BLACK, Color.BLACK, Color.BLACK);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPhongDiffuseSimple() {
		printCurrentMethodName();
		checkDefaultPhong(new SingleColor(Color.MAGENTA), Color.BLACK, 1f, 0f,
				0f, new Color(0.99f, 0f, 0.99f), new Color(0.7f, 0f, 0.7f),
				new Color(0.14f, 0f, 0.14f));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPhongSpecularSimple() {
		printCurrentMethodName();
		checkDefaultPhong(new SingleColor(Color.YELLOW), Color.BLACK, 0f, 1f,
				0f, Color.WHITE, Color.WHITE, Color.WHITE);
	}

	// Shader constructor tests

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPhongConstructor_Null() {
		printCurrentMethodName();
		assertTrue(createPhongWithException(IllegalArgumentException.class,
				null, Color.WHITE, 1.0f, 1.0f, 1.0f));
		assertTrue(createPhongWithException(IllegalArgumentException.class,
				new SingleColor(Color.BLACK), null, 1.0f, 1.0f, 1.0f));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testCheckerBoardSimple() {
		printCurrentMethodName();
		checkColorCheckerboardSimple(new SingleColor(Color.BLACK),
				new SingleColor(Color.WHITE), Color.BLACK, Color.WHITE);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testCheckerBoardConstructor_Null() {
		printCurrentMethodName();
		assertTrue(createCheckerBoardWithException(
				IllegalArgumentException.class, null,
				new SingleColor(Color.RED), 1.0f));
		assertTrue(createCheckerBoardWithException(
				IllegalArgumentException.class, new SingleColor(Color.RED),
				null, 1.0f));
	}
}
