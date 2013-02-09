package org.highj.data.fs;

import org.highj._;
import org.highj.__;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class representing an unary function.
 */
public interface F1<A, B> extends  __<F1.µ, A, B>, Function<A,B> {

    @SuppressWarnings("rawtype")

    public static class µ {
    }

    public default Supplier<B> lazy(A a) {
        return Fs.lazy(this, a);
    }

    public default <C> F1<A, C> andThen(_<__.µ<µ, B>, C> that) {
        return Fs.compose_(that, this);
    }

}
