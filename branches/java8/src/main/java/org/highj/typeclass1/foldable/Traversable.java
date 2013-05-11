package org.highj.typeclass1.foldable;

import org.highj._;
import org.highj.__;
import org.highj.data.Const;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Functor;

import java.util.function.Function;

/**
 * The Traversable type class.
 *
 * Note that sequence and mapM are not needed, as Monads are Applicatives in highJ,
 * so you can replace sequence with sequenceA, and mapM with traverse.
 *
 * Minimal complete definition: 'map' AND ('traverse' OR 'sequenceA').
 *
 */
public interface Traversable<µ> extends Foldable<µ>, Functor<µ> {

    @Override
    public <A, B> _<µ, B> map(Function<A, B> fn, _<µ, A> as);

    public default <A, B, X> _<X, _<µ, B>> traverse(Applicative<X> applicative, Function<A, _<X, B>> fn, _<µ, A> traversable) {
        return sequenceA(applicative, map(fn, traversable));
    }

    public default <A, X> _<X, _<µ, A>> sequenceA(Applicative<X> applicative, _<µ, _<X, A>> traversable) {
        return traverse(applicative, Functions.<_<X, A>>id(), traversable);
    }

    @Override
    public default <A, B> B foldMap(Monoid<B> mb, final Function<A, B> fn, _<µ, A> nestedA) {
        //foldMapDefault f = getConst . traverse (Const . f)
        Applicative<__.µ<Const.µ, B>> applicative = Const.applicative(mb);
        _<__.µ<Const.µ,B>, _<µ, A>> co = traverse(applicative, a -> new Const<>(fn.apply(a)), nestedA);
        return Const.narrow(co).get();
    }

}
