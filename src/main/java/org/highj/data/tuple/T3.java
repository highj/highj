package org.highj.data.tuple;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.HList;
import org.highj.data.HList.HCons;
import org.highj.data.HList.HNil;
import org.highj.data.tuple.t3.*;
import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A tuple of arity 3, a.k.a. "triple".
 */
public abstract class T3<A, B, C> implements __3<T3.µ, A, B, C> {
    public static class µ {

    }

    public abstract A _1();

    public abstract B _2();

    public abstract C _3();

    public static <A, B, C> T3<A, B, C> of(A a, B b, C c) {
        assert a != null && b != null && c != null;
        return new T3<A, B, C>() {

            @Override
            public A _1() {
                return a;
            }

            @Override
            public B _2() {
                return b;
            }

            @Override
            public C _3() {
                return c;
            }
        };
    }

    public static <A, B, C> T3<A, B, C> ofLazy(Supplier<A> thunkA, Supplier<B> thunkB, Supplier<C> thunkC) {
        return new T3<A, B, C>() {

            @Override
            public A _1() {
                return thunkA.get();
            }

            @Override
            public B _2() {
                return thunkB.get();
            }

            @Override
            public C _3() {
                return thunkC.get();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C> T3<A, B, C> narrow(__<__<__<µ, A>, B>, C> value) {
        return (T3) value;
    }

    public <AA> T3<AA, B, C> map_1(Function<? super A, ? extends AA> fn) {
        return of(fn.apply(_1()), _2(), _3());
    }

    public <BB> T3<A, BB, C> map_2(Function<? super B, ? extends BB> fn) {
        return of(_1(), fn.apply(_2()), _3());
    }

    public <CC> T3<A, B, CC> map_3(Function<? super C, ? extends CC> fn) {
        return of(_1(), _2(), fn.apply(_3()));
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

    public static <A, AA, AAA, B, BB, BBB, C, CC, CCC> T3<C, CC, CCC> merge(T3<A, AA, AAA> a, T3<B, BB, BBB> b,
                                                                            Function<A, Function<B, C>> fn1, Function<AA, Function<BB, CC>> fn2, Function<AAA, Function<BBB, CCC>> fn3) {
        return new T3<C, CC, CCC>() {
            @Override
            public C _1() {
                return fn1.apply(a._1()).apply(b._1());
            }

            @Override
            public CC _2() {
                return fn2.apply(a._2()).apply(b._2());
            }

            @Override
            public CCC _3() {
                return fn3.apply(a._3()).apply(b._3());
            }
        };
    }

    public static <A, B, C> Eq<T3<A, B, C>> eq(Eq<? super A> eqA, Eq<? super B> eqB, Eq<? super C> eqC) {
        return (one, two) -> eqA.eq(one._1(), two._1()) && eqB.eq(one._2(), two._2()) && eqC.eq(one._3(), two._3());
    }

    public static <A, B, C> Ord<T3<A, B, C>> ord(Ord<? super A> ordA, Ord<? super B> ordB, Ord<? super C> ordC) {
        return (one, two) -> ordA.cmp(one._1(), two._1())
                .andThen(ordB.cmp(one._2(), two._2()))
                .andThen(ordC.cmp(one._3(), two._3()));
    }


    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", _1(), _2(), _3());
    }

    public static <S, T> T3Functor<S, T> functor() {
        return new T3Functor<S, T>() {
        };
    }

    public static <S, T> T3Bind<S, T> bind(Semigroup<S> semigroupS, Semigroup<T> semigroupT) {
        return new T3Bind<S, T>() {
            @Override
            public Semigroup<S> getS() {
                return semigroupS;
            }

            @Override
            public Semigroup<T> getT() {
                return semigroupT;
            }
        };
    }

    public static <S, T> T3Monad<S, T> monad(Monoid<S> monoidS, Monoid<T> monoidT) {
        return new T3Monad<S, T>() {
            @Override
            public Monoid<S> getS() {
                return monoidS;
            }

            @Override
            public Monoid<T> getT() {
                return monoidT;
            }
        };
    }

    public static <S, T> T3Comonad<S, T> comonad() {
        return new T3Comonad<S, T>() {
        };
    }

    public static <A, B, C> Semigroup<T3<A, B,C>> semigroup(BinaryOperator<A> semigroupA,
                                                            BinaryOperator<B> semigroupB,
                                                            BinaryOperator<C> semigroupC) {
        return (x,y) -> T3.of(semigroupA.apply(x._1(), y._1()),
                semigroupB.apply(x._2(), y._2()),
                semigroupC.apply(x._3(), y._3()));
    }

    public static <A, B, C> Monoid<T3<A, B,C>> monoid(Monoid<A> monoidA, Monoid<B> monoidB, Monoid<C> monoidC) {
        return Monoid.create(T3.of(monoidA.identity(), monoidB.identity(), monoidC.identity()),
                (x, y) -> T3.of(monoidA.apply(x._1(), y._1()), monoidB.apply(x._2(), y._2()), monoidC.apply(x._3(), y._3())));
    }

    public static <A, B, C> Group<T3<A, B, C>> group(Group<A> groupA, Group<B> groupB, Group<C> groupC) {
        return Group.create(T3.of(groupA.identity(), groupB.identity(), groupC.identity()),
                (x, y) -> T3.of(groupA.apply(x._1(), y._1()), groupB.apply(x._2(), y._2()), groupC.apply(x._3(), y._3())),
                z -> T3.of(groupA.inverse(z._1()), groupB.inverse(z._2()), groupC.inverse(z._3())));
    }

    public static <S> T3Bifunctor<S> bifunctor() {
        return new T3Bifunctor<S>() {
        };
    }

    public static <S> T3Biapply<S> biapply(Semigroup<S> semigroupS) {
        return () -> semigroupS;
    }

    public static <S> T3Biapplicative<S> biapplicative(Monoid<S> monoidS) {
        return () -> monoidS;
    }

    public HCons<A, HCons<B, HCons<C, HNil>>> toHlist() {
        return HList.cons(_1(), HList.cons(_2(), HList.cons(_3(), HList.nil)));
    }


}