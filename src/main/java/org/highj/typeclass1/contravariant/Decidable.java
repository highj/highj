package org.highj.typeclass1.contravariant;

import org.derive4j.hkt.__;
import org.highj.data.Either;

import java.util.function.Function;

/**
 * The contravariant version of Alternative.
 */
public interface Decidable<F> extends Divisible<F> {

    <A> __<F,A> lose(Function<A, Void> fn);

    <A,B,C> __<F,A> choose(Function<A,Either<B,C>> fn, __<F,B> fb, __<F,C> fc);

    default __<F, Void> lost() {
        return lose(Function.identity());
    }

    default <B,C> __<F, Either<B,C>> chosen(__<F,B> fb, __<F,C> fc) {
        return choose(Function.identity(), fb, fc);
    }
}
