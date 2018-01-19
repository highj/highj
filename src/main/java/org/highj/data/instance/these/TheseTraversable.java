package org.highj.data.instance.these;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

public interface TheseTraversable<F> extends TheseFunctor<F>, TheseFoldable<F>, Traversable<__<These.µ, F>> {
    @Override
    default <A, B> These<F, B> map(Function<A, B> fn, __<__<These.µ, F>, A> nestedA) {
        return TheseFunctor.super.map(fn, nestedA);
    }

    @Override
    default <A, B, X> __<X, __<__<These.µ, F>, B>> traverse(Applicative<X> applicative, Function<A, __<X, B>> fn, __<__<These.µ, F>, A> traversable) {
        return Hkt.asThese(traversable).these(
                f -> applicative.pure(These.This(f)),
                a -> applicative.map(These::That, fn.apply(a)),
                (f, a) -> applicative.map(b -> These.Both(f, b), fn.apply(a))
        );
    }

    @Override
    default <A, X> __<X, __<__<These.µ, F>, A>> sequenceA(Applicative<X> applicative, __<__<These.µ, F>, __<X, A>> traversable) {
        return Hkt.asThese(traversable).these(
                f -> applicative.pure(These.This(f)),
                a -> applicative.map(These::That, a),
                (f, a) -> applicative.map(b -> These.Both(f, b), a)
        );
    }
}
