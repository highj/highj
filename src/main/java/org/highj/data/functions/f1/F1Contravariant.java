package org.highj.data.functions.f1;

import org.highj._;
import org.highj.data.functions.F1;
import org.highj.data.structural.Dual;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

public interface F1Contravariant<S> extends Contravariant<_<_<Dual.µ, F1.µ>, S>> {

    @Override
    default <A, B> Dual<F1.µ, S, A> contramap(Function<A, B> fn, _<_<_<Dual.µ, F1.µ>, S>, B> nested) {
        Dual<F1.µ, S, B> dual = Dual.narrow(nested);
        F1<B, S> dualFn = F1.narrow(dual.get());
        return F1.compose(dualFn, fn).dual();
    }
}
