package org.highj.function;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass.group.Monoid;

/**
 * A class representing a binary function.
 */
public abstract class F2<A, B, C> extends F1<A, F1<B, C>> {

    public abstract C $(A a, B b);

    public F0<C> lazy(A a, B b) {
        return F0.lazy(this, a, b);
    }

    @Override
    public F1<B, C> $(final A a) {
        return new F1<B, C>() {

            @Override
            public C $(B b) {
                return F2.this.$(a, b);
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C> F2<A, B, C> narrow2(_<__.µ<µ, A>, _<__.µ<µ, B>, C>> function) {
        return (F2) function;
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C> F1<A, _<__.µ<µ, B>, C>> widen2(F1<A, F1<B, C>> fn) {
        return (F1) fn;
    }

    public F1<T2<A, B>, C> uncurry2() {
        return new F1<T2<A, B>, C>() {

            @Override
            public C $(T2<A, B> pair) {
                return F2.this.$(pair._1(), pair._2());
            }
        };
    }

    public static <A, B, C> F2<A, B, C> curry2(final F1<T2<A, B>, C> fn) {
        return new F2<A, B, C>() {
            @Override
            public C $(A a, B b) {
                return fn.$(Tuple.of(a, b));
            }
        };
    }

    public static <A, B, C, D> F2<A, B, D> compose2(final F1<? super C, ? extends D> f, final F2<? super A, ? super B, ? extends C> g) {
        return new F2<A, B, D>() {

            @Override
            public D $(A a, B b) {
                return f.$(g.$(a, b));
            }
        };
    }

    public <D> F2<A, B, D> andThen2(F1<C, D> that) {
        return compose2(that, this);
    }


    public static <A, B> F2<A, B, A> constant() {
        return new F2<A, B, A>() {
            @Override
            public A $(A a, B b) {
                return a;
            }
        };
    }

    public F2<B, A, C> flip() {
        return new F2<B, A, C>() {
            public C $(B b, A a) {
                return F2.this.$(a, b);
            }
        };
    }

    @Override
    public String toString(){
        return "<F2>";
    }

}
