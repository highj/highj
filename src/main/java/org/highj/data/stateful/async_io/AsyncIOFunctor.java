package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.AsyncIO;
import org.highj.data.stateful.SafeIO;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface AsyncIOFunctor<E> extends Functor<__<AsyncIO.µ,E>> {
    @Override
    default <A, B> __<__<AsyncIO.µ, E>, B> map(Function<A, B> f, __<__<AsyncIO.µ, E>, A> ma) {
        return (AsyncIO<E,B>)(F1<Either<E,B>,SafeIO<T0>> handler) ->
            SafeIO.narrow(SafeIO.functor.map(
                (Maybe<Either<E,A>> x) -> x.map(
                    (Either<E,A> x2) -> x2.rightMap(f)
                ),
                AsyncIO.narrow(ma).toIO(
                    (Either<E,A> x) -> handler.apply(x.rightMap(f))
                )
            ));
    }
}
