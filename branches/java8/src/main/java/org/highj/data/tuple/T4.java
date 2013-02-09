package org.highj.data.tuple;

import org.highj.____;

import java.util.function.Function;

public abstract class T4<A, B, C, D> implements  ____<T4.µ, A, B, C, D> {
    public static class µ {}

    public abstract A _1();

    public abstract B _2();

    public abstract C _3();

    public abstract D _4();

    public <AA> T4<AA,B,C,D> map_1(Function<? super A,? extends AA> fn) {
        return Tuple.of(fn.apply(_1()), _2(),_3(),_4());
    }

    public <BB> T4<A,BB,C,D> map_2(Function<? super B,? extends BB> fn) {
        return Tuple.of(_1(), fn.apply(_2()),_3(),_4());
    }

    public <CC> T4<A,B,CC,D> map_3(Function<? super C,? extends CC> fn) {
        return Tuple.of(_1(), _2(), fn.apply(_3()),_4());
    }

    public <DD> T4<A,B,C,DD> map_4(Function<? super D,? extends DD> fn) {
        return Tuple.of(_1(), _2(), _3(), fn.apply(_4()));
    }

    @Override
    public int hashCode(){
        return 31*_1().hashCode() + 37*_2().hashCode() + 41*_3().hashCode() + 43*_4().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof T4) {
            T4<?,?,?,?> that = (T4) o;
            return this._1().equals(that._1()) && this._2().equals(that._2()) &&
                   this._3().equals(that._3()) && this._4().equals(that._4());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s,%s)", _1(), _2(), _3(), _4());
    }
}