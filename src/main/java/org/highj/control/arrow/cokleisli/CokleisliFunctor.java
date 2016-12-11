package org.highj.control.arrow.cokleisli;

import org.derive4j.hkt.__;
import org.highj.control.arrow.Cokleisli;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asCokleisli;

public interface CokleisliFunctor<W, S> extends Functor<__<__<Cokleisli.µ, W>, S>> {

    @Override
    default <A, B> Cokleisli<W, S, B> map(Function<A, B> fn, __<__<__<Cokleisli.µ, W>, S>, A> nestedA) {
        return new Cokleisli<>(asCokleisli(nestedA).andThen(fn));
    }
}
