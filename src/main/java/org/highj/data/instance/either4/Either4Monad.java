package org.highj.data.instance.either4;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Either3;
import org.highj.data.Either4;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

import static org.highj.Hkt.asEither4;

public interface Either4Monad<S,T,U> extends Either4Applicative<S,T,U>,
        MonadRec<__<__<__<Either4.µ,S>,T>,U>> {

    @Override
    default <A, B> Either4<S, T, U, B> bind(__<__<__<__<Either4.µ, S>, T>, U>, A> nestedA,
                                            Function<A, __<__<__<__<Either4.µ, S>, T>, U>, B>> fn) {
        return asEither4(nestedA).either(
                Either4::E1, Either4::E2, Either4::E3, a -> asEither4(fn.apply(a)));
    }

    @Override
    default <A, B> Either4<S, T, U, B> tailRec(Function<A, __<__<__<__<Either4.µ, S>, T>,U>,
            Either<A, B>>> function, A startValue) {
        Either4<S, T, U, Either<A, B>> step = Either4.E4(Either.Left(startValue));
        while(step.isE4() && step.getE4().isLeft()) {
            step = asEither4(function.apply(step.getE4().getLeft()));
        }
        return step.map4(Either::getRight);
    }
}
