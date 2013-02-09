package org.highj.data.tuple;

import org.highj._;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.highj.data.tuple.Tuple.of;

/**
 * A tuple of arity 1, a.k.a. "cell" or "Id".
 */
public abstract class T1<A> implements  _<T1.µ, A>, Supplier<A> {
    public static class µ {}

    @Override
    public A get() {
        return _1();
    }

    public abstract A _1();

    @Override
    public String toString() {
        return String.format("(%s)", _1());
    }

    public <B> T1<B> map(Function<? super A, ? extends B> fn) {
        return of(fn.apply(_1()));
    }

    public <B> T1<B> ap(T1<Function<A, B>> nestedFn) {
        return map(Tuple.narrow(nestedFn)._1());
    }

    @Override
    public int hashCode() {
        return _1().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T1) {
            T1<?> that = (T1) o;
            return this._1().equals(that._1());
        }
        return false;
    }

}
