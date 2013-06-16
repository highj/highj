package org.highj.data.tuple;

import org.highj.data.compare.Ordering;
import org.highj.data.tuple.t0.T0Group;
import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass0.group.Group;

/**
 * A tuple of arity 0, a.k.a. "unit".
 * It often plays a role similar to <code>void</code>.
 */
public enum T0 {
    unit;

    /**
     * The nullary tuple - just for consistency
     *
     * @return the nullary tuple, which is the unit value
     */
    public static T0 of() {
        return unit;
    }

    @Override
    public String toString() {
        return "()";
    }

    public static final Eq<T0> eq =  (one, two) -> true;

    public static final Ord<T0> ord =  (one, two) -> Ordering.EQ;

    public static final Group<T0> group = new T0Group();
}
