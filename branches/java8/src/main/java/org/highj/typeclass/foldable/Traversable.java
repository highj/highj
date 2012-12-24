package org.highj.typeclass.foldable;

import org.highj._;
import org.highj.__;
import org.highj.data.Const;
import org.highj.function.F1;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.monad.Applicative;
import org.highj.typeclass.monad.Functor;

/**
 * The Traversable type class.
 *
 * Note that sequence and mapM are not needed, as Monads are Applicatives in highJ,
 * so you can replace sequence with sequenceA, and mapM with traverse.
 *
 * Minimal complete definition: 'map' AND ('traverse' OR 'sequenceA').
 *
 */
public interface Traversable<mu> extends Foldable<mu>, Functor<mu> {

    public <A, B> _<mu, B> map(F1<A, B> fn, _<mu, A> nestedA);

    public default <A, B, X> _<X, _<mu, B>> traverse(Applicative<X> applicative, F1<A, _<X, B>> fn, _<mu, A> traversable) {
        return sequenceA(applicative, map(fn, traversable));
    }

    public default <A, X> _<X, _<mu, A>> sequenceA(Applicative<X> applicative, _<mu, _<X, A>> traversable) {
        return traverse(applicative, F1.<_<X, A>>id(), traversable);
    }

    public default <A, B> B foldMap(Monoid<B> mb, final F1<A, B> fn, _<mu, A> nestedA) {
        //foldMapDefault f = getConst . traverse (Const . f)
        Applicative<__.µ<Const.µ, B>> applicative = Const.applicative(mb);
        F1<A, _<__.µ<Const.µ, B>, B>> fun = new F1<A, _<__.µ<Const.µ, B>, B>>() {

            @Override
            public _<__.µ<Const.µ, B>, B> $(A a) {
                return new Const<B, B>(fn.$(a));
            }
        };
        return Const.narrow(traverse(applicative, fun, nestedA)).get();
    }

}
