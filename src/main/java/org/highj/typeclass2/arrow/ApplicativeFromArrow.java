package org.highj.typeclass2.arrow;

import org.highj._;
import org.highj.__;
import org.highj.data.functions.Functions;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.HigherKinded.uncurry2;

@FunctionalInterface
public interface ApplicativeFromArrow<Arr,X> extends Applicative<__.µ<Arr, X>> {

    public Arrow<Arr> getArrow();

    @Override
    public default <A, B> _<__.µ<Arr, X>, B> ap(_<__.µ<Arr, X>, Function<A, B>> fn, _<__.µ<Arr, X>, A> nestedA) {
        return getArrow().then(getArrow().fanout(uncurry2(fn), uncurry2(nestedA)), getArrow().arr(
                (T2<Function<A, B>, A> pair) -> pair._1().apply(pair._2())));
    }

    @Override
    public default <A> _<__.µ<Arr, X>, A> pure(A a) {
        return getArrow().arr(Functions.<X, A>constant(a));
    }

    @Override
    public default <A, B> _<__.µ<Arr, X>, B> map(Function<A, B> fn, _<__.µ<Arr, X>, A> nestedA) {
        return getArrow().then(uncurry2(nestedA), getArrow().arr(fn));
    }
}
