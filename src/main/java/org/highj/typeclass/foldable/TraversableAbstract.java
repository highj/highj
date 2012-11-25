package org.highj.typeclass.foldable;

import org.highj._;
import org.highj.__;
import org.highj.data.Const;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.monad.Applicative;
import org.highj.typeclass.group.Monoid;

/**
 * A helper class for implementing Traversable.
 * <p/>
 * Minimal complete definition: 'map' AND ('traverse' OR 'sequenceA').
 * <p/>
 * Note that sequence and mapM are not needed, as Monads are Applicatives in highJ,
 * so you can replace sequence with sequenceA, and mapM with traverse.
 *
 * @param <mu>
 */
public abstract class TraversableAbstract<mu> extends FoldableAbstract<mu> implements Traversable<mu> {

    @Override
    public <A, B, X> _<X, _<mu, B>> traverse(Applicative<X> applicative, F1<A, _<X, B>> fn, _<mu, A> traversable) {
        return sequenceA(applicative, map(fn, traversable));
    }

    @Override
    public <A, X> _<X, _<mu, A>> sequenceA(Applicative<X> applicative, _<mu, _<X, A>> traversable) {
        return traverse(applicative, F1.<_<X, A>>id(), traversable);
    }

    @Override
    public <A, B> B foldMap(Monoid<B> mb, final F1<A, B> fn, _<mu, A> nestedA) {
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

    @Override
    public abstract <A, B> _<mu, B> map(F1<A, B> fn, _<mu, A> nestedA);

    //Duplicated from FunctorAbstract

    // <$  (Data.Functor)
    public <A, B> _<mu, A> left$(A a, _<mu, B> nestedB) {
        return map(F2.<A, B>constant().$(a), nestedB);
    }

    //void (Control.Monad)
    public <A> _<mu, T0> voidF(_<mu, A> nestedA) {
        return left$(T0.unit, nestedA);
    }

    //flip (Data.Functor.Syntax)
    public <A, B> _<mu, B> flip(_<mu, F1<A, B>> nestedFn, final A a) {
        return map(new F1<F1<A, B>, B>() {
            @Override
            public B $(F1<A, B> fn) {
                return fn.$(a);
            }
        }, nestedFn);
    }

    //liftA (Control.Applicative), liftM (Control.Monad), curried version of fmap
    public <A, B> F1<_<mu, A>, _<mu, B>> lift(final F1<A, B> fn) {
        return new F1<_<mu, A>, _<mu, B>>() {
            @Override
            public _<mu, B> $(_<mu, A> a) {
                return map(fn, a);
            }
        };
    }
}
