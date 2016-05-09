package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.transformer.reader.*;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface ReaderT<R, M, A> extends __3<ReaderT.µ, R, M, A> {
    public static class µ {
    }

    public static <R, M, A> ReaderT<R, M, A> narrow(__<__<__<µ, R>, M>, A> a) {
        return (ReaderT<R, M, A>) a;
    }

    public __<M, A> run(R r);

    public static <R, M> ReaderTFunctor<R, M> functor(Functor<M> mFunctor) {
        return (ReaderTFunctor<R, M>) () -> mFunctor;
    }

    public static <R, M> ReaderTApply<R, M> apply(Apply<M> mApply) {
        return (ReaderTApply<R, M>) () -> mApply;
    }

    public static <R, M> ReaderTApplicative<R, M> applicative(Applicative<M> mApplicative) {
        return (ReaderTApplicative<R, M>) () -> mApplicative;
    }

    public static <R, M> ReaderTBind<R, M> bind(Bind<M> mBind) {
        return (ReaderTBind<R, M>) () -> mBind;
    }

    public static <R, M> ReaderTMonad<R, M> monad(Monad<M> mMonad) {
        return (ReaderTMonad<R, M>) () -> mMonad;
    }

    public static <R, M> ReaderTMonadReader<R, M> monadReader(Monad<M> mMonad) {
        return (ReaderTMonadReader<R, M>) () -> mMonad;
    }

    public static <R, M> ReaderTMonadTrans<R, M> monadTrans(Monad<M> mMonad) {
        return (ReaderTMonadTrans<R, M>) () -> mMonad;
    }
}
