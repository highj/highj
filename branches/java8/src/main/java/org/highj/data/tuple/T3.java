package org.highj.data.tuple;

import org.highj.___;

import java.util.function.Function;

/**
 * A tuple of arity 3, a.k.a. "triple".
 */
public abstract class T3<A, B, C> implements ___<T3.µ, A, B, C> {
    public static class µ {
    }

    public abstract A _1();

    public abstract B _2();

    public abstract C _3();

    public <AA> T3<AA, B, C> map_1(Function<? super A, ? extends AA> fn) {
        return Tuple.of(fn.apply(_1()), _2(), _3());
    }

    public <BB> T3<A, BB, C> map_2(Function<? super B, ? extends BB> fn) {
        return Tuple.of(_1(), fn.apply(_2()), _3());
    }

    public <CC> T3<A, B, CC> map_3(Function<? super C, ? extends CC> fn) {
        return Tuple.of(_1(), _2(), fn.apply(_3()));
    }

    @Override
    public int hashCode() {
        return 31 * _1().hashCode() + 37 * _2().hashCode() + 41 * _3().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T3) {
            T3<?, ?, ?> that = (T3) o;
            return this._1().equals(that._1()) && this._2().equals(that._2()) && this._3().equals(that._3());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", _1(), _2(), _3());
    }
}