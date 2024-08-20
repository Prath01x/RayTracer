package prog2.tests.pub;

import java.util.Random;

/**
 * A simple random number generator
 */
public final class RandomGenerator {

	private static Random random = new Random();

	private RandomGenerator() {
	}

	/**
	 * Generates a pseudo random number
	 *
	 * @return The generated pseudo random int number
	 */
	public static final int nextInt() {
		return random.nextInt();
	}

	/**
	 * Generates a random new boolean
	 *
	 * @return The generated random new boolean
	 */
	public static final boolean nextBoolean() {
		return random.nextBoolean();
	}

	/**
	 * Generates a random scaled float value
	 *
	 * @param scale
	 *            The scale factor
	 */
	public static final float nextFloat(final float scale) {
		return random.nextFloat() * scale;
	}

}
