package org.highj.data.tuple;

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
}
