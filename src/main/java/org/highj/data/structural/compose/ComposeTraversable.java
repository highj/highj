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
    default <A, B> __<F, __<G, B>> __map(Function<A, B> fn, __<F, __<G, A>> nestedA) {
        return ComposeFunctor.super.__map(fn, nestedA);
    }

    @Override
    default <A, B> Compose<F, G, B> map(Function<A, B> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        return ComposeFunctor.super.map(fn, nestedA);
    }

    @Override
    default <A, B> B __foldMap(final Monoid<B> mb, final Function<A, B> fn, __<F, __<G, A>> nestedA) {
        return ComposeFoldable.super.__foldMap(mb, fn, nestedA);
    }

    @Override
    default <A, B> B foldMap(final Monoid<B> mb, final Function<A, B> fn, __<__<__<Compose.µ, F>, G>, A> nestedA) {
        return ComposeFoldable.super.foldMap(mb, fn, nestedA);
    }

    default <A, B, X> __<X, __<F, __<G, B>>> __traverse(Applicative<X> applicative, Function<A, __<X, B>> fn, __<F, __<G, A>> traversable) {
        return getF().traverse(applicative,
                ga -> getG().traverse(applicative, fn, ga),
                traversable);
    }

    @Override
    default <A, B, X> __<X, __<__<__<Compose.µ, F>, G>, B>> traverse(Applicative<X> applicative, Function<A, __<X, B>> fn, __<__<__<Compose.µ, F>, G>, A> traversable) {
        return applicative.map(Compose::new, __traverse(applicative, fn, Hkt.asCompose(traversable).get()));
    }
}
