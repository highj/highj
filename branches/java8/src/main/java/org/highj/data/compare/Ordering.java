package org.highj.data.compare;

import org.highj.data.compare.ordering.OrderingGroup;
import org.highj.typeclass0.group.Group;

public enum Ordering {
    LT, EQ, GT;

    public int cmpResult() {
        return ordinal() - 1;
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
        int result = x.compareTo(y);
        return result < 0 ? LT : result > 0 ? GT : EQ;
    }

}
