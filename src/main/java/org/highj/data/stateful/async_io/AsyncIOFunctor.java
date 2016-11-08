package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.functor.Functor;

import java.io.IOException;
import java.util.function.Function;

public interface AsyncIOFunctor extends Functor<AsyncIO.µ> {
    @Override
    default <A, B> __<AsyncIO.µ, B> map(Function<A, B> f, __<AsyncIO.µ, A> ma) {
        return (AsyncIO<B>)(F1<Either<IOException,B>,SafeIO<T0>> handler) ->
            SafeIO.narrow(SafeIO.functor.map(
                (Maybe<Either<IOException,A>> x) -> x.map(
                    (Either<IOException,A> x2) -> x2.rightMap(f)
                ),
                AsyncIO.narrow(ma).toIO(
                    (Either<IOException,A> x) -> handler.apply(x.rightMap(f))
                )
            ));
    }
}
