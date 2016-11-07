package org.highj.data.stateful;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.stateful.async_io.*;
import org.highj.data.tuple.T0;
import org.highj.function.F1;

public interface AsyncIO<E,A> extends __2<AsyncIO.µ,E,A> {
    enum µ {}

    static <E,A> AsyncIO<E,A> narrow(__<__<µ,E>,A> a) {
        return (AsyncIO<E,A>)a;
    }

    SafeIO<Maybe<Either<E,A>>> toIO(F1<Either<E,A>,SafeIO<T0>> handler);

    default SafeIO<T0> run(F1<Either<E,A>,SafeIO<T0>> handler) {
        return SafeIO.narrow(SafeIO.bind.bind(
            toIO(handler),
            (Maybe<Either<E,A>> syncOp) ->
                syncOp.map(handler).getOrElse(SafeIO.applicative.pure(T0.of()))
        ));
    }

    static <E> AsyncIOFunctor<E> functor() {
        return new AsyncIOFunctor<E>() {};
    }

    static <E> AsyncIOApply<E> apply() {
        return new AsyncIOApply<E>() {};
    }

    static <E> AsyncIOApplicative<E> applicative() {
        return new AsyncIOApplicative<E>() {};
    }

    static <E> AsyncIOBind<E> bind() {
        return new AsyncIOBind<E>() {};
    }

    static <E> AsyncIOMonad<E> monad() {
        return new AsyncIOMonad<E>() {};
    }

    static <E> AsyncIOMonadRec<E> monadRec() {
        return new AsyncIOMonadRec<E>() {};
    }
}
