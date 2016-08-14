package org.highj.data.tuple;

import org.highj.data.ord.Ordering;
import org.highj.data.HList;
import org.highj.data.eq.Eq;
import org.highj.data.ord.Ord;
import org.highj.typeclass0.group.Group;

/**
 * An immutable tuple of arity 0, a.k.a. "unit".
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

    /**
     * Represents the tuple as heterogenous list.
     * @return the {@link HList}
     */
    public HList.HNil toHlist() { return HList.nil; }

    /**
     * The {@link Eq} instance.
     */
    public static final Eq<T0> eq =  (one, two) -> true;

    /**
     * The {@link Ord} instance.
     */
    public static final Ord<T0> ord =  (one, two) -> Ordering.EQ;

    /**
     * The {@link Group} instance.
     */
    public static final Group<T0> group = Group.create(
       unit, (x,y) -> unit, z -> unit);
}
