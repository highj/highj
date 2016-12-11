package org.highj.data.predicates.pred;

import org.derive4j.hkt.__;
import org.highj.data.predicates.Pred;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.contravariant.Divisible;

import java.util.function.Function;

import static org.highj.Hkt.asPred;

public interface PredDivisible extends PredContravariant, Divisible<Pred.µ> {

    @Override
    default <A, B, C> Pred<A> divide(Function<A, T2<B, C>> fn, __<Pred.µ, B> fb, __<Pred.µ, C> fc) {
        return a -> fn.apply(a).cata(
                (b, c) -> asPred(fb).test(b)
                        && asPred(fc).test(c));
    }

    @Override
    default <A> Pred<A> conquer() {
        return Pred.True();
    }
}
