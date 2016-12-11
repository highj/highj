package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.MonadRec;

import java.io.IOException;
import java.util.function.Function;

import static org.highj.Hkt.asAsyncIO;
import static org.highj.Hkt.asSafeIO;

public interface AsyncIOMonadRec extends AsyncIOMonad, MonadRec<AsyncIO.µ> {
    @Override
    default <A, B> __<AsyncIO.µ, B> tailRec(Function<A, __<AsyncIO.µ, Either<A, B>>> f, A a0) {
        return (AsyncIO<B>)(F1<Either<IOException,B>,SafeIO<T0>> handler) ->
            SafeIO.monadRec.tailRec(
                (A a) ->
                    asSafeIO(
                        SafeIO.functor.map(
                            (Maybe<Either<IOException,Either<A,B>>> x) ->
                                x.map(
                                    (Either<IOException,Either<A,B>> x2) ->
                                        x2.either(
                                            (IOException e) -> Either.<A,Maybe<Either<IOException,B>>>Right(Maybe.Just(Either.<IOException,B>Left(e))),
                                            (Either<A,B> x3) ->
                                                x3.either(
                                                    Either::<A, Maybe<Either<IOException, B>>>Left,
                                                    (B b) -> Either.<A,Maybe<Either<IOException,B>>>Right(Maybe.Just(Either.<IOException,B>Right(b)))
                                                )
                                        )
                                ).getOrElse(Either.<A,Maybe<Either<IOException,B>>>Right(Maybe.<Either<IOException,B>>Nothing())),
                            asAsyncIO(f.apply(a)).toIO(
                                (Either<IOException,Either<A,B>> x) ->
                                    x.either(
                                        (IOException e) -> handler.apply(Either.<IOException,B>Left(e)),
                                        (Either<A,B> x2) ->
                                            x2.either(
                                                (A a2) -> asAsyncIO(tailRec(f, a2)).run(handler),
                                                (B b) -> handler.apply(Either.<IOException,B>Right(b))
                                            )
                                    )
                            )
                        )
                    ),
                a0
            );
    }
}
