package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

public interface AsyncIOBind<E> extends AsyncIOApply<E>, Bind<__<AsyncIO.µ,E>> {
    @Override
    default <A, B> __<__<AsyncIO.µ, E>, B> bind(__<__<AsyncIO.µ, E>, A> ma, Function<A, __<__<AsyncIO.µ, E>, B>> f) {
        return (AsyncIO<E,B>)(F1<Either<E,B>,SafeIO<T0>> handler) ->
            SafeIO.narrow(
                SafeIO.bind.bind(
                    AsyncIO.narrow(ma).toIO(
                        (Either<E,A> x) ->
                            x.either(
                                (E e) -> handler.apply(Either.<E,B>Left(e)),
                                (A a) ->
                                    AsyncIO.narrow(f.apply(a)).run(handler)
                            )
                    ),
                    (Maybe<Either<E,A>> x) ->
                        x.map(
                            (Either<E,A> x2) ->
                                x2.either(
                                    (E e) -> SafeIO.applicative.pure(Maybe.Just(Either.<E,B>Left(e))),
                                    (A a) ->
                                        AsyncIO.narrow(f.apply(a)).toIO(handler)
                                )
                        ).getOrElse(
                            SafeIO.applicative.pure(Maybe.<Either<E,B>>Nothing())
                        )
                )
            );
    }
}
