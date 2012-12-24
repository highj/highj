package org.highj.typeclass.arrow;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.function.F1;
import org.highj.typeclass.monad.Applicative;

public interface Arrow<mu> extends Category<mu> {

    public <A, B> __<mu, A, B> arr(F1<A, B> fn);

    public <A, B, C> __<mu, T2<A, C>, T2<B, C>> first(__<mu, A, B> arrow);

    public default <A, B, C> __<mu, T2<C, A>, T2<C, B>> second(__<mu, A, B> arrow) {
        __<mu, T2<C, A>, T2<A, C>> swapForth = arr(Tuple.<C, A>swap());
        __<mu, T2<A, C>, T2<B, C>> arrowFirst = first(arrow);
        __<mu, T2<B, C>, T2<C, B>> swapBack = arr(Tuple.<B, C>swap());
        return then(swapForth, arrowFirst, swapBack);
    }

    public default <A, B, AA, BB> __<mu, T2<A, AA>, T2<B, BB>> split(__<mu, A, B> arr1, __<mu, AA, BB> arr2) {
        __<mu, T2<A, AA>, T2<B, AA>> one = first(arr1);
        __<mu, T2<B, AA>, T2<B, BB>> two = second(arr2);
        return then(one, two);
    }

    public default <A, B, C> __<mu, A, T2<B, C>> fanout(__<mu, A, B> arr1, __<mu, A, C> arr2) {
        __<mu, A, T2<A, A>> duplicated = arr(new F1<A, T2<A, A>>() {
            @Override
            public T2<A, A> $(A a) {
                return Tuple.of(a, a);
            }
        });
        __<mu, T2<A, A>, T2<B, C>> splitted = split(arr1, arr2);
        return then(duplicated, splitted);
    }

    public default <A> __<mu, A, A> returnA() {
        return arr(F1.<A>id());
    }

    public default <A, B, C> F1<__<mu, B, C>, __<mu, A, C>> precomposition(final F1<A, B> fn) {
        //(^>>) :: Arrow a => (b -> c) -> a c d -> a b d
        //f ^>> a = arr f >>> a
        return new F1<__<mu, B, C>, __<mu, A, C>>() {
            private final __<mu, A, B> arrow = arr(fn);

            @Override
            public __<mu, A, C> $(__<mu, B, C> bc) {
                return then(arrow, bc);
            }
        };
    }

    public default <A, B, C> F1<__<mu, A, B>, __<mu, A, C>> postcomposition(final F1<B, C> fn) {
        //(^<<) :: Arrow a => (c -> d) -> a b c -> a b d
        //f ^<< a = arr f <<< a
        return new F1<__<mu, A, B>, __<mu, A, C>>() {
            private final __<mu, B, C> arrow = arr(fn);

            @Override
            public __<mu, A, C> $(__<mu, A, B> ab) {
                return then(ab, arrow);
            }
        };
    }

    public default <X> Applicative<__.µ<mu, X>> getApplicative() {
        return new Applicative<__.µ<mu, X>>() {
            @Override
            public <A, B> _<__.µ<mu, X>, B> ap(_<__.µ<mu, X>, F1<A, B>> fn, _<__.µ<mu, X>, A> nestedA) {
                return then(fanout(__.uncurry2(fn), __.uncurry2(nestedA)), arr(
                        new F1<T2<F1<A, B>, A>, B>() {
                            @Override
                            public B $(T2<F1<A, B>, A> pair) {
                                return pair._1().$(pair._2());
                            }
                        }));
            }

            @Override
            public <A> _<__.µ<mu, X>, A> pure(A a) {
                return arr(F1.<X, A>constant(a));
            }

            @Override
            public <A, B> _<__.µ<mu, X>, B> map(F1<A, B> fn, _<__.µ<mu, X>, A> nestedA) {
                return then(__.uncurry2(nestedA), arr(fn));
            }
        };
    }}