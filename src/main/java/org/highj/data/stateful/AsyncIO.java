package org.highj.data.stateful;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.async_io.*;
import org.highj.data.tuple.T0;
import org.highj.function.F1;

import java.io.IOException;

import static org.highj.Hkt.asSafeIO;

/**
 * Mixed Asynchronous/Synchronous IO monad
 * @param <A> The result type of the computation.
 */
public interface AsyncIO<A> extends __<AsyncIO.µ,A> {
    interface µ {}

    /**
     * The IO equivalent of this AsyncIO monad.
     * If this computation is a synchronous computation then the result is returned straight away. ("Just")
     * If this computation is a asynchronous computation with an immediate result, then the result is also returned
     * straight away. ("Just")
     * If this computation is a asynchronous computation with no result yet, then the result is Nothing and the
     * given handler will be called at a later time with the result.
     * If the result is a Just, then the handler is never called.
     *
     * AsyncIO is implemented in this way to allow for an MonadRec instance that will not cause a stack overflow.
     *
     * @param handler a handler for the result of a asynchronous computation with no result yet.
     * @return Just when there is an immediate result, or Nothing when there is no result yet.
     */
    SafeIO<Maybe<Either<IOException,A>>> toIO(F1<Either<IOException,A>,SafeIO<T0>> handler);

    /**
     * A helper method that wall use the handler in both cases of an immediate result and no result yet.
     * @param handler the handler to use the result of the computation.
     * @return a IO value representing the effect of this method.
     */
    default SafeIO<T0> run(F1<Either<IOException,A>,SafeIO<T0>> handler) {
        return asSafeIO(SafeIO.bind.bind(
            toIO(handler),
            (Maybe<Either<IOException,A>> syncOp) ->
                syncOp.map(handler).getOrElse(SafeIO.applicative.pure(T0.of()))
        ));
    }

    AsyncIOFunctor functor = new AsyncIOFunctor() {};

    AsyncIOApply apply = new AsyncIOApply() {};

    AsyncIOApplicative applicative = new AsyncIOApplicative() {};

    AsyncIOBind bind = new AsyncIOBind() {};

    AsyncIOMonad monad = new AsyncIOMonad() {};

    AsyncIOMonadRec monadRec = new AsyncIOMonadRec() {};

}
