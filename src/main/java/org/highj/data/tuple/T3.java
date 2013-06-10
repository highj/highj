package org.highj.data.tuple;

import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.t3.*;
import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

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

    public static <A, B, C> Eq<T3<A, B, C>> eq(Eq<? super A> eqA, Eq<? super B> eqB, Eq<? super C> eqC) {
        return (one, two) -> eqA.eq(one._1(), two._1()) && eqB.eq(one._2(), two._2()) && eqC.eq(one._3(), two._3());
    }

    public static <A, B, C> Ord<T3<A,B,C>> ord(Ord<? super A> ordA, Ord<? super B> ordB, Ord<? super C> ordC) {
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
        return new T3Comonad<S,T>(){};
    }

    public static <A, B, C> Semigroup<T3<A, B, C>> semigroup(Semigroup<A> semigroupA, Semigroup<B> semigroupB, Semigroup<C> semigroupC) {
        return T3Semigroup.from(semigroupA, semigroupB, semigroupC);
    }

    public static <A, B, C> Monoid<T3<A, B, C>> monoid(Monoid<A> monoidA, Monoid<B> monoidB, Monoid<C> monoidC) {
        return T3Monoid.from(monoidA, monoidB, monoidC);
    }

    public static <A, B, C> Group<T3<A, B, C>> group(Group<A> groupA, Group<B> groupB, Group<C> groupC) {
        return T3Group.from(groupA, groupB, groupC);
    }
}