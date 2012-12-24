package org.highj.data.tuple;

import org.highj._;
import org.highj.__;
import org.highj.function.F2;
import org.highj.typeclass.comonad.Comonad;
import org.highj.typeclass.group.*;
import org.highj.typeclass.monad.Monad;
import org.highj.data.compare.Eq;
import org.highj.function.F1;

/**
 * A tuple of arity 2, a.k.a. "pair".
 */
public abstract class T2<A, B> extends __<T2.µ, A, B> {
    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    T2() {
        super(hidden);
    }

    public abstract A _1();
    public abstract B _2();

    public T2<B, A> swap() {
        return Tuple.of(_2(), _1());
    }

    public <AA> T2<AA,B> map_1(F1<A,AA> fn) {
        return Tuple.of(fn.$(_1()), _2());
    }

    public <BB> T2<A,BB> map_2(F1<B,BB> fn) {
        return Tuple.of(_1(), fn.$(_2()));
    }

    @SuppressWarnings("unchecked")
    public static <A, B> T2<A, B> narrow(_<__.µ<µ, A>, B> value) {
        return (T2) value;
    }

    public static <S> Monad<__.µ<µ, S>> monad(final Monoid<S> monoid) {
        return new Monad<__.µ<µ, S>>() {
            @Override
            public <A, B> _<__.µ<T2.µ, S>, B> map(F1<A, B> fn, _<__.µ<T2.µ, S>, A> nestedA) {
                return T2.narrow(nestedA).map_2(fn);
            }

            @Override
            public <A> _<__.µ<µ, S>, A> pure(A a) {
                return Tuple.of(monoid.identity(), a);
            }

            @Override
            public <A, B> _<__.µ<µ, S>, B> ap(_<__.µ<µ, S>, F1<A, B>> fn, _<__.µ<µ, S>, A> nestedA) {
                T2<S, F1<A, B>> fnPair = narrow(fn);
                T2<S,A> aPair = narrow(nestedA);
                return Tuple.of(monoid.dot(fnPair._1(), aPair._1()), fnPair._2().$(aPair._2()));
            }

            @Override
            public <A, B> _<__.µ<µ, S>, B> bind(_<__.µ<µ, S>, A> nestedA, F1<A, _<__.µ<µ, S>, B>> fn) {
                T2<S,A> ta = narrow(nestedA);
                T2<S,B> tb = narrow(fn.$(ta._2()));
                return Tuple.of(monoid.dot(ta._1(), tb._1()), tb._2());
            }
        };
    }

    public static <S> Comonad<__.µ<µ,S>> comonad() {
        return new Comonad<__.µ<µ,S>>(){
            @Override
            public <A> _<__.µ<µ, S>, _<__.µ<µ, S>, A>> duplicate(_<__.µ<µ, S>, A> nestedA) {
                T2<S,A> pair = narrow(nestedA);
                return Tuple.of(pair._1(), nestedA);
            }

            @Override
            public <A> A extract(_<__.µ<µ, S>, A> nestedA) {
                T2<S,A> pair = narrow(nestedA);
                return pair._2();
            }

            @Override
            public <A, B> _<__.µ<µ, S>, B> map(F1<A, B> fn, _<__.µ<µ, S>, A> nestedA) {
                return T2.narrow(nestedA).map_2(fn);
            }
        };
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

    public static <A,B> Eq<T2<A,B>> eq(final Eq<? super A> eqA, final Eq<? super B> eqB) {
        return new Eq<T2<A,B>>() {

            @Override
            public boolean eq(T2<A,B> one, T2<A,B> two) {
                return eqA.eq(one._1(), two._1()) && eqB.eq(one._2(), two._2());
            }
        };
    }

    private static <A,B> F2<T2<A,B>, T2<A,B>, T2<A,B>> dotFn(final Semigroup<A> semigroupA, final Semigroup<B> semigroupB) {
        return new F2<T2<A,B>, T2<A,B>, T2<A,B>>(){

            @Override
            public T2<A,B> $(T2<A,B> x, T2<A,B> y) {
                return Tuple.of(semigroupA.dot(x._1(), y._1()),semigroupB.dot(x._2(), y._2()));
            }
        };
    }

    private static <A,B> F1<T2<A,B>, T2<A,B>> inverseFn(final Group<A> groupA, final Group<B> groupB) {
        return new F1<T2<A,B>, T2<A,B>>() {

            @Override
            public T2<A,B> $(T2<A,B> x) {
                return Tuple.of(groupA.inverse(x._1()), groupB.inverse(x._2()));
            }
        };
    }

    public static <A,B> Semigroup<T2<A,B>> semigroup(Semigroup<A> semigroupA, Semigroup<B> semigroupB) {
        return () -> dotFn(semigroupA, semigroupB);
    }

    public static <A,B> Monoid<T2<A,B>> monoid(Monoid<A> monoidA, Monoid<B> monoidB) {
        return new Monoid<T2<A,B>>(){
            private T2<A, B> id = Tuple.of(monoidA.identity(), monoidB.identity());

            @Override
            public T2<A, B> identity() {
                return id;
            }

            @Override
            public F2<T2<A, B>, T2<A, B>, T2<A, B>> dot() {
                return dotFn(monoidA, monoidB);
            }
        };
    }

    public static <A,B> Group<T2<A,B>> group(Group<A> groupA,Group<B> groupB) {
        return new Group<T2<A,B>>(){

            private T2<A,B> id = Tuple.of(groupA.identity(),groupB.identity());

            @Override
            public F1<T2<A, B>, T2<A, B>> inverse() {
                return inverseFn(groupA, groupB);
            }

            @Override
            public T2<A, B> identity() {
                return id;
            }

            @Override
            public F2<T2<A, B>, T2<A, B>, T2<A, B>> dot() {
                return dotFn(groupA, groupB);
            }
        };
    }
}
