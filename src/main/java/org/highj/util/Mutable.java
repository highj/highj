package org.highj.util;


import java.util.function.Supplier;

/**
 * A wrapper for a mutable value
 */
public class Mutable<A> implements Supplier<A> {

    private A value;

    public static <A> Mutable<A> newMutable() {
        return new Mutable<>();
    }

    public static <A> Mutable<A> newMutable(A a) {
        Mutable<A> mutable = new Mutable<>();
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
