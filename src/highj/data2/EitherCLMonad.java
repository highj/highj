/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.data.Either;
import highj.CL;
import highj._;
import highj.typeclasses.category.Monad;
import highj.typeclasses.category.MonadAbstract;

/**
 * Monad of Either (left-curried as in Haskell)
 * @author DGronau
 */
public class EitherCLMonad<X> extends MonadAbstract<CL<EitherOf, X>> implements Monad<CL<EitherOf, X>> {

    @Override
    public <A, B> _<CL<EitherOf, X>, B> bind(_<CL<EitherOf, X>, A> nestedA, F<A, _<CL<EitherOf, X>, B>> fn) {
        Either<X, A> eitherA = EitherOf.unwrapCL(nestedA);
        if (eitherA.isLeft()) {
            return EitherOf.wrapCL(Either.<X, B>left(eitherA.left().value()));
        } else {
            return fn.f(eitherA.right().value());
        }
    }

    @Override
    public <A, B> _<CL<EitherOf, X>, B> star(_<CL<EitherOf, X>, F<A, B>> fn, _<CL<EitherOf, X>, A> nestedA) {
        Either<X, F<A, B>> fnEither = EitherOf.unwrapCL(fn);
        if (fnEither.isLeft()) {
            return EitherOf.wrapCL(Either.<X, B>left(fnEither.left().value()));
        } else {
            Either<X, A> eitherA = EitherOf.unwrapCL(nestedA);
            if (eitherA.isLeft()) {
                return EitherOf.wrapCL(Either.<X, B>left(eitherA.left().value()));
            } else {
                F<A, B> function = fnEither.right().value();
                A argument = eitherA.right().value();
                return EitherOf.wrapCL(Either.<X, B>right(function.f(argument)));
            }
        }
    }

    @Override
    public <A, B> _<CL<EitherOf, X>, B> fmap(F<A, B> fn, _<CL<EitherOf, X>, A> nestedA) {
        Either<X, A> eitherA = EitherOf.unwrapCL(nestedA);
        Either<X, B> eitherB = Either.<X, A, B>rightMap_().f(fn).f(eitherA);
        return EitherOf.wrapCL(eitherB);
    }

    @Override
    public <A> _<CL<EitherOf, X>, A> pure(A a) {
        return EitherOf.wrapCL(Either.<X, A>right(a));
    }
}
