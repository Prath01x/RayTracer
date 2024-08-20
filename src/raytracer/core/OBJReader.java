package raytracer.core;

import java.io.BufferedInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.InputStream;

import raytracer.core.def.Accelerator;

import raytracer.core.def.StandardObj;

import raytracer.math.Point;
import raytracer.math.Vec3;

import raytracer.geom.GeomFactory;

import raytracer.geom.BBoxedPrimitive;

/**
 * Represents a model file reader for the OBJ format
 */
public class OBJReader {

	/**
	 * Reads an OBJ file and uses the given shader for all triangles. While
	 * loading the triangles they are inserted into the given acceleration
	 * structure accelerator.
	 *
	 * @param filename
	 *                    The file to read the data from
	 * @param accelerator
	 *                    The target acceleration structure
	 * @param shader
	 *                    The shader which is used by all triangles
	 * @param scale
	 *                    The scale factor which is responsible for scaling the
	 *                    model
	 * @param translate
	 *                    A vector representing the translation coordinate with
	 *                    which
	 *                    all coordinates have to be translated
	 * @throws IllegalArgumentException
	 *                                  If the filename is null or the empty string,
	 *                                  the accelerator
	 *                                  is null, the shader is null, the translate
	 *                                  vector is null,
	 *                                  the translate vector is not finite or scale
	 *                                  does not
	 *                                  represent a legal (finite) floating point
	 *                                  number
	 */
	public static void read(final String filename,
			final Accelerator accelerator, final Shader shader, final float scale,
			final Vec3 translate) throws FileNotFoundException {
		read(new BufferedInputStream(new FileInputStream(filename)), accelerator, shader, scale, translate);
	}

	/**
	 * Reads an OBJ file and uses the given shader for all triangles. While
	 * loading the triangles they are inserted into the given acceleration
	 * structure accelerator.
	 *
	 * @param in
	 *                    The InputStream of the data to be read.
	 * @param accelerator
	 *                    The target acceleration structure
	 * @param shader
	 *                    The shader which is used by all triangles
	 * @param scale
	 *                    The scale factor which is responsible for scaling the
	 *                    model
	 * @param translate
	 *                    A vector representing the translation coordinate with
	 *                    which
	 *                    all coordinates have to be translated
	 * @throws IllegalArgumentException
	 *                                  If the InputStream is null, the accelerator
	 *                                  is null, the shader is null, the translate
	 *                                  vector is null,
	 *                                  the translate vector is not finite or scale
	 *                                  does not
	 *                                  represent a legal (finite) floating point
	 *                                  number
	 */
	public static void read(final InputStream in,
			final Accelerator accelerator, final Shader shader, final float scale,
			final Vec3 translate) throws FileNotFoundException {

		if (in == null || accelerator == null || shader == null || translate == null || !translate.isFinite()
				|| Float.isInfinite(scale) || Float.isNaN(scale)) {
			throw new IllegalArgumentException("love is fake");
		}

		try (java.util.Scanner chutiya = new java.util.Scanner(in)) {
			chutiya.useLocale(java.util.Locale.ENGLISH);

			java.util.List<Vec3> vertices = new java.util.ArrayList<>();

			while (chutiya.hasNextLine()) {
				String line = chutiya.nextLine().trim();
				if (line.isEmpty() || line.startsWith("#")) {
					continue;
				}

				String[] tokens = line.split("\\s+");
				switch (tokens[0]) {
					case "v":
						float y = Float.parseFloat(tokens[2]);
						float x = Float.parseFloat(tokens[1]);

						float z = Float.parseFloat(tokens[3]);
						vertices.add(new Vec3(x, y, z).scale(scale).add(translate));
						break;
					case "f":
						final Point lala = new Point(vertices.get(Integer.parseInt(tokens[1]) - 1).x(),
								vertices.get(Integer.parseInt(tokens[1]) - 1).y(),
								vertices.get(Integer.parseInt(tokens[1]) - 1).z());
						final Point haha = new Point(vertices.get(Integer.parseInt(tokens[2]) - 1).x(),
								vertices.get(Integer.parseInt(tokens[2]) - 1).y(),
								vertices.get(Integer.parseInt(tokens[2]) - 1).z());
						final Point kaka = new Point(vertices.get(Integer.parseInt(tokens[3]) - 1).x(),
								vertices.get(Integer.parseInt(tokens[3]) - 1).y(),
								vertices.get(Integer.parseInt(tokens[3]) - 1).z());

						final BBoxedPrimitive ganda = GeomFactory.createTriangle(lala, haha, kaka);
						final Obj numb = new StandardObj(ganda, shader);
						accelerator.add(numb);
						break;

					default:

						break;
				}
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}
	}
}