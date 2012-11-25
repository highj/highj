package org.highj.data.tuple;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.function.F1;
import org.highj.function.Objects;

/**
 * A tuple of arity 3, a.k.a. "triple".
 */
public abstract class T3<A, B, C> extends ___<T3.µ, A, B, C> {
    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    T3() {
        super(hidden);
    }

    public abstract A _1();

    public abstract B _2();

    public abstract C _3();

    @SuppressWarnings("unchecked")
    public static <A, B, C> T3<A, B, C> narrow(_<__.µ<___.µ<µ, A>, B>, C> value) {
        return (T3) value;
    }

    public <AA> T3<AA,B,C> map_1(F1<A,AA> fn) {
        return Tuple.of(fn.$(_1()), _2(),_3());
    }

    public <BB> T3<A,BB,C> map_2(F1<B,BB> fn) {
        return Tuple.of(_1(), fn.$(_2()),_3());
    }

    public <CC> T3<A,B,CC> map_3(F1<C,CC> fn) {
        return Tuple.of(_1(), _2(), fn.$(_3()));
    }

    @Override
    public int hashCode(){
        return 31*_1().hashCode() + 37*_2().hashCode() + 41*_3().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof T3) {
            T3<?,?,?> that = (T3) o;
            return this._1().equals(that._1()) && this._2().equals(that._2()) && this._3().equals(that._3());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", _1(), _2(), _3());
    }
}