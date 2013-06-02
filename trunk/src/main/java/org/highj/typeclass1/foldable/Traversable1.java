package org.highj.typeclass1.foldable;

import org.highj._;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

/**
 * The Traversable1 type class.
 *
 * Minimal complete definition: ('traverse1' OR 'sequence1') AND minimal definitions for Foldable and Traversable.
 */

public interface Traversable1<µ> extends Traversable<µ>, Foldable<µ> {

    //traverse1 :: Apply f => (a -> f b) -> t a -> f (t b)
    //traverse1 f = sequence1 . fmap f
    public default <A,B,F> _<F,_<µ,B>> traverse1(Apply<F> apply, Function<A,_<F,B>> fn, _<µ,A> traversable) {
        return sequence1(apply, map(fn, traversable));
    }

    //sequence1 :: Apply f => t (f b) -> f (t b)
    //sequence1 = traverse1 id
    public default <B,F> _<F,_<µ,B>> sequence1(Apply<F> apply, _<µ,_<F,B>> traversable) {
        return traverse1(apply, x -> x, traversable);
    }

}
