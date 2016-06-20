package org.highj.data.compare;

import org.highj.data.compare.ordering.OrderingGroup;
import org.highj.typeclass0.group.Group;

public enum Ordering {
    LT, EQ, GT;

    public int cmpResult() {
        return ordinal() - 1;
    }

    public Ordering andThen(Ordering that) {
        return this == EQ ? that : this;
    }

    public Ordering andThen(boolean x, boolean y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public Ordering andThen(char x, char y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public Ordering andThen(byte x, byte y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public Ordering andThen(short x, short y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public Ordering andThen(int x, int y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public Ordering andThen(long x, long y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public Ordering andThen(float x, float y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public Ordering andThen(double x, double y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public <T extends Comparable<? super T>> Ordering andThen(T x, T y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    public static Group<Ordering> group = new OrderingGroup();

    public static Ordering compare(boolean x, boolean y) {
        return x == y ? EQ : !x ? LT : GT;
    }

    public static Ordering compare(char x, char y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    public static Ordering compare(byte x, byte y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    public static Ordering compare(short x, short y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    public static Ordering compare(int x, int y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    public static Ordering compare(long x, long y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    public static Ordering compare(float x, float y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    public static Ordering compare(double x, double y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    public static <T extends Comparable<? super T>> Ordering compare(T x, T y) {
        return fromInt(x.compareTo(y));
    }

    public static Ordering fromInt(int compareResult) {
        return compare(compareResult, 0);
    }

}
