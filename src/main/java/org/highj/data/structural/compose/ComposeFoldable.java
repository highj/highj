package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Compose;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Foldable;

import java.util.function.Function;

public interface ComposeFoldable<F, G> extends Foldable<__<__<Compose.µ, F>, G>> {

    Foldable<F> getF();

    Foldable<G> getG();

    default <A, B> B __foldMap(final Monoid<B> mb, final Function<A, B> fn, __<F, __<G, A>> nestedA) {
        return getF().foldMap(mb, ga -> getG().foldMap(mb, fn, ga), nestedA);
    }

    @Override
    default <A, B> B foldMap(final Monoid<B> mb, final Function<A, B> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        return __foldMap(mb, fn, Hkt.asCompose(nestedA).get());
    }
}
