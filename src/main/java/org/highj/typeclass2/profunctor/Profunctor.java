package org.highj.typeclass2.profunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.typeclass2.arrow.Category;

import java.util.function.Function;

/**
 * A profunctor can be thought of as a bifunctor where the first argument is contravariant and the second argument is covariant.
 *
 * Minimal definition: dimap OR (lmap AND rmap)
 *
 * @param <P> the profunctor type class
 */
public interface Profunctor<P> {

    default <A, B, C, D> __2<P, A, D> dimap(Function<A, B> f, Function<C, D> g, __<__<P, B>, C> p) {
        return rmap(g, lmap(f, p));
    }

    default <A, B, C> __2<P, A, C> lmap(Function<A, B> f, __<__<P, B>, C> p) {
        return dimap(f, Function.identity(), p);
    }

    default <A, B, C> __2<P, A, C> rmap(Function<B, C> g, __<__<P, A>, B> p) {
        return dimap(Function.identity(), g, p);
    }

    default <A, B> __2<P, A, B> arr(Category<P> category, Function<A, B> f) {
        return rmap(f, category.identity());
    }
}
