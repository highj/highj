package org.highj.data.tuple;

import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.t4.*;
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

public abstract class T4<A, B, C, D> implements ____<T4.µ, A, B, C, D> {
    public static class µ {
    }

    public abstract A _1();

    public abstract B _2();

    public abstract C _3();

    public abstract D _4();

    public <AA> T4<AA, B, C, D> map_1(Function<? super A, ? extends AA> fn) {
        return Tuple.of(fn.apply(_1()), _2(), _3(), _4());
    }

    public <BB> T4<A, BB, C, D> map_2(Function<? super B, ? extends BB> fn) {
        return Tuple.of(_1(), fn.apply(_2()), _3(), _4());
    }

    public <CC> T4<A, B, CC, D> map_3(Function<? super C, ? extends CC> fn) {
        return Tuple.of(_1(), _2(), fn.apply(_3()), _4());
    }

    public <DD> T4<A, B, C, DD> map_4(Function<? super D, ? extends DD> fn) {
        return Tuple.of(_1(), _2(), _3(), fn.apply(_4()));
    }

    @Override
    public int hashCode() {
        return 31 * _1().hashCode() + 37 * _2().hashCode() + 41 * _3().hashCode() + 43 * _4().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T4) {
            T4<?, ?, ?, ?> that = (T4) o;
            return this._1().equals(that._1())
                    && this._2().equals(that._2())
                    && this._3().equals(that._3())
                    && this._4().equals(that._4());
        }
        return false;
    }

    public static <A, B, C, D> Eq<T4<A, B, C, D>> eq(Eq<? super A> eqA, Eq<? super B> eqB,
                                                     Eq<? super C> eqC, Eq<? super D> eqD) {
        return (one, two) -> eqA.eq(one._1(), two._1())
                && eqB.eq(one._2(), two._2())
                && eqC.eq(one._3(), two._3())
                && eqD.eq(one._4(), two._4());
    }

    public static <A, B, C, D> Ord<T4<A, B, C, D>> ord(Ord<? super A> ordA, Ord<? super B> ordB,
                                                       Ord<? super C> ordC, Ord<? super D> ordD) {
        return (one, two) -> ordA.cmp(one._1(), two._1())
                .andThen(ordB.cmp(one._2(), two._2()))
                .andThen(ordC.cmp(one._3(), two._3()))
                .andThen(ordD.cmp(one._4(), two._4()));
    }


    @Override
    public String toString() {
        return String.format("(%s,%s,%s,%s)", _1(), _2(), _3(), _4());
    }

    public static <S, T, U> T4Functor<S, T, U> functor() {
        return new T4Functor<S, T, U>() {
        };
    }

    public static <S, T, U> T4Bind<S, T, U> bind(Semigroup<S> semigroupS, Semigroup<T> semigroupT, Semigroup<U> semigroupU) {
        return new T4Bind<S, T, U>() {
            @Override
            public Semigroup<S> getS() {
                return semigroupS;
            }

            @Override
            public Semigroup<T> getT() {
                return semigroupT;
            }

            @Override
            public Semigroup<U> getU() {
                return semigroupU;
            }
        };
    }

    public static <S, T, U> T4Monad<S, T, U> monad(Monoid<S> monoidS, Monoid<T> monoidT, Monoid<U> monoidU) {
        return new T4Monad<S, T, U>() {
            @Override
            public Monoid<S> getS() {
                return monoidS;
            }

            @Override
            public Monoid<T> getT() {
                return monoidT;
            }

            @Override
            public Monoid<U> getU() {
                return monoidU;
            }
        };
    }


    public static <S, T, U> T4Comonad<S,T,U> comonad() {
        return new T4Comonad<S,T,U>(){};
    }

    public static <A, B, C, D> Semigroup<T4<A, B, C, D>> semigroup(Semigroup<A> semigroupA, Semigroup<B> semigroupB,
                                                                   Semigroup<C> semigroupC, Semigroup<D> semigroupD) {
        return T4Semigroup.from(semigroupA, semigroupB, semigroupC, semigroupD);
    }

    public static <A, B, C, D> Monoid<T4<A, B, C, D>> monoid(Monoid<A> monoidA, Monoid<B> monoidB, Monoid<C> monoidC,
                                                             Monoid<D> monoidD) {
        return T4Monoid.from(monoidA, monoidB, monoidC, monoidD);
    }

    public static <A, B, C, D> Group<T4<A, B, C, D>> group(Group<A> groupA, Group<B> groupB, Group<C> groupC,
                                                           Group<D> groupD) {
        return T4Group.from(groupA, groupB, groupC, groupD);
    }
}