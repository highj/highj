package org.highj.data.instance.these;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.data.These.Both;
import static org.highj.data.These.This;

public interface TheseBind<F> extends TheseApply<F>, Bind<__<These.µ, F>> {

    @Override
    default <A, B> These<F, B> bind(__<__<These.µ, F>, A> nestedA, Function<A, __<__<These.µ, F>, B>> fn) {
        return Hkt.asThese(nestedA).these(
                These::This,
                a1 -> Hkt.asThese(fn.apply(a1)),
                (f1, a1) -> Hkt.asThese(fn.apply(a1)).these(
                        f2 -> This(get().apply(f1, f2)),
                        b2 -> Both(f1, b2),
                        (f2, b2) -> Both(get().apply(f1, f2), b2)));
    }
}
