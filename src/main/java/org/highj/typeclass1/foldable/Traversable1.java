package org.highj.typeclass1.foldable;

import org.derive4j.hkt.__;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

/**
 * The Traversable1 type class.
 *
 * Minimal complete definition: ('traverse1' OR 'sequence1') AND minimal definitions for Foldable and Traversable.
 */

public interface Traversable1<T> extends Traversable<T>, Foldable<T> {

    //traverse1 :: Apply f => (a -> f b) -> t a -> f (t b)
    //traverse1 f = sequence1 . fmap f
    public default <A,B,F> __<F,__<T,B>> traverse1(Apply<F> apply, Function<A,__<F,B>> fn, __<T,A> traversable) {
        return sequence1(apply, map(fn, traversable));
    }

    //sequence1 :: Apply f => t (f b) -> f (t b)
    //sequence1 = traverse1 id
    public default <B, F> __<F,__<T,B>> sequence1(Apply<F> apply, __<T,__<F,B>> traversable) {
        return traverse1(apply, x -> x, traversable);
    }

}
