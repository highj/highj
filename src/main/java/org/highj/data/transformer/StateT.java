package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.transformer.state.*;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface StateT<S, M, A> extends __3<StateT.µ, S, M, A> {
    interface µ {
    }

    __<M, T2<A, S>> run(S s);

    default __<M, A> eval(Functor<M> mFunctor, S s) {
        return mFunctor.map(T2::_1, run(s));
    }

    default __<M, S> run(Functor<M> mFunctor, S s) {
        return mFunctor.map(T2::_2, run(s));
    }

    static <S, M> StateTFunctor<S, M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    static <S, M> StateTApply<S, M> apply(Bind<M> mBind) {
        return () -> mBind;
    }

    static <S, M> StateTApplicative<S, M> applicative(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <S, M> StateTBind<S, M> bind(Bind<M> mBind) {
        return () -> mBind;
    }

    static <S, M> StateTMonad<S, M> monad(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <S, M> StateTMonadTrans<S, M> monadTrans(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <S, M> StateTMonadState<S, M> monadState(Monad<M> mMonad) {
        return () -> mMonad;
    }

    static <S, M> StateTContravariant<S, M> contravariant(Contravariant<M> mContravariant) {
        return () -> mContravariant;
    }
}