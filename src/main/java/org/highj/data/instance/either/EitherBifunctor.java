package org.highj.data.instance.either;

import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

import static org.highj.Hkt.asEither;

public interface EitherBifunctor extends Bifunctor<Either.µ> {
    @Override
    default <A, B, C, D> Either<B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __2<Either.µ, A, C> nestedAC) {
        Either<A,C> either = asEither(nestedAC);
        return either.bimap(fn1, fn2);
    }
}
