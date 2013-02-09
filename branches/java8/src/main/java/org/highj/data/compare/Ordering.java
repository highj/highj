package org.highj.data.compare;

import org.highj.typeclass0.group.Group;

public enum Ordering {
    LT, EQ, GT;

    public int cmpResult() {
        return ordinal() - 1;
    }

    public static Group<Ordering> group = new Group<Ordering>(){

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
            switch(ordering) {
                case EQ: return EQ;
                case LT: return GT;
                case GT: return LT;
                default: throw new AssertionError();
            }
        }
    };
}
