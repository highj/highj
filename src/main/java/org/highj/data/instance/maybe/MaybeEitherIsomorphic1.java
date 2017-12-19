package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.tuple.T0;
import org.highj.typeclass2.injective.Isomorphic1;

public interface MaybeEitherIsomorphic1 extends Isomorphic1<Maybe.µ, __<Either.µ, T0>> {
    @Override
    default <A> Maybe<A> from(__<__<Either.µ, T0>, A> input) {
        return Hkt.asEither(input).either(u -> Maybe.Nothing(), Maybe::Just);
    }

    @Override
    default <A> Either<T0, A> to(__<Maybe.µ, A> input) {
        return Hkt.asMaybe(input).cata(Either.Left(T0.unit), Either::<T0, A>Right);
    }
}
