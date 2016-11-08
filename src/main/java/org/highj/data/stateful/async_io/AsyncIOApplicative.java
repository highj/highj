package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.Applicative;

import java.io.IOException;

public interface AsyncIOApplicative extends AsyncIOApply, Applicative<AsyncIO.µ> {
    @Override
    default <A> __<AsyncIO.µ, A> pure(A a) {
        return (AsyncIO<A>)(F1<Either<IOException,A>,SafeIO<T0>> handler) ->
            SafeIO.applicative.pure(Maybe.Just(Either.<IOException,A>Right(a)));
    }
}
