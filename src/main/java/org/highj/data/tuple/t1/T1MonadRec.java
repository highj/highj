package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

import static org.highj.Hkt.asT1;

public interface T1MonadRec extends T1Monad, MonadRec<T1.µ> {

    @Override
    default <A, B> T1<B> tailRec(Function<A, __<T1.µ, Either<A, B>>> function, A startValue) {
        T1<Either<A, B>> step = T1.of(Either.Left(startValue));
        while(step.get().isLeft()) {
            step = asT1(function.apply(step.get().getLeft()));
        }
        return step.map(Either::getRight);
    }
}
