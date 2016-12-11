package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.Bind;

import java.io.IOException;
import java.util.function.Function;

import static org.highj.Hkt.asAsyncIO;
import static org.highj.Hkt.asSafeIO;

public interface AsyncIOBind extends AsyncIOApply, Bind<AsyncIO.µ> {
    @Override
    default <A, B> __<AsyncIO.µ, B> bind(__<AsyncIO.µ, A> ma, Function<A, __<AsyncIO.µ, B>> f) {
        return (AsyncIO<B>)(F1<Either<IOException,B>,SafeIO<T0>> handler) ->
            asSafeIO(
                SafeIO.bind.bind(
                    asAsyncIO(ma).toIO(
                        (Either<IOException,A> x) ->
                            x.either(
                                (IOException e) -> handler.apply(Either.<IOException,B>Left(e)),
                                (A a) ->
                                    asAsyncIO(f.apply(a)).run(handler)
                            )
                    ),
                    (Maybe<Either<IOException,A>> x) ->
                        x.map(
                            (Either<IOException,A> x2) ->
                                x2.either(
                                    (IOException e) -> SafeIO.applicative.pure(Maybe.Just(Either.<IOException,B>Left(e))),
                                    (A a) ->
                                        asAsyncIO(f.apply(a)).toIO(handler)
                                )
                        ).getOrElse(
                            SafeIO.applicative.pure(Maybe.<Either<IOException,B>>Nothing())
                        )
                )
            );
    }
}
