package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.data.transformer.maybe.*;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.*;

/**
 * @param <M> the wrapped monad
 * @param <A> the element type
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public class MaybeT<M, A> implements __2<MaybeT.µ, M, A> {

    public static class µ {
    }

    private final __<M, Maybe<A>> value;

    public MaybeT(__<M, Maybe<A>> value) {
        this.value = value;
    }

    public __<M, Maybe<A>> get() {
        return value;
    }

    public static <M> MaybeTFunctor<M> functor(final Functor<M> functorM) {
        return () -> functorM;
    }

    public static <M> MaybeTApply<M> apply(final Apply<M> applyM) {
        return () -> applyM;
    }

    public static <M> MaybeTApplicative<M> applicative(final Applicative<M> applicativeM) {
        return () -> applicativeM;
    }

    public static <M> MaybeTBind<M> bind(final Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> MaybeTMonad<M> monad(final Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> MaybeTMonadTrans<M> monadTrans(final Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> MaybeTMonadRec<M> monadRec(final MonadRec<M> mMonadRec) {
        return () -> mMonadRec;
    }

    public static <M> MaybeTContravariant<M> contravariant(final Contravariant<M> mContravariant) {
        return () -> mContravariant;
    }
}
