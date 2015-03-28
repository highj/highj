package org.highj.data.transformer;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.reader.*;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

import static org.highj.HigherKinded.uncurry3;

/**
 * @author Clinton Selke
 */
public interface ReaderT<R, M, A> extends ___<ReaderT.µ, R, M, A> {
    public static class µ {
    }

    public static <R, M, A> ReaderT<R, M, A> narrow(___<ReaderT.µ, R, M, A> a) {
        return (ReaderT<R, M, A>) a;
    }

    public static <R, M, A> ReaderT<R, M, A> narrow(_<__.µ<___.µ<ReaderT.µ, R>, M>, A> a) {
        return narrow(uncurry3(a));
    }

    public _<M, A> run(R r);

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
