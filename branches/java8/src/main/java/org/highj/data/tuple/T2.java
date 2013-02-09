package org.highj.data.tuple;

import org.highj.__;

import java.util.function.Function;

/**
 * A tuple of arity 2, a.k.a. "pair".
 */
public abstract class T2<A, B> implements __<T2.µ, A, B> {
    public static class µ {
    }

    public abstract A _1();
    public abstract B _2();

    public T2<B, A> swap() {
        return Tuple.of(_2(), _1());
    }

    public <AA> T2<AA,B> map_1(Function<? super A, ? extends AA> fn) {
        return Tuple.of(fn.apply(_1()), _2());
    }

    public <BB> T2<A,BB> map_2(Function<? super B, ? extends BB> fn) {
        return Tuple.of(_1(), fn.apply(_2()));
    }


    @Override
    public String toString() {
        return String.format("(%s,%s)", _1(), _2());
    }

    @Override
    public int hashCode(){
       return 31*_1().hashCode() + 37*_2().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof T2) {
            T2<?,?> that = (T2) o;
            return this._1().equals(that._1()) && this._2().equals(that._2());
        }
        return false;
    }

}
