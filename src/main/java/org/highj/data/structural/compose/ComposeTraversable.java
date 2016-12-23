package org.highj.data.structural.compose;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Compose;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

public interface ComposeTraversable<F, G> extends ComposeFoldable<F, G>, ComposeFunctor<F, G>, Traversable<__<__<Compose.µ, F>, G>> {
    @Override
    Traversable<F> getF();

    @Override
    Traversable<G> getG();

    @Override
    default <A, B> Compose<F, G, B> map(Function<A, B> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        return ComposeFunctor.super.map(fn, nestedA);
    }

    @Override
    default <A, B> B foldMap(final Monoid<B> mb, final Function<A, B> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        return ComposeFoldable.super.foldMap(mb, fn, nestedA);
    }

    @Override
    default <A, B, X> __<X, __<__<__<Compose.µ, F>, G>, B>> traverse(Applicative<X> applicative, Function<A, __<X, B>> fn, __<__<__<Compose.µ, F>, G>, A> traversable) {
        __<F, __<G, A>> fga = Hkt.asCompose(traversable).get();
        return applicative.map(Compose::new,
                getF().traverse(applicative,
                        ga -> getG().traverse(applicative, fn, ga),
                        fga));
    }
}
