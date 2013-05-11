package org.highj.data.tuple.t0;

import org.highj.data.tuple.T0;
import org.highj.typeclass0.group.Group;

public class T0Group implements Group<T0> {
    @Override
    public T0 inverse(T0 t0) {
        return T0.unit;
    }

    @Override
    public T0 identity() {
        return T0.unit;
    }

    @Override
    public T0 dot(T0 x, T0 y) {
        return T0.unit;
    }
}
