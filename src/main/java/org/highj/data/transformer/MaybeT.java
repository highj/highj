package org.highj.data.transformer;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Maybe;
import org.highj.data.transformer.maybe.MaybeTApplicative;
import org.highj.data.transformer.maybe.MaybeTApply;
import org.highj.data.transformer.maybe.MaybeTBind;
import org.highj.data.transformer.maybe.MaybeTFunctor;
import org.highj.data.transformer.maybe.MaybeTMonad;
import org.highj.data.transformer.maybe.MaybeTMonadTrans;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Monad;

/**
 * @param <M> the wrapped monad
 * @param <A> the element type
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public class MaybeT<M, A> implements __<MaybeT.µ, M, A> {

    public static class µ {
    }

    private final _<M, Maybe<A>> value;

    public MaybeT(_<M, Maybe<A>> value) {
        this.value = value;
    }

    public _<M, Maybe<A>> get() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public static <M, A> MaybeT<M, A> narrow(_<__.µ<µ, M>, A> value) {
        return (MaybeT) value;
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

}


/*

-- | Transform the computation inside a @MaybeT@.
--
-- * @'runMaybeT' ('mapMaybeT' f m) = f ('runMaybeT' m)@
mapMaybeT :: (m (Maybe a) -> n (Maybe b)) -> MaybeT m a -> MaybeT n b
mapMaybeT f = MaybeT . f . runMaybeT

instance (Foldable f) => Foldable (MaybeT f) where
    foldMap f (MaybeT a) = foldMap (foldMap f) a

instance (Traversable f) => Traversable (MaybeT f) where
    traverse f (MaybeT a) = MaybeT <$> traverse (traverse f) a

instance (Functor m, Monad m) => Applicative (MaybeT m) where
    pure = return
    (<*>) = ap

instance (Functor m, Monad m) => Alternative (MaybeT m) where
    empty = mzero
    (<|>) = mplus

instance (Monad m) => Monad (MaybeT m) where
    fail _ = MaybeT (return Nothing)
    return = lift . return
    x >>= f = MaybeT $ do
        v <- runMaybeT x
        case v of
            Nothing -> return Nothing
            JustLazy y  -> runMaybeT (f y)

instance (Monad m) => MonadPlus (MaybeT m) where
    mzero = MaybeT (return Nothing)
    mplus x y = MaybeT $ do
        v <- runMaybeT x
        case v of
            Nothing -> runMaybeT y
            JustLazy _  -> return v

instance (MonadFix m) => MonadFix (MaybeT m) where
    mfix f = MaybeT (mfix (runMaybeT . f . unJust))
      where unJust = fromMaybe (error "mfix MaybeT: Nothing")

instance MonadTrans MaybeT where
    lift = MaybeT . liftM JustLazy

instance (MonadIO m) => MonadIO (MaybeT m) where
    liftIO = lift . liftIO

-- | Lift a @callCC@ operation to the new monadTrans.
liftCallCC :: (((Maybe a -> m (Maybe b)) -> m (Maybe a)) ->
    m (Maybe a)) -> ((a -> MaybeT m b) -> MaybeT m a) -> MaybeT m a
liftCallCC callCC f =
    MaybeT $ callCC $ \ c -> runMaybeT (f (MaybeT . c . JustLazy))

-- | Lift a @catchError@ operation to the new monadTrans.
liftCatch :: (m (Maybe a) -> (e -> m (Maybe a)) -> m (Maybe a)) ->
    MaybeT m a -> (e -> MaybeT m a) -> MaybeT m a
liftCatch f m h = MaybeT $ f (runMaybeT m) (runMaybeT . h)

-- | Lift a @listen@ operation to the new monadTrans.
liftListen :: Monad m =>
    (m (Maybe a) -> m (Maybe a,w)) -> MaybeT m a -> MaybeT m (a,w)
liftListen listen = mapMaybeT $ \ m -> do
    (a, w) <- listen m
    return $! fmap (\ r -> (r, w)) a

-- | Lift a @pass@ operation to the new monadTrans.
liftPass :: Monad m => (m (Maybe a,w -> w) -> m (Maybe a)) ->
    MaybeT m (a,w -> w) -> MaybeT m a
liftPass pass = mapMaybeT $ \ m -> pass $ do
    a <- m
    return $! case a of
        Nothing     -> (Nothing, id)
        JustLazy (v, f) -> (JustLazy v, f)
*/