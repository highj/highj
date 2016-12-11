package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.transformer.identity.*;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.foldable.Traversable1;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.*;

import java.util.function.Function;

import static org.highj.Hkt.asIdentityT;

public class IdentityT<M, A> implements __2<IdentityT.µ, M, A> {

    public interface µ {
    }

    private final __<M, A> value;

    public IdentityT(__<M, A> value) {
        this.value = value;
    }

    public __<M, A> get() {
        return value;
    }

    public static <M, A, N, B> Function<IdentityT<M, A>, IdentityT<N, B>> mapIdentityT(final Function<__<M, A>, __<N, B>> fn) {
        return ma -> new IdentityT<>(fn.apply(ma.get()));
    }

    public static <M, A, N, B, P, C> Function<IdentityT<M, A>, Function<IdentityT<N, B>, IdentityT<P, C>>> lift2IdentityT(
            final Function<__<M, A>, Function<__<N, B>, __<P, C>>> fn
    ) {
        return nestedA -> nestedB -> {
            IdentityT<M, A> aId = asIdentityT(nestedA);
            IdentityT<N, B> bId = asIdentityT(nestedB);
            return new IdentityT<>(fn.apply(aId.get()).apply(bId.get()));
        };
    }

    public static <M> IdentityTFunctor<M> functor(final Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    public static <M> IdentityTApply<M> apply(final Apply<M> mApply) {
        return () -> mApply;
    }

    public static <M> IdentityTApplicative<M> applicative(final Applicative<M> mApplicative) {
        return () -> mApplicative;
    }

    public static <M> IdentityTBind<M> bind(final Bind<M> mBind) {
        return () -> mBind;
    }

    public static <M> IdentityTMonadTrans<M> monadTrans(final Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> IdentityTMonadZero<M> monadZero(final MonadZero<M> mMonadZero) {
        return () -> mMonadZero;
    }

    public static <M> IdentityTMonadPlus<M> monadPlus(final MonadPlus<M> mMonadPlus) {
        return () -> mMonadPlus;
    }

    public static <M> IdentityTFoldable<M> foldable(final Foldable<M> mFoldable) {
        return () -> mFoldable;
    }

    public static <M> IdentityTTraversable<M> traversable(final Traversable<M> mTraversable) {
        return () -> mTraversable;
    }

    public static <M> IdentityTTraversable1<M> traversable1(final Traversable1<M> mTraversable1) {
        return () -> mTraversable1;
    }

    public static <M> IdentityTContravariant<M> contravariant(Contravariant<M> mContravariant) {
        return () -> mContravariant;
    }
}

