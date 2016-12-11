package org.highj.function.f1;

import org.derive4j.hkt.__;
import org.highj.function.F1;
import org.highj.data.structural.Dual;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asDual;
import static org.highj.Hkt.asF1;

public interface F1Contravariant<S> extends Contravariant<__<__<Dual.µ, F1.µ>, S>> {

    @Override
    default <A, B> Dual<F1.µ, S, A> contramap(Function<A, B> fn, __<__<__<Dual.µ, F1.µ>, S>, B> nested) {
        Dual<F1.µ, S, B> dual = asDual(nested);
        F1<B, S> dualFn = asF1(dual.get());
        return new Dual<>(F1.compose(dualFn, fn));
    }
}
