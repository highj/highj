package org.highj.data.transformer;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Functor;

import java.util.function.Function;

public class MaybeT <M, A> implements _<_<MaybeT.µ, M>, A> {

    public static class µ {}

    private final _<M, _<Maybe.µ,A>> value;

    public MaybeT(_<M, _<Maybe.µ,A>> value) {
        this.value = value;
    }

    public _<M, _<Maybe.µ,A>> get() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public static <M, A> MaybeT<M, A> narrow(_<_<µ, M>, A> value) {
        return (MaybeT) value;
    }

    public static <M> Functor<_<µ, M>> functor(final Functor<M> functorM) {
        return new Functor<_<µ, M>>() {
            @Override
            public <A, B> _<_<µ, M>, B> map(Function<A, B> fn, _<_<µ, M>, A> nestedA) {
                MaybeT<M, A> aId = narrow(nestedA);
                Function<_<Maybe.µ,A>, _<Maybe.µ,B>> liftedFn = Maybe.monad.lift(fn);
                return new MaybeT<>(functorM.map(liftedFn, aId.get()));
            }
        };
    }

    public static <M> Applicative<_<µ, M>> applicative(final Applicative<M> applicativeM) {
        return new Applicative<_<µ, M>>() {
            @Override
            public <A, B> _<_<µ, M>, B> map(Function<A, B> fn, _<_<µ, M>, A> nestedA) {
                MaybeT<M, A> aMaybeT = narrow(nestedA);
                Function<_<Maybe.µ,A>, _<Maybe.µ,B>> liftedFn = Maybe.monad.lift(fn);
                return new MaybeT<>(applicativeM.map(liftedFn, aMaybeT.get()));
            }

            @Override
            public <A> _<_<µ, M>, A> pure(A a) {
                return new MaybeT<>(applicativeM.pure(Maybe.monad.pure(a)));
            }

            //this looks too complicated...
            @Override
            public <A, B> _<_<µ, M>, B> ap(_<_<µ, M>, Function<A, B>> fn, _<_<µ, M>, A> nestedA) {
                final MaybeT<M, A> aId = narrow(nestedA);
                MaybeT<M, Function<A, B>> fnMaybeT = narrow(fn);
                _<M, _<Maybe.µ, Function<A, B>>> fnMaybe = fnMaybeT.get();
                _<M, Function<_<Maybe.µ,A>,_<Maybe.µ,B>>> fnConv = applicativeM.map(new Function<_<Maybe.µ, Function<A, B>>, Function<_<Maybe.µ, A>, _<Maybe.µ, B>>>() {
                    @Override
                    public Function<_<Maybe.µ, A>, _<Maybe.µ, B>> apply(_<Maybe.µ, Function<A, B>> maybeFnNested) {
                        final Maybe<Function<A,B>> maybeFn = Maybe.narrow(maybeFnNested);
                        return maybeANested -> {
                            Maybe<A> maybeA = Maybe.narrow(maybeANested);
                            return maybeFn.isJust() && maybeA.isJust()
                                ? Maybe.Just(maybeFn.get().apply(maybeA.get()))
                                : Maybe.<B>Nothing();
                        };
                    }
                }, fnMaybe);
                _<M, _<Maybe.µ, B>> apResult = applicativeM.ap(fnConv, aId.get());
                return new MaybeT<>(apResult);
            }
        };
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