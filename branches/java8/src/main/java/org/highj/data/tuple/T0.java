package org.highj.data.tuple;

import org.highj.data.tuple.t0.T0Group;
import org.highj.typeclass0.group.Group;

/**
 * A tuple of arity 0, a.k.a. "unit".
 * It often plays a role similar to <code>void</code>.
 */
public enum T0 {
    unit;

    @Override
    public String toString() {
        return "()";
    }

    public static final Group<T0> group = new T0Group();
}
