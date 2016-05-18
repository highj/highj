package org.highj.data.collection.identity;

import org.derive4j.hkt.__;
import org.highj.data.collection.Either;
import org.highj.data.collection.Identity;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

public interface IdentityMonadRec extends IdentityMonad, MonadRec<Identity.µ> {
    @Override
    default <A, B> __<Identity.µ, B> tailRec(Function<A, __<Identity.µ, Either<A, B>>> function, A startA) {
        A a = startA;
        while (true) {
            Either<A,B> x = Identity.narrow(function.apply(a)).run();
            for (A a2 : x.maybeLeft()) {
                a = a2;
            }
            for (B b : x.maybeRight()) {
                return Identity.identity(b);
            }
        }
    }
}
