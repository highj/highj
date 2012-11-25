package org.highj.function;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.Tuple;

/**
 * A class representing a ternary function.
 */
public abstract class F3<A, B, C, D> extends F2<A, B, F1<C, D>> {

    public abstract D $(A a, B b, C c);

    @Override
    public F1<C, D> $(final A a, final B b) {
        return new F1<C, D>() {
            @Override
            public D $(C c) {
                return F3.this.$(a, b, c);
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C, D> F3<A, B, C, D> narrow3(_<__.µ<µ, A>, _<__.µ<µ, B>, _<__.µ<µ, C>, D>>> function) {
        return (F3) function;
    }

    public F0<D> lazy(A a, B b, C c) {
        return F0.lazy(this, a, b, c);
    }

    public F1<T3<A, B, C>, D> uncurry3() {
        return new F1<T3<A, B, C>, D>() {

            @Override
            public D $(T3<A, B, C> triple) {
                return F3.this.$(triple._1(), triple._2(), triple._3());
            }
        };
    }

    public static <A, B, C, D> F3<A, B, C, D> curry3(final F1<T3<A, B, C>, D> fn) {
        return new F3<A, B, C, D>() {
            @Override
            public D $(A a, B b, C c) {
                return fn.$(Tuple.of(a, b, c));
            }
        };
    }

    @Override
    public String toString(){
        return "<F3>";
    }
}
