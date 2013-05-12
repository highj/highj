package org.highj.typeclass2.arrow;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.data.functions.Functions;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.HigherKinded.uncurry2;

public interface Arrow<µ> extends Category<µ> {

    public <A, B> __<µ, A, B> arr(Function<A, B> fn);

    public <A, B, C> __<µ, T2<A, C>, T2<B, C>> first(__<µ, A, B> arrow);

    public default <A, B, C> __<µ, T2<C, A>, T2<C, B>> second(__<µ, A, B> arrow) {
        __<µ, T2<C, A>, T2<A, C>> swapForth = arr(T2<C, A>::swap);
        __<µ, T2<A, C>, T2<B, C>> arrowFirst = first(arrow);
        __<µ, T2<B, C>, T2<C, B>> swapBack = arr(T2<B, C>::swap);
        return then(swapForth, arrowFirst, swapBack);
    }

    public default <A, B, AA, BB> __<µ, T2<A, AA>, T2<B, BB>> split(__<µ, A, B> arr1, __<µ, AA, BB> arr2) {
        __<µ, T2<A, AA>, T2<B, AA>> one = first(arr1);
        __<µ, T2<B, AA>, T2<B, BB>> two = second(arr2);
        return then(one, two);
    }

    public default <A, B, C> __<µ, A, T2<B, C>> fanout(__<µ, A, B> arr1, __<µ, A, C> arr2) {
        __<µ, A, T2<A, A>> duplicated = arr((A a) -> Tuple.<A, A>of(a, a));
        __<µ, T2<A, A>, T2<B, C>> splitted = split(arr1, arr2);
        return then(duplicated, splitted);
    }

    public default <A> __<µ, A, A> returnA() {
        return arr(Functions.<A>id());
    }

    //(^>>) :: Arrow a => (b -> c) -> a c d -> a b d
    public default <A, B, C> Function<__<µ, B, C>, __<µ, A, C>> precomposition(Function<A, B> fn) {
        //f ^>> a = arr f >>> a
        return bc -> then(arr(fn), bc);
    }

    //(^<<) :: Arrow a => (c -> d) -> a b c -> a b d
    public default <A, B, C> Function<__<µ, A, B>, __<µ, A, C>> postcomposition(final Function<B, C> fn) {
        //f ^<< a = arr f <<< a
        return ab -> then(ab, arr(fn));
    }

    public default <X> Applicative<__.µ<µ, X>> getApplicative() {
        return new Applicative<__.µ<µ, X>>() {
            @Override
            public <A, B> _<__.µ<µ, X>, B> ap(_<__.µ<µ, X>, Function<A, B>> fn, _<__.µ<µ, X>, A> nestedA) {
                return then(fanout(uncurry2(fn), uncurry2(nestedA)), arr(
                        (T2<Function<A, B>, A> pair) -> pair._1().apply(pair._2())));
            }

            @Override
            public <A> _<__.µ<µ, X>, A> pure(A a) {
                return arr(Functions.<X, A>constant(a));
            }

            @Override
            public <A, B> _<__.µ<µ, X>, B> map(Function<A, B> fn, _<__.µ<µ, X>, A> nestedA) {
                return then(uncurry2(nestedA), arr(fn));
            }
        };
    }
}