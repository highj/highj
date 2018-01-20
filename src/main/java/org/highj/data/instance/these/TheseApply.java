package org.highj.data.instance.these;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.data.These.*;

public interface TheseApply<F> extends TheseFunctor<F>, Apply<__<These.µ, F>> {
    Semigroup<F> get();

    @Override
    default <A, B> These<F, B> ap(__<__<These.µ, F>, Function<A, B>> fn, __<__<These.µ, F>, A> nestedA) {
        return Hkt.asThese(fn).these(
                These::This,
                abFn -> Hkt.asThese(nestedA).these(
                        These::This,
                        a2 -> That(abFn.apply(a2)),
                        (f2, a2) -> Both(f2, abFn.apply(a2))
                ),
                (f1, abFn) -> Hkt.asThese(nestedA).these(
                        f2 -> This(get().apply(f1, f2)),
                        a2 -> Both(f1, abFn.apply(a2)),
                        (f2, a2) -> Both(get().apply(f1, f2), abFn.apply(a2))
                ));
    }
}
