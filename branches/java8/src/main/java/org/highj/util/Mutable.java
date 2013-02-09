package org.highj.util;


import java.util.function.Supplier;

/**
 * A wrapper for a mutable value
 */
public class Mutable<A> implements Supplier<A> {

    private A value;

    public static <A> Mutable<A> Mutable() {
        return new Mutable<A>();
    }

    public static <A> Mutable<A> Mutable(A a) {
        Mutable<A> mutable = new Mutable<A>();
        mutable.set(a);
        return mutable;
    }

    public void set(A a) {
        value = a;
    }

    public A get() {
        return value;
    }
}
