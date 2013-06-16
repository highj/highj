package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.functions.Functions;
import org.highj.data.tuple.T2;

import java.util.function.Function;

public interface Arrow<A> extends Category<A> {

    public <B, C> __<A, B, C> arr(Function<B, C> fn);

    public <B, C, D> __<A, T2<B, D>, T2<C, D>> first(__<A, B, C> arrow);

    public default <B, C, D> __<A, T2<D, B>, T2<D, C>> second(__<A, B, C> arrow) {
        __<A, T2<D, B>, T2<B, D>> swapForth = arr(T2<D, B>::swap);
        __<A, T2<B, D>, T2<C, D>> arrowFirst = first(arrow);
        __<A, T2<C, D>, T2<D, C>> swapBack = arr(T2<C, D>::swap);
        return then(swapForth, arrowFirst, swapBack);
    }

    //(***)
    public default <B, C, BB, CC> __<A, T2<B, BB>, T2<C, CC>> split(__<A, B, C> arr1, __<A, BB, CC> arr2) {
        __<A, T2<B, BB>, T2<C, BB>> one = first(arr1);
        __<A, T2<C, BB>, T2<C, CC>> two = second(arr2);
        return then(one, two);
    }

    //(&&&)
    public default <B, C, D> __<A, B, T2<C, D>> fanout(__<A, B, C> arr1, __<A, B, D> arr2) {
        __<A, B, T2<B, B>> duplicated = arr((B a) -> T2.<B, B>of(a, a));
        __<A, T2<B, B>, T2<C, D>> splitted = split(arr1, arr2);
        return then(duplicated, splitted);
    }

    public default <B> __<A, B, B> returnA() {
        return arr(Functions.<B>id());
    }

    //(^>>) :: Arrow a => (b -> c) -> a c d -> a b d
    public default <B, C, D> Function<__<A, C, D>, __<A, B, D>> precomposition(Function<B, C> fn) {
        //f ^>> a = arr f >>> a
        return bc -> then(arr(fn), bc);
    }

    //(^<<) :: Arrow a => (c -> d) -> a b c -> a b d
    public default <B, C, D> Function<__<A, B, C>, __<A, B, D>> postcomposition(final Function<C, D> fn) {
        //f ^<< a = arr f <<< a
        return ab -> then(ab, arr(fn));
    }

    public default <X> ApplicativeFromArrow<A,X> getApplicative() {
        return () -> Arrow.this;
   }
}