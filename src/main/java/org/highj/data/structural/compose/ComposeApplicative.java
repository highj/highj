package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.data.structural.Compose;
import org.highj.typeclass1.monad.Applicative;

public interface ComposeApplicative<F, G> extends ComposeApply<F, G>, Applicative<__<__<Compose.µ, F>, G>> {

    @Override
    Applicative<F> getF();

    @Override
    Applicative<G> getG();

    @Override
    default <A> Compose<F, G, A> pure(A a) {
        return new Compose<>(getF().pure(getG().pure(a)));
    }
}
