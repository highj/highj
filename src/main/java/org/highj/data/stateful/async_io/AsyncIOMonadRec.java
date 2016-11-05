package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

public interface AsyncIOMonadRec<E> extends AsyncIOMonad<E>, MonadRec<__<AsyncIO.µ,E>> {
    @Override
    default <A, B> __<__<AsyncIO.µ, E>, B> tailRec(Function<A, __<__<AsyncIO.µ, E>, Either<A, B>>> f, A a0) {
        return (AsyncIO<E,B>)(F1<Either<E,B>,SafeIO<T0>> handler) ->
            SafeIO.monadRec.tailRec(
                (A a) ->
                    SafeIO.narrow(
                        SafeIO.functor.map(
                            (Maybe<Either<E,Either<A,B>>> x) ->
                                x.map(
                                    (Either<E,Either<A,B>> x2) ->
                                        x2.either(
                                            (E e) -> Either.<A,Maybe<Either<E,B>>>Right(Maybe.Just(Either.<E,B>Left(e))),
                                            (Either<A,B> x3) ->
                                                x3.either(
                                                    Either::<A, Maybe<Either<E, B>>>Left,
                                                    (B b) -> Either.<A,Maybe<Either<E,B>>>Right(Maybe.Just(Either.<E,B>Right(b)))
                                                )
                                        )
                                ).getOrElse(Either.Right(Maybe.<Either<E,B>>Nothing())),
                            AsyncIO.narrow(f.apply(a)).toIO(
                                (Either<E,Either<A,B>> x) ->
                                    x.either(
                                        (E e) -> handler.apply(Either.<E,B>Left(e)),
                                        (Either<A,B> x2) ->
                                            x2.either(
                                                (A a2) -> AsyncIO.narrow(tailRec(f, a2)).run(handler),
                                                (B b) -> handler.apply(Either.<E,B>Right(b))
                                            )
                                    )
                            )
                        )
                    ),
                a0
            );
    }
}
