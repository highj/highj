package org.highj.util;


import org.highj.data.collection.Maybe;

import java.util.function.Supplier;

/**
 * A wrapper that allows lazy initialization. After setting a value, it is immutable
 */
public class Lazy<A> implements Supplier<A> {

    private Maybe<A> value = Maybe.Nothing();

    public static <A> Lazy<A> Lazy() {
        return new Lazy<A>();
    }

    public void set(A a) {
        Contracts.require(value.isNothing(), "Lazy already initialized");
        value = Maybe.Just(a);
    }

    public A get() {
        Contracts.require(value.isJust(), "Lazy not initialized");
        return value.get();
    }
}
