package prog2.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import prog2.tests.pub.RandomGenerator;
import raytracer.core.Camera;
import raytracer.core.LightSource;
import raytracer.core.OBJReader;
import raytracer.core.Obj;
import raytracer.core.PerspectiveCamera;
import raytracer.core.Scene;
import raytracer.core.Shader;
import raytracer.core.Trace;
import raytracer.core.def.Accelerator;
import raytracer.core.def.BVH;
import raytracer.core.def.BVHBase;
import raytracer.core.def.PointLightSource;
import raytracer.core.def.SimpleAccelerator;
import raytracer.core.def.StandardObj;
import raytracer.core.def.StandardScene;
import raytracer.geom.GeomFactory;
import raytracer.geom.Primitive;
import raytracer.math.Color;
import raytracer.math.Constants;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec3;
import raytracer.shade.ShaderFactory;
import raytracer.shade.SingleColor;

public abstract class RayTracerTestBase {

	public static void printCurrentMethodName() {
		System.out.println("Running "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ ".");
	}

	public static interface ObjectEmitter {
		public Object emit(int id);
	}

	public static interface EqualityChecker {
		public boolean checkCorrectEquality(Object f, Object s);
	}

	public boolean testEquality(final int numberObjects, final ObjectEmitter emitter,
			final EqualityChecker checker) {
		// dynamic test
		final Object source = emitter.emit(0);
		for (int i = 1; i < numberObjects; ++i) {
			final Object target = emitter.emit(i);
			if (!checker.checkCorrectEquality(source, target))
				return false;
		}
		return true;
	}

	public boolean createObjectWithException(final Class<?> exception,
			final ObjectEmitter emitter) {
		try {
			emitter.emit(0);
		} catch (final Exception ex) {
			return ex.getClass() == exception;
		}
		return false;
	}

	public boolean createPhongWithException(final Class<?> exception,
			final Shader inner, final Color ambient, final float diffuse,
			final float specular, final float shininess) {
		return createObjectWithException(exception, new ObjectEmitter() {

			@Override
			public Object emit(final int id) {
				return createPhong(inner, ambient, diffuse, specular, shininess);
			}
		});
	}

	public boolean createCheckerBoardWithException(final Class<?> exception,
			final Shader a, final Shader b, final float scale) {
		return createObjectWithException(exception, new ObjectEmitter() {

			@Override
			public Object emit(final int id) {
				return createCheckerBoard(a, b, scale);
			}
		});
	}

	protected final static BVHBase createStudentBVH() {
		return new BVH();
	}

	protected final static Point createRandomPoint() {
		return new Point(RandomGenerator.nextFloat(100.0f),
				RandomGenerator.nextFloat(100.0f),
				RandomGenerator.nextFloat(100.0f));
	}

	protected static void readOBJ(final String filename, final Accelerator student, final SingleColor shader, final float scale, final Vec3 translate) throws FileNotFoundException {
		final InputStream in = new BufferedInputStream(new FileInputStream(filename));
		OBJReader.read(in, student, shader, scale, translate);
	}

	protected Obj createSomeObject(final Primitive prim) {
		return new StandardObj(prim, new SingleColor(Color.WHITE));
	}

	protected Obj createSomeObject() {
		return createSomeObject(createSphere(createRandomPoint(),
				RandomGenerator.nextFloat(42.0f)));
	}

	protected Shader createPhong(final Shader inner, final Color ambient, final float diffuse,
			final float specular, final float shininess) {
		return ShaderFactory.createPhong(inner, ambient, diffuse, specular, shininess);
	}

	protected Shader createCheckerBoard(final Shader a, final Shader b, final float scale) {
		return ShaderFactory.createCheckerBoard(a, b, scale);
	}

	protected Primitive createSphere(final Point m, final float r) {
		return GeomFactory.createSphere(m, r);
	}

	protected Primitive createTriangle(final Point a, final Point b, final Point c) {
		return GeomFactory.createTriangle(a, b, c);
	}

	protected Primitive createPlane(final Point a, final Point b, final Point c) {
		return GeomFactory.createPlane(a, b, c);
	}

	protected Primitive createPlane(final Vec3 /* normalized */n, final Point supp) {
		return GeomFactory.createPlane(n, supp);
	}

	public StandardScene getScene(final Obj sceneObj, final LightSource... lights) {
		final Camera cam = new PerspectiveCamera(new Point(0, 0, -20),
				Point.ORIGIN, new Vec3(0, 1, 0), 3, 4, 3);
		final Accelerator accel = new SimpleAccelerator();

		accel.add(sceneObj);

		return new StandardScene(cam, Arrays.asList(lights), accel);
	}

	public void checkColorCheckerboardSimple(final Shader s1, final Shader s2, final Color c1,
			final Color c2) {
		final Primitive plane = createPlane(new Vec3(0f, 0f, -1f), Point.ORIGIN);
		final Shader shader = createCheckerBoard(s1, s2, 1f);
		final Obj checkerboard = new StandardObj(plane, shader);

		final LightSource light = new PointLightSource(new Point(0, 0, -10), Color.WHITE);
		final Scene       scene = getScene(checkerboard, light);

		Ray ray = new Ray(new Point(0.5f, 0.5f, -10f), Vec3.Z);
		Color c = Trace.primary(scene, ray).shade();
		assertTrue(
				"Color should be " + c1.toString() + " but was " + c.toString(),
				c.equals(c1));

		ray = new Ray(new Point(1f, 1f, -10f), Vec3.Z);
		c = Trace.primary(scene, ray).shade();
		assertTrue(
				"Color should be " + c1.toString() + " but was " + c.toString(),
				c.equals(c1));

		ray = new Ray(new Point(7f, 7f, -10f), Vec3.Z);
		c = Trace.primary(scene, ray).shade();
		assertTrue(
				"Color should be " + c1.toString() + " but was " + c.toString(),
				c.equals(c1));

		ray = new Ray(new Point(-42f, 1f, -10f), Vec3.Z);
		c = Trace.primary(scene, ray).shade();
		assertTrue(
				"Color should be " + c2.toString() + " but was " + c.toString(),
				c.equals(c2));
	}

	public interface PhongTestEmitter {

		Shader emitShader();

		Scene emitScene(Obj obj);

	}

	public void checkPhong(final Color c1, final Color c2, final Color c3,
			final PhongTestEmitter emitter) {
		//final Primitive plane = createPlane(new Vec3(0f, 0f, -1f), Point.ORIGIN);
		final float sz = 200f;
		final Primitive plane 
		 = createTriangle(new Point( sz, sz, 0), new Point( -sz, sz, 0), new Point( sz, -sz, 0));
		final Shader shader = emitter.emitShader();

		final Obj phongPlane = new StandardObj(plane, shader);

		final Scene scene = emitter.emitScene(phongPlane);

		Ray ray = new Ray(new Point(0.5f, 0f, -10f), Vec3.Z);
		Color c = Trace.primary(scene, ray).shade();
		assertTrue(
				"Color should be " + c1.toString() + " but was " + c.toString(),
				colorEqualsPhong(c, c1));

		ray = new Ray(new Point(10f, 0f, -10f), Vec3.Z);
		c = Trace.primary(scene, ray).shade();
		assertTrue(
				"Color should be " + c2.toString() + " but was " + c.toString(),
				colorEqualsPhong(c, c2));

		ray = new Ray(new Point(70, 0f, -10f), Vec3.Z);
		c = Trace.primary(scene, ray).shade();
		assertTrue(
				"Color should be " + c3.toString() + " but was " + c.toString(),
				colorEqualsPhong(c, c3));

	}

	public void checkDefaultPhong(final Shader inner, final Color ambient,
			final float diffuse, final float specular, final float shininess,
			final Color c1, final Color c2, final Color c3) {
		checkPhong(c1, c2, c3, new PhongTestEmitter() {

			@Override
			public Shader emitShader() {
				return createPhong(inner, ambient, diffuse, specular, shininess);
			}

			@Override
			public Scene emitScene(final Obj obj) {
				final LightSource light = new PointLightSource(new Point(0, 0, -10), Color.WHITE);
				return getScene(obj, light);
			}
		});
	}

	public boolean colorEqualsPhong(final Color ref, final Color test) {
		final float EPSI = 0.2f;
		return Constants.isEqual(ref.x(), test.x(), EPSI)
				&& Constants.isEqual(ref.y(), test.y(), EPSI)
				&& Constants.isEqual(ref.z(), test.z(), EPSI);
	}
}
