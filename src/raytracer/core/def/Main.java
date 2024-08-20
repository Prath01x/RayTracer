package raytracer.core.def;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import raytracer.core.Camera;
import raytracer.core.LightSource;
import raytracer.core.OBJReader;
import raytracer.core.Obj;
import raytracer.core.PerspectiveCamera;
import raytracer.core.Renderer;
import raytracer.core.Scene;
import raytracer.core.Shader;
import raytracer.geom.GeomFactory;
import raytracer.geom.Primitive;
import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Vec3;
import raytracer.shade.ShaderFactory;
import raytracer.shade.SingleColor;

public class Main {

	private static class MyPanel extends JPanel {

		private final BufferedImage img;

		public MyPanel(final int w, final int h) {
			img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(img.getWidth(), img.getHeight());
		}

		public void drawPacket(final int x, final int y, final int w, final int h, final int[] data) {
			img.setRGB(x, y, w, h, data, 0, w);
		}

		@Override
		public void paintComponent(final Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
		}
	}

	public static void main(final String[] args) {
		final int xRes = 640, yRes = 480, packet = 16;
		final MyPanel panel = new MyPanel(xRes, yRes);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame f = new JFrame("Prog2 Raytracer");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(panel);
				f.pack();
				f.setVisible(true);
			}
		});
		final boolean implementedPlane = false; // TODO implement Plane
		final boolean implementedCheckerBoard = false; // TODO implement CheckerBoard
		final boolean implementedSphere = false; // TODO implement Sphere
		final boolean implementedPhong = false; // TODO implement Phong
		final boolean implementedOBJReader = false; // TODO implement OBJReader
		final boolean implementedBVH = false; // TODO implement BVH
		final LightSource ls = new PointLightSource(new Point(-10, 10, -10), Color.WHITE);
		final Color ambient = Color.WHITE.scale(0.05f);
		final Camera cam = new PerspectiveCamera(new Point(0, 4, -10), Point.ORIGIN, new Vec3(0, 5, 0), 3, 4, 3);
		final Accelerator accel = new SimpleAccelerator();

		{
			final Primitive tri = GeomFactory.createTriangle(new Point(-3, .5f, -1.5f), new Point(-1, 2.5f, -1.5f),
					new Point(1, .5f, -1.5f));
			final Shader yellow = new SingleColor(Color.YELLOW);
			final Obj triangle = new StandardObj(tri, yellow);
			accel.add(triangle);
		}

		if (implementedPlane) {
			final Primitive plane = GeomFactory.createPlane(Vec3.Y, Point.ORIGIN);
			final Shader black = new SingleColor(Color.BLACK);
			final Shader white = new SingleColor(Color.WHITE);
			final Shader shader = implementedCheckerBoard ? ShaderFactory.createCheckerBoard(black, white, 2f) : white;
			final Obj triangle = new StandardObj(plane, shader);
			accel.add(triangle);
		}

		if (implementedSphere) {
			{
				final Primitive prim = GeomFactory.createSphere(new Point(0, 1, 0), 1);
				final Shader blue = new SingleColor(Color.BLUE);
				final Shader shader = implementedPhong ? ShaderFactory.createPhong(blue, ambient, 0.4f, 1.0f, 15)
						: blue;
				final Obj sphere = new StandardObj(prim, shader);
				accel.add(sphere);
			}

			{
				final Primitive prim = GeomFactory.createSphere(new Point(1, 1.3f, 0), 1);
				final Shader red = new SingleColor(Color.RED);
				final Shader shader = implementedPhong ? ShaderFactory.createPhong(red, ambient, 0.4f, 1.0f, 15) : red;
				final Obj sphere = new StandardObj(prim, shader);
				accel.add(sphere);
			}
		}

		if (implementedOBJReader) {
			final BVH bvh = implementedBVH ? new BVH() : null;
			try {
				final String filename;
				final float scale;
				if (implementedBVH) {
					filename = "obj/bunny.obj";
					scale = 25;
				} else {
					filename = "obj/pyramid.obj";
					scale = 1;
				}

				final Shader green = new SingleColor(Color.GREEN);
				final Shader shader = implementedPhong ? ShaderFactory.createPhong(green, ambient, 1.f, .5f, 50)
						: green;
				OBJReader.read(filename, bvh != null ? bvh : accel, shader, scale, new Vec3(-3, 0, 0));
			} catch (final FileNotFoundException e) {
				System.err.println(e);
				return;
			}

			if (bvh != null) {
				bvh.buildBVH();
				accel.add(bvh);
			}
		}

		final List<LightSource> lights = new ArrayList<LightSource>();
		lights.add(ls);

		final Scene scene = new StandardScene(cam, lights, accel);
		final Renderer r = new Renderer(scene, xRes, yRes, 2);

		final Executor exe = Executors.newFixedThreadPool(2);
		final CompletionService<Renderer.Work> ecs = new ExecutorCompletionService<Renderer.Work>(exe);
		int num = 0;
		for (int x = 0; x < xRes; x += packet) {
			for (int y = 0; y < yRes; y += packet) {
				ecs.submit(r.render(x, y, packet, packet));
				num = num + 1;
			}
		}

		for (int i = 0; i < num; i++) {
			try {
				final Renderer.Work w = ecs.take().get();
				panel.drawPacket(w.x, w.y, packet, packet, w.pixels);
				if (i % 100 == 0)
					panel.repaint();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			} catch (final ExecutionException e) {
				e.printStackTrace();
			}
		}

		panel.repaint();
		System.out.println("done");
	}

}