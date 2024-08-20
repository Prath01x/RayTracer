package raytracer.core;

import java.util.concurrent.Callable;

import raytracer.math.Color;
import raytracer.math.Ray;

/**
 * The renderer for this project which renders a scene into an image
 */
public class Renderer {

	private final Scene scene;
	private final Camera cam;
	private final int superSample;
	private final float dx, dy, sdx, sdy;

	/**
	 * Creates a new renderer
	 *
	 * @param scene
	 *            The scene to render
	 * @param xRes
	 *            The X target resolution to render the image with
	 * @param yRes
	 *            The Y target resolution to render the image with
	 * @param superSample
	 *            The amount of sumper sampling to use
	 */
	public Renderer(final Scene scene, final int xRes, final int yRes, final int superSample) {
		this.scene = scene;
		this.cam = scene.getCamera();
		this.superSample = superSample;
		this.dx = 1.0f / xRes;
		this.dy = 1.0f / yRes;
		this.sdx = dx / superSample;
		this.sdy = dy / superSample;
	}

	/**
	 * Represents a work instance containing data to compute a part of an image.
	 * The idea of the work class is to split the actual rendering task into
	 * work instances which can be handled in parallel.
	 */
	public class Work {

		public final int[] pixels;
		public final int x, y, w, h;

		public Work(final int x, final int y, final int w, final int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.pixels = new int[w * h];
		}
	}

	/**
	 * Returns a Callable<Work> object which describes the callable task for the
	 * given parameters
	 *
	 * @param sx
	 *            The x point to start
	 * @param sy
	 *            The y point to start
	 * @param w
	 *            The width to use
	 * @param h
	 *            The height to use
	 * @return Callable<Work> object which describes the callable task
	 */
	public Callable<Work> render(final int sx, final int sy, final int w,
			final int h) {
		return new Callable<Work>() {
			@Override
			public Work call() throws Exception {
				final Work work = new Work(sx, sy, w, h);
				int ofs = 0;
				float bx = sx * dx;
				float by = sy * dy;
				for (int y = 0; y < h; y++) {
					bx = sx * dx;
					for (int x = 0; x < w; x++) {

						Color res = Color.BLACK;
						int n = 0;
						float suy = by - superSample / 2.0f * sdy;
						for (int j = 0; j < superSample; j++) {
							float sux = bx - superSample / 2.0f * sdx;
							for (int i = 0; i < superSample; i++) {
								final Ray r = cam.cast(sux, suy);
								final Trace trace = Trace.primary(scene, r);
								final Color c = trace.shade();
								res = res.avg(c, n);
								sux += sdx;
								n++;
							}
							suy += sdy;
						}
						work.pixels[ofs++] = res.rgb();
						bx += dx;
					}
					by += dy;
				}
				return work;
			}
		};
	}

}
