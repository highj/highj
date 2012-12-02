package org.highj.function;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;

public abstract class F4<A, B, C, D, E> extends F3<A, B, C, F1<D, E>> {

    public abstract E $(A a, B b, C c, D d);

    @Override
    public F1<D, E> $(final A a, final B b, final C c) {
        return new F1<D, E>() {
            @Override
            public E $(D d) {
                return F4.this.$(a, b, c,d);
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C, D, E> F4<A, B, C, D, E> narrow4(_<__.µ<µ, A>, _<__.µ<µ, B>, _<__.µ<µ, C>, _<__.µ<µ, D>, E>>>> function) {
        return (F4) function;
    }

    public F0<E> lazy(A a, B b, C c, D d) {
        return F0.lazy(this, a, b, c, d);
    }

    public F1<T4<A, B, C, D>, E> uncurry4() {
        return new F1<T4<A, B, C, D>, E>() {

            @Override
            public E $(T4<A, B, C, D> quadruple) {
                return F4.this.$(quadruple._1(), quadruple._2(), quadruple._3(), quadruple._4());
            }
        };
    }

    public static <A, B, C, D, E> F4<A, B, C, D, E> curry4(final F1<T4<A, B, C, D>, E> fn) {
        return new F4<A, B, C, D, E>() {
            @Override
            public E $(A a, B b, C c, D d) {
                return fn.$(Tuple.of(a, b, c, d));
            }
        };
    }

    @Override
    public String toString(){
        return "<F4>";
    }
}
