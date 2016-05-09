package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.identity.*;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.foldable.Traversable1;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.*;

import java.util.function.Function;

public class IdentityT<M, A> implements __2<IdentityT.µ, M, A> {

    public static class µ {
    }

    private final __<M, A> value;

    public IdentityT(__<M, A> value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <M, A> IdentityT<M, A> narrow(__<__<µ, M>, A> value) {
        return (IdentityT) value;
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
            IdentityT<M, A> aId = IdentityT.narrow(nestedA);
            IdentityT<N, B> bId = IdentityT.narrow(nestedB);
            return new IdentityT<>(fn.apply(aId.get()).apply(bId.get()));
        };
    }

    public static <M> IdentityTFunctor<M> functor(final Functor<M> functorM) {
        return () -> functorM;
    }

    public static <M> IdentityTApply<M> apply(final Apply<M> applyM) {
        return () -> applyM;
    }

    public static <M> IdentityTApplicative<M> applicative(final Applicative<M> applicativeM) {
        return () -> applicativeM;
    }

    public static <M> IdentityTBind<M> bind(final Bind<M> bindM) {
        return () -> bindM;
    }

    public static <M> IdentityTMonadTrans<M> monadTrans(final Monad<M> monad) {
        return () -> monad;
    }

    public static <M> IdentityTMonadZero<M> monadZero(final MonadZero<M> monadZero) {
        return () -> monadZero;
    }

    public static <M> IdentityTMonadPlus<M> monadPlus(final MonadPlus<M> monadPlus) {
        return () -> monadPlus;
    }

    public static <M> IdentityTFoldable<M> foldable(final Foldable<M> foldableM) {
        return () -> foldableM;
    }

    public static <M> IdentityTTraversable<M> traversable(final Traversable<M> traversableM) {
        return () -> traversableM;
    }

    public static <M> IdentityTTraversable1<M> traversable1(final Traversable1<M> traversable1M) {
        return () -> traversable1M;
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