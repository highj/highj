package org.highj.data.instance.either4;

import org.derive4j.hkt.__;
import org.highj.data.Either4;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asEither4;

public interface Either4Functor<S, T, U> extends Functor<__<__<__<Either4.µ, S>, T>, U>> {
    @Override
    default <A, B> Either4<S, T, U, B> map(Function<A, B> fn,
                                           __<__<__<__<Either4.µ, S>, T>, U>, A> nestedA) {
        return asEither4(nestedA).map4(fn);
    }
}
