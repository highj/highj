package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.structural.Compose;

public interface ComposeEq1<F, G> extends Eq1<__<__<Compose.µ, F>, G>> {

    Eq1<F> getF();

    Eq1<G> getG();

    default <A> Eq<__<F, __<G, A>>> __eq1(Eq<? super A> eq) {
        return (one, two) -> {
            Eq<__<G, A>> eqG = getG().eq1(eq);
            return getF().eq1(eqG).eq(one, two);
        };
    }

    @Override
    default <A> Eq<__<__<__<Compose.µ, F>, G>, A>> eq1(Eq<? super A> eq) {
        return (one, two) -> {
            Compose<F, G, A> composeOne = Hkt.asCompose(one);
            Compose<F, G, A> composeTwo = Hkt.asCompose(two);
            Eq<__<G, A>> eqG = getG().eq1(eq);
            return getF().eq1(eqG).eq(composeOne.get(), composeTwo.get());
        };
    }
}
