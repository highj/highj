package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.Applicative;

public interface AsyncIOApplicative<E> extends AsyncIOApply<E>, Applicative<__<AsyncIO.µ,E>> {
    @Override
    default <A> __<__<AsyncIO.µ, E>, A> pure(A a) {
        return (AsyncIO<E,A>)(F1<Either<E,A>,SafeIO<T0>> handler) ->
            SafeIO.applicative.pure(Maybe.Just(Either.<E,A>Right(a)));
    }
}
