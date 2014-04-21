package org.highj.typeclass1.foldable;

import org.highj._;
import org.highj.__;
import org.highj.data.structural.Const;
import org.highj.data.functions.Functions;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.functor.Functor;

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
public interface Traversable<T> extends Foldable<T>, Functor<T> {

    @Override
    public <A, B> _<T, B> map(Function<A, B> fn, _<T, A> as);

    public default <A, B, X> _<X, _<T, B>> traverse(Applicative<X> applicative, Function<A, _<X, B>> fn, _<T, A> traversable) {
        return sequenceA(applicative, map(fn, traversable));
    }

    public default <A, X> _<X, _<T, A>> sequenceA(Applicative<X> applicative, _<T, _<X, A>> traversable) {
        return traverse(applicative, Function.<_<X, A>>identity(), traversable);
    }

    @Override
    public default <A, B> B foldMap(Monoid<B> mb, final Function<A, B> fn, _<T, A> nestedA) {
        //foldMapDefault f = getConst . traverse (Const . f)
        Applicative<__.µ<Const.µ, B>> applicative = Const.applicative(mb);
        _<__.µ<Const.µ,B>, _<T, A>> co = traverse(applicative, a -> new Const<>(fn.apply(a)), nestedA);
        return Const.narrow(co).get();
    }

}
