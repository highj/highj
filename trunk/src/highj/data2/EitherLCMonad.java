/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.data.Either;
import highj.LC;
import highj._;
import highj.typeclasses.category.Monad;
import highj.typeclasses.category.MonadAbstract;

/**
 * Monad of Either (left-curried as in Haskell)
 * @author DGronau
 */
public class EitherLCMonad<X> extends MonadAbstract<LC<EitherOf, X>> implements Monad<LC<EitherOf, X>> {

    @Override
    public <A, B> _<LC<EitherOf, X>, B> bind(_<LC<EitherOf, X>, A> nestedA, F<A, _<LC<EitherOf, X>, B>> fn) {
        Either<X, A> eitherA = EitherOf.unwrapLC(nestedA);
        if (eitherA.isLeft()) {
            return EitherOf.wrapLC(Either.<X, B>left(eitherA.left().value()));
        } else {
            return fn.f(eitherA.right().value());
        }
    }

    @Override
    public <A, B> _<LC<EitherOf, X>, B> ap(_<LC<EitherOf, X>, F<A, B>> fn, _<LC<EitherOf, X>, A> nestedA) {
        Either<X, F<A, B>> fnEither = EitherOf.unwrapLC(fn);
        if (fnEither.isLeft()) {
            return EitherOf.wrapLC(Either.<X, B>left(fnEither.left().value()));
        } else {
            Either<X, A> eitherA = EitherOf.unwrapLC(nestedA);
            if (eitherA.isLeft()) {
                return EitherOf.wrapLC(Either.<X, B>left(eitherA.left().value()));
            } else {
                F<A, B> function = fnEither.right().value();
                A argument = eitherA.right().value();
                return EitherOf.wrapLC(Either.<X, B>right(function.f(argument)));
            }
        }
    }

    @Override
    public <A, B> _<LC<EitherOf, X>, B> fmap(F<A, B> fn, _<LC<EitherOf, X>, A> nestedA) {
        Either<X, A> eitherA = EitherOf.unwrapLC(nestedA);
        Either<X, B> eitherB = Either.<X, A, B>rightMap_().f(fn).f(eitherA);
        return EitherOf.wrapLC(eitherB);
    }

    @Override
    public <A> _<LC<EitherOf, X>, A> pure(A a) {
        return EitherOf.wrapLC(Either.<X, A>right(a));
    }

    @Override
    public <A> F<A, _<LC<EitherOf, X>, A>> pure() {
        return new F<A, _<LC<EitherOf, X>, A>>() {
            @Override
            public _<LC<EitherOf, X>, A> f(A a) {
                return pure(a);
            }
        };
    }
}
