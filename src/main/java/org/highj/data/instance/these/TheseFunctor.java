package org.highj.data.instance.these;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface TheseFunctor<F> extends Functor<__<These.µ, F>> {
    @Override
    default <A, B> These<F, B> map(Function<A, B> fn, __<__<These.µ, F>, A> nestedA) {
        return Hkt.asThese(nestedA).these(
                These::This,
                a -> These.That(fn.apply(a)),
                (f, a) -> These.Both(f, fn.apply(a))
        );
    }
}
