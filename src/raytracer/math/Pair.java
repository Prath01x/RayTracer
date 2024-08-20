package raytracer.math;

public class Pair<T, U> {
	public final T a;
	public final U b;

	public Pair(final T a, final U b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public int hashCode() {
		return a.hashCode() ^ b.hashCode();
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof Pair<?, ?>))
			return false;

		final Pair<?, ?> p = (Pair<?, ?>)o;
		return a.equals(p.a) && b.equals(p.b);
	}
}
