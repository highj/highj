/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.Either;
import org.highj.data.transformer.error.*;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

/**
 * @author clintonselke
 */
public interface ErrorT<E, M, A> extends __3<ErrorT.µ, E, M, A> {
    interface µ {
    }

    __<M, Either<E, A>> run();

    static <E, M> ErrorTFunctor<E, M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    static <E, M> ErrorTApply<E, M> apply(Apply<M> mApply) {
        return () -> mApply;
    }

    static <E, M> ErrorTApplicative<E, M> applicative(Applicative<M> mApplicative) {
        return () -> mApplicative;
    }

    static <E, M> ErrorTBind<E, M> bind(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <E, M> ErrorTMonad<E, M> monad(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <E, M> ErrorTMonadError<E, M> monadError(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <E, M> ErrorTMonadRec<E, M> monadRec(MonadRec<M> mMonadRec) {
        return () -> mMonadRec;
    }

    static <E, M> ErrorTMonadTrans<E, M> monadTrans(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <E, M> ErrorTContravariant<E, M> contravariant(Contravariant<M> mContravariant) {
        return () -> mContravariant;
    }
}
