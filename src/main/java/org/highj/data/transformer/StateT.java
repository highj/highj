package org.highj.data.transformer;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.state.*;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

import static org.highj.HigherKinded.uncurry3;

/**
 * @author Clinton Selke
 */
public interface StateT<S, M, A> extends ___<StateT.µ, S, M, A> {
    public static class µ {
    }

    public static <S, M, A> StateT<S, M, A> narrow(___<StateT.µ, S, M, A> a) {
        return (StateT<S, M, A>) a;
    }

    public static <S, M, A> StateT<S, M, A> narrow(_<_<_<StateT.µ, S>, M>, A> a) {
        return narrow(uncurry3(a));
    }

    public _<M, T2<A, S>> run(S s);

    public default _<M, A> eval(Functor<M> mFunctor, S s) {
        return mFunctor.map(T2::_1, run(s));
    }

    public default _<M, S> run(Functor<M> mFunctor, S s) {
        return mFunctor.map(T2::_2, run(s));
    }

    public static <S, M> StateTFunctor<S, M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    public static <S, M> StateTApply<S, M> apply(Bind<M> mBind) {
        return () -> mBind;
    }

    public static <S, M> StateTApplicative<S, M> applicative(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <S, M> StateTBind<S, M> bind(Bind<M> mBind) {
        return () -> mBind;
    }

    public static <S, M> StateTMonad<S, M> monad(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <S, M> StateTMonadTrans<S, M> monadTrans(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <S, M> StateTMonadState<S, M> monadState(Monad<M> mMonad) {
        return () -> mMonad;
    }
}