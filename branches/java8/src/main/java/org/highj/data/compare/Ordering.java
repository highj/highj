package org.highj.data.compare;

import org.highj.function.F2;
import org.highj.typeclass.group.Monoid;

public enum Ordering {
    LT, EQ, GT;

    public int cmpResult() {
        return ordinal() - 1;
    }

    public static Monoid<Ordering> monoid = new Monoid<Ordering>(){
        @Override
        public Ordering identity() {
            return EQ;
        }

        @Override
        public F2<Ordering, Ordering, Ordering> dot() {
            return new F2<Ordering,Ordering,Ordering>() {
                @Override
                public Ordering $(Ordering x, Ordering y) {
                    return x == EQ ? y : x;
                }
            };
        }
    };
}
