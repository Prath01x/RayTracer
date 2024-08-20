package prog2.tests.pub;

import java.util.HashSet;
import java.util.Set;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.core.def.Accelerator;
import raytracer.geom.BBox;
import raytracer.math.Ray;

public class ComparableAccelarator extends Accelerator implements
Comparable<ComparableAccelarator> {

	private final Set<Obj> objects = new HashSet<Obj>();

	public ComparableAccelarator() {
	}

	@Override
	public BBox bbox() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Hit hit(final Ray ray, final Obj obj, final float tmin, final float tmax) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void add(final Obj prim) {
		objects.add(prim);
	}

	@Override
	public int compareTo(final ComparableAccelarator other) {
		if (other.objects.size() != objects.size()) {
			return objects.size() - other.objects.size();
		}
		for (final Obj o : objects) {
			if (!other.objects.contains(o)) {
				return -1;
			}
		}

		for (final Obj o : other.objects) {
			if (!objects.contains(o)) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof ComparableAccelarator)) {
			return false;
		}
		return compareTo((ComparableAccelarator) obj) == 0;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (final Obj o : objects) {
			hash = hash * 31 + o.hashCode();
		}
		return hash;
	}

}
