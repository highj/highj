package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.transformer.reader.*;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
@FunctionalInterface
public interface ReaderT<R, M, A> extends __3<ReaderT.µ, R, M, A> {
    interface µ {
    }

    __<M, A> run(R r);

    static <R, M> ReaderTFunctor<R, M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    static <R, M> ReaderTApply<R, M> apply(Apply<M> mApply) {
        return () -> mApply;
    }

    static <R, M> ReaderTApplicative<R, M> applicative(Applicative<M> mApplicative) {
        return () -> mApplicative;
    }

    static <R, M> ReaderTBind<R, M> bind(Bind<M> mBind) {
        return () -> mBind;
    }

    static <R, M> ReaderTMonad<R, M> monad(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <R, M> ReaderTMonadReader<R, M> monadReader(Monad<M> mMonad) {
        return  () -> mMonad;
    }

    static <R, M> ReaderTMonadTrans<R, M> monadTrans(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <R, M> ReaderTContravariant<R, M> contravariant(Contravariant<M> mContravariant) {
        return () -> mContravariant;
    }
}
