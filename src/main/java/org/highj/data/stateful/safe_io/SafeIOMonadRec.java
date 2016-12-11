package org.highj.data.stateful.safe_io;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.stateful.SafeIO;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

import static org.highj.Hkt.asSafeIO;

public interface SafeIOMonadRec extends SafeIOMonad, MonadRec<SafeIO.µ> {

    @Override
    default <A, B> SafeIO<B> tailRec(Function<A, __<SafeIO.µ, Either<A, B>>> f, A startA) {
        return () -> {
            A a = startA;
            while (true) {
                Either<A,B> x = asSafeIO(f.apply(a)).run();
                for (A x2 : x.maybeLeft()) {
                    a = x2;
                }
                for (B x2 : x.maybeRight()) {
                    return x2;
                }
            }
        };
    }
}
