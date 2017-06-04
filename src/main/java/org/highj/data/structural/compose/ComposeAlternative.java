package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Compose;
import org.highj.typeclass1.alternative.Alternative;

public interface ComposeAlternative<F, G> extends ComposeApplicative<F, G>, Alternative<__<__<Compose.µ, F>, G>> {

    @Override
    Alternative<F> getF();

    default <A> __<F, __<G, A>> __mzero() {
        return getF().mzero();
    }

    default <A> __<F, __<G, A>> __mplus(__<F, __<G, A>> first, __<F, __<G, A>> second) {
        return getF().mplus(first, second);
    }

    @Override
    default <A> Compose<F, G, A> mzero() {
        return new Compose<>(__mzero());
    }

    @Override
    default <A> Compose<F, G, A> mplus(__<__<__<Compose.µ, F>, G>, A> first, __<__<__<Compose.µ, F>, G>, A> second) {
        return new Compose<>(__mplus(Hkt.asCompose(first).get(), Hkt.asCompose(second).get()));
    }
}
