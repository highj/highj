package org.highj.data.functions.f1;

import org.derive4j.hkt.__;
import org.highj.data.functions.F1;
import org.highj.data.structural.Dual;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

public interface F1Contravariant<S> extends Contravariant<__<__<Dual.µ, F1.µ>, S>> {

    @Override
    default <A, B> Dual<F1.µ, S, A> contramap(Function<A, B> fn, __<__<__<Dual.µ, F1.µ>, S>, B> nested) {
        Dual<F1.µ, S, B> dual = Dual.narrow(nested);
        F1<B, S> dualFn = F1.narrow(dual.get());
        return new Dual<>(F1.compose(dualFn, fn));
    }
}
