package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.functions.Functions;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.derive4j.hkt.__2.coerce;

@FunctionalInterface
public interface ApplicativeFromArrow<Arr,X> extends Applicative<__<Arr, X>> {

    public Arrow<Arr> getArrow();

    @Override
    public default <A, B> __2<Arr, X, B> ap(__<__<Arr, X>, Function<A, B>> fn, __<__<Arr, X>, A> nestedA) {
        return getArrow().then(getArrow().fanout(__2.coerce(fn), __2.coerce(nestedA)), getArrow().arr(
                (T2<Function<A, B>, A> pair) -> pair._1().apply(pair._2())));
    }

    @Override
    public default <A> __2<Arr, X, A> pure(A a) {
        return getArrow().arr(Functions.<X, A>constant(a));
    }

    @Override
    public default <A, B> __2<Arr, X, B> map(Function<A, B> fn, __<__<Arr, X>, A> nestedA) {
        return getArrow().then(__2.coerce(nestedA), getArrow().arr(fn));
    }
}
