package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

public interface T1Monad extends MonadRec<T1.µ>, T1Functor {

    @Override
    default <A> T1<A> pure(A a) {
        return T1.of(a);
    }

    @Override
    default <A, B> T1<B> ap(__<T1.µ, Function<A, B>> nestedFn, __<T1.µ, A> nestedA) {
        return T1.narrow(nestedA).ap(T1.narrow(nestedFn));
    }

    @Override
    default <A, B> T1<B> bind(__<T1.µ, A> nestedA, Function<A, __<T1.µ, B>> fn) {
        return T1.narrow(nestedA).bind(a -> T1.narrow(fn.apply(a)));
    }

    @Override
    default <A, B> T1<B> tailRec(Function<A, __<T1.µ, Either<A, B>>> function, A startValue) {
        T1<Either<A, B>> step = T1.of(Either.Left(startValue));
        while(step.get().isLeft()) {
            step = T1.narrow(function.apply(step.get().getLeft()));
        }
        return step.map(Either::getRight);
    }
}
