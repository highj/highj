package org.highj.data.compare;

import org.highj.function.F2;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.group.MonoidAbstract;

public enum Ordering {
    LT, EQ, GT;

    public int cmpResult() {
        return ordinal() - 1;
    }

    public static Monoid<Ordering> monoid = new MonoidAbstract<Ordering>(
            new F2<Ordering,Ordering,Ordering>() {
        @Override
        public Ordering $(Ordering x, Ordering y) {
            return x == EQ ? y : x;
        }
    }, EQ);
}
