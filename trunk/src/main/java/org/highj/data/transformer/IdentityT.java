package org.highj.data.transformer;

import org.highj._;
import org.highj.__;
import org.highj.data.transformer.identity.*;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.foldable.Traversable1;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.*;

import java.util.function.Function;

public class IdentityT<M, A> implements __<IdentityT.µ, M, A> {

    public static class µ {
    }

    private final _<M, A> value;

    public IdentityT(_<M, A> value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <M, A> IdentityT<M, A> narrow(_<__.µ<µ, M>, A> value) {
        return (IdentityT) value;
    }

    public _<M, A> get() {
        return value;
    }

    public static <M, A, N, B> Function<IdentityT<M, A>, IdentityT<N, B>> mapIdentityT(final Function<_<M, A>, _<N, B>> fn) {
        return ma -> new IdentityT<>(fn.apply(ma.get()));
    }

    public static <M, A, N, B, P, C> Function<IdentityT<M, A>, Function<IdentityT<N, B>, IdentityT<P, C>>> lift2IdentityT(
            final Function<_<M, A>, Function<_<N, B>, _<P, C>>> fn
    ) {
        return nestedA -> nestedB -> {
            IdentityT<M, A> aId = IdentityT.narrow(nestedA);
            IdentityT<N, B> bId = IdentityT.narrow(nestedB);
            return new IdentityT<>(fn.apply(aId.get()).apply(bId.get()));
        };
    }

    public static <M> Functor<__.µ<µ, M>> functor(final Functor<M> functorM) {
        return (IdentityTFunctor<M>) () -> functorM;
    }

    public static <M> Apply<__.µ<µ, M>> apply(final Apply<M> applyM) {
        return (IdentityTApply<M>) () -> applyM;
    }

    public static <M> Applicative<__.µ<µ, M>> applicative(final Applicative<M> applicativeM) {
        return (IdentityTApplicative<M>) () -> applicativeM;
    }

    public static <M> Bind<__.µ<µ, M>> bind(final Bind<M> bindM) {
        return (IdentityTBind<M>) () -> bindM;
    }

    public static <M> MonadTrans<µ, M> monadTrans(final Monad<M> monad) {
        return (IdentityTMonadTrans<M>) () -> monad;
    }

    public static <M> MonadZero<__.µ<µ, M>> monadZero(final MonadZero<M> monadZero) {
        return (IdentityTMonadZero<M>) () -> monadZero;
    }

    public static <M> MonadPlus<__.µ<µ, M>> monadPlus(final MonadPlus<M> monadPlus) {
        return (IdentityTMonadPlus<M>) () -> monadPlus;
    }

    public static <M> Foldable<__.µ<µ, M>> foldable(final Foldable<M> foldableM) {
        return (IdentityTFoldable<M>) () -> foldableM;
    }

    public static <M> Traversable<__.µ<µ, M>> traversable(final Traversable<M> traversableM) {
        return (IdentityTTraversable<M>) () -> traversableM;
    }

    public static <M> Traversable1<__.µ<µ, M>> traversable1(final Traversable1<M> traversable1M) {
        return (IdentityTTraversable1<M>) () -> traversable1M;
    }

}


/*

instance (MonadFix m) => MonadFix (IdentityT m) where
    mfix f = IdentityT (mfix (runIdentityT . f))

instance (MonadIO m) => MonadIO (IdentityT m) where
    liftIO = IdentityT . liftIO

-- | Lift a @callCC@ operation to the new monadTrans.
liftCallCC :: (((a -> m b) -> m a) ->
    m a) -> ((a -> IdentityT m b) -> IdentityT m a) -> IdentityT m a
liftCallCC callCC f =
    IdentityT $ callCC $ \ c -> runIdentityT (f (IdentityT . c))

-- | Lift a @catchError@ operation to the new monadTrans.
liftCatch :: (m a -> (e -> m a) -> m a) ->
    IdentityT m a -> (e -> IdentityT m a) -> IdentityT m a
liftCatch f m h = IdentityT $ f (runIdentityT m) (runIdentityT . h)
*/