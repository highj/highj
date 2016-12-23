package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Compose;
import org.highj.typeclass1.alternative.Alternative;

public interface ComposeAlternative<F,G> extends ComposeApplicative<F,G>, Alternative<__<__<Compose.µ, F>, G>> {

    @Override
    Alternative<F> getF();

    @Override
    default <A> Compose<F, G, A> mzero() {
        return new Compose<>(getF().mzero());
    }

    @Override
    default <A> Compose<F, G, A> mplus(__<__<__<Compose.µ, F>, G>, A> first, __<__<__<Compose.µ, F>, G>, A> second) {
        __<F, __<G, A>> fga1 = Hkt.asCompose(first).get();
        __<F, __<G, A>> fga2 = Hkt.asCompose(second).get();
        return new Compose<>(getF().mplus(fga1, fga2));
    }
}
