package org.highj.data.compare.ordering;

import org.highj.data.compare.Ordering;
import org.highj.typeclass0.group.Group;

import static org.highj.data.compare.Ordering.*;

public class OrderingGroup implements Group<Ordering> {

    @Override
    public Ordering identity() {
        return EQ;
    }

    @Override
    public Ordering dot(Ordering x, Ordering y) {
        return x == EQ ? y : x;
    }

    @Override
    public Ordering inverse(Ordering ordering) {
        switch (ordering) {
            case EQ:
                return EQ;
            case LT:
                return GT;
            case GT:
                return LT;
            default:
                throw new AssertionError();
        }
    }
}
