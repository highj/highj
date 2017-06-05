package org.highj.typeclass1.foldable;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asConst;
import static org.highj.data.structural.Const.*;

/**
 * The Traversable type class.
 *
 * Note that sequence and mapM are not needed, as monads are applicatives in highJ,
 * so you can replace sequence with sequenceA, and mapM with traverse.
 *
 * Minimal complete definition: 'map' AND ('traverse' OR 'sequenceA').
 *
 */
public interface Traversable<T> extends Foldable<T>, Functor<T> {

    @Override
    <A, B> __<T, B> map(Function<A, B> fn, __<T, A> as);

    default <A, B, X> __<X, __<T, B>> traverse(Applicative<X> applicative, Function<A, __<X, B>> fn, __<T, A> traversable) {
        return sequenceA(applicative, map(fn, traversable));
    }

    default <A, X> __<X, __<T, A>> sequenceA(Applicative<X> applicative, __<T, __<X, A>> traversable) {
        return traverse(applicative, Function.<__<X, A>>identity(), traversable);
    }

    @Override
    default <A, B> B foldMap(Monoid<B> mb, final Function<A, B> fn, __<T, A> nestedA) {
        Applicative<__<Const.µ, B>> applicative = applicative(mb);
        __<__<Const.µ,B>, __<T, A>> co = traverse(applicative, a -> Const(fn.apply(a)), nestedA);
        return asConst(co).get();
    }

}
