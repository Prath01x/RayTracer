package raytracer.core;

import raytracer.math.ONB;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec3;

/**
 * A perspective field of view camera
 */
public class PerspectiveCamera implements Camera {

	private final Point pos, corner;
	private final Vec3 across, up;

	/**
	 * Creates a new perspective camera
	 *
	 * @param pos
	 *            The origin of the camera
	 * @param lookAt
	 *            The position to look at
	 * @param up
	 *            The up vector
	 * @param distance
	 *            The distance to the projection plane
	 * @param width
	 *            The width supported by the camera
	 * @param height
	 *            The height supported by the camera
	 */
	public PerspectiveCamera(final Point pos, final Point lookAt, final Vec3 up, final float distance,
			final float width, final float height) {
		this(pos, lookAt.sub(pos), up, distance, width, height);
	}

	/**
	 * Creates a new perspective camera
	 *
	 * @param pos
	 *            The origin of the camera
	 * @param gaze
	 *            The viewing direction of the camera
	 * @param distance
	 *            The distance to the projection plane
	 * @param width
	 *            The width supported by the camera
	 * @param height
	 *            The height supported by the camera
	 */
	public PerspectiveCamera(final Point pos, final Vec3 gaze, final Vec3 up, final float distance,
			final float width, final float height) {
		this.pos = pos;
		final ONB uvw = ONB.fromWV(gaze.neg(), up.neg());
		this.corner = pos.add(uvw.combine(-width / 2, -height / 2, -distance));
		this.across = uvw.u().scale(width);
		this.up = uvw.v().scale(height);
	}

	/**
	 * Casts a ray through the pixels x and y
	 *
	 * @return The casted ray
	 */
	@Override
	public Ray cast(final float x, final float y) {
		final Point pixel = corner.combine(x, across).combine(y, up);
		final Vec3 /* normalized */dir = pixel.sub(pos).normalized();
		return new Ray(pos, dir);
	}

}
