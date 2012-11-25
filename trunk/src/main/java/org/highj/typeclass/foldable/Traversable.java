package org.highj.typeclass.foldable;

import org.highj._;
import org.highj.function.F1;
import org.highj.typeclass.monad.Applicative;
import org.highj.typeclass.monad.Functor;

/**
 * The Traversable type class.
 *
 * Note that sequence and mapM are not needed, as Monads are Applicatives in highJ,
 * so you can replace sequence with sequenceA, and mapM with traverse.
 *
 * @param <µ>
 */
public interface Traversable<µ> extends Foldable<µ>, Functor<µ> {

    public <A,B,X> _<X,_<µ,B>> traverse(Applicative<X> applicative, F1<A,_<X,B>> fn, _<µ, A> traversable);

    public <A,X> _<X,_<µ,A>> sequenceA(Applicative<X> applicative, _<µ,_<X,A>> traversable);

}
