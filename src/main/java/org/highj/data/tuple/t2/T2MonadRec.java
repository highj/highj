package org.highj.data.tuple.t2;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

public interface T2MonadRec<S> extends T2Monad<S>, MonadRec<__<T2.µ, S>> {

    @Override
    Monoid<S> get();

    @Override
    default <A, B> T2<S, B> tailRec(Function<A, __<__<T2.µ, S>, Either<A, B>>> function, A startValue) {
        T2<S,Either<A, B>> step = T2.of(get().identity(), Either.Left(startValue));
        while(step._2().isLeft()) {
            step = bind(step, s -> function.apply(s.getLeft()));
        }
        return step.map_2(Either::getRight);
    }
}
