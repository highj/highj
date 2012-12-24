package org.highj.data.transformer;

import org.highj._;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.foldable.Foldable;
import org.highj.typeclass.foldable.Traversable;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.monad.*;

public class IdentityT<M, A> extends _<_<IdentityT.µ, M>, A> {

    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    public static class µµ<M> extends _<µ, M> {
        private µµ() {
            super(hidden);
        }
    }

    private final _<M, A> value;

    public IdentityT(_<M, A> value) {
        super(new µµ<M>());
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <M, A> IdentityT<M, A> narrow(_<_<µ, M>, A> value) {
        return (IdentityT) value;
    }

    public static <M, A> F1<_<M, A>, _<_<µ, M>, A>> wrap() {
        return new F1<_<M, A>, _<_<µ, M>, A>>() {
            @Override
            public _<_<IdentityT.µ, M>, A> $(_<M, A> ma) {
                return new IdentityT<M,A>(ma);
            }
        };
    }

    public static <M, A> F1<_<_<µ, M>, A>, _<M, A>> unwrap() {
        return new F1<_<_<µ, M>, A>, _<M, A>>() {
            @Override
            public _<M, A> $(_<_<IdentityT.µ, M>, A> a) {
                return IdentityT.<M, A>narrow(a).get();
            }
        };
    }

    public _<M, A> get() {
        return value;
    }

    public static <M, A, N, B> F1<IdentityT<M, A>, IdentityT<N, B>> mapIdentityT(final F1<_<M, A>, _<N, B>> fn) {
        return new F1<IdentityT<M, A>, IdentityT<N, B>>() {

            @Override
            public IdentityT<N, B> $(IdentityT<M, A> ma) {
                return new IdentityT<N, B>(fn.$(ma.get()));
            }
        };
    }

    public static <M, A, N, B, P, C> F2<IdentityT<M, A>, IdentityT<N, B>, IdentityT<P, C>> lift2IdentityT(
            final F2<_<M, A>, _<N, B>, _<P, C>> fn
    ) {
        return new F2<IdentityT<M, A>, IdentityT<N, B>, IdentityT<P, C>>() {

            @Override
            public IdentityT<P, C> $(IdentityT<M, A> nestedA, IdentityT<N, B> nestedB) {
                IdentityT<M, A> aId = IdentityT.narrow(nestedA);
                IdentityT<N, B> bId = IdentityT.narrow(nestedB);
                return new IdentityT<P, C>(fn.$(aId.get(), bId.get()));
            }
        };
    }

    public static <M> Functor<_<µ, M>> functor(final Functor<M> functorM) {
        return new Functor<_<µ, M>>() {
            @Override
            public <A, B> _<_<µ, M>, B> map(F1<A, B> fn, _<_<µ, M>, A> nestedA) {
                IdentityT<M, A> aId = narrow(nestedA);
                return new IdentityT<M, B>(functorM.map(fn, aId.get()));
            }
        };
    }

    public static <M> Applicative<_<µ, M>> applicative(final Applicative<M> applicativeM) {
        return new Applicative<_<µ, M>>() {
            @Override
            public <A, B> _<_<µ, M>, B> map(F1<A, B> fn, _<_<µ, M>, A> nestedA) {
                IdentityT<M, A> aId = narrow(nestedA);
                return new IdentityT<M, B>(applicativeM.map(fn, aId.get()));
            }

            @Override
            public <A> _<_<µ, M>, A> pure(A a) {
                return new IdentityT<M, A>(applicativeM.pure(a));
            }

            @Override
            public <A, B> _<_<µ, M>, B> ap(_<_<µ, M>, F1<A, B>> fn, _<_<µ, M>, A> nestedA) {
                IdentityT<M, A> aId = narrow(nestedA);
                IdentityT<M, F1<A, B>> fnId = narrow(fn);
                return new IdentityT<M, B>(applicativeM.ap(fnId.get(), aId.get()));
            }
        };
    }


    public static <M> Monad<_<µ, M>> monad(final Monad<M> monadM) {
        return new Monad<_<µ, M>>() {
            @Override
            public <A, B> _<_<µ, M>, B> map(F1<A, B> fn, _<_<µ, M>, A> nestedA) {
                IdentityT<M, A> aId = narrow(nestedA);
                return new IdentityT<M, B>(monadM.map(fn, aId.get()));
            }

            @Override
            public <A> _<_<µ, M>, A> pure(A a) {
                return new IdentityT<M, A>(monadM.pure(a));
            }

            @Override
            public <A, B> _<_<µ, M>, B> ap(_<_<µ, M>, F1<A, B>> fn, _<_<µ, M>, A> nestedA) {
                IdentityT<M, A> aId = narrow(nestedA);
                IdentityT<M, F1<A, B>> fnId = narrow(fn);
                return new IdentityT<M, B>(monadM.ap(fnId.get(), aId.get()));
            }

            @Override
            public <A, B> _<_<µ, M>, B> bind(_<_<µ, M>, A> nestedA, F1<A, _<_<µ, M>, B>> fn) {
                IdentityT<M, A> aId = narrow(nestedA);
                return new IdentityT<M, B>(monadM.bind(aId.get(), fn.andThen(IdentityT.<M, B>unwrap())));
            }
        };
    }

    public static <M> MonadPlus<_<µ, M>> monadPlus(final MonadPlus<M> monadPlusM) {
        return new IdentityTMonadPlus<M>(monadPlusM);
    }

    private static final class IdentityTMonadPlus<M> implements MonadPlus<_<µ, M>> {

        private final MonadPlus<M> monadPlusM;

        public IdentityTMonadPlus(MonadPlus<M> monadPlusM) {
            this.monadPlusM = monadPlusM;
        }

        @Override
        public <A, B> _<_<µ, M>, B> map(F1<A, B> fn, _<_<µ, M>, A> nestedA) {
            IdentityT<M, A> aId = narrow(nestedA);
            return new IdentityT<M, B>(monadPlusM.map(fn, aId.get()));
        }

        @Override
        public <A> _<_<µ, M>, A> pure(A a) {
            return new IdentityT<M, A>(monadPlusM.pure(a));
        }

        @Override
        public <A, B> _<_<µ, M>, B> ap(_<_<µ, M>, F1<A, B>> fn, _<_<µ, M>, A> nestedA) {
            IdentityT<M, A> aId = narrow(nestedA);
            IdentityT<M, F1<A, B>> fnId = narrow(fn);
            return new IdentityT<M, B>(monadPlusM.ap(fnId.get(), aId.get()));
        }

        @Override
        public <A, B> _<_<µ, M>, B> bind(_<_<µ, M>, A> nestedA, F1<A, _<_<µ, M>, B>> fn) {
            IdentityT<M, A> aId = narrow(nestedA);
            return new IdentityT<M, B>(monadPlusM.bind(aId.get(), fn.andThen(IdentityT.<M, B>unwrap())));
        }

        @Override
        public <A> _<_<µ, M>, A> mplus(_<_<µ, M>, A> one, _<_<µ, M>, A> two) {
            IdentityT<M, A> oneId = narrow(one);
            IdentityT<M, A> twoId = narrow(two);
            return new IdentityT<M,A>(monadPlusM.mplus(oneId.get(), twoId.get()));
        }

        @Override
        public <A> _<_<µ, M>, A> mzero() {
            return new IdentityT<M,A>(monadPlusM.<A>mzero());
        }
    }

    public static <M> Foldable<_<µ,M>> foldable(final Foldable<M> foldableM) {
        return new Foldable<_<µ, M>>() {
            @Override
            public <A, B> B foldMap(Monoid<B> mb, F1<A, B> fn, _<_<µ, M>, A> nestedA) {
                IdentityT<M, A> aId = narrow(nestedA);
                return foldableM.foldMap(mb, fn, aId.get());
            }
        };
    }

    public static <M> Traversable<_<µ,M>> traversable(final Traversable<M> traversableM) {
        return new Traversable<_<µ, M>>() {
            @Override
            public <A, B> _<_<µ, M>, B> map(F1<A, B> fn, _<_<µ, M>, A> nestedA) {
                IdentityT<M, A> aId = narrow(nestedA);
                return new IdentityT<M,B>(traversableM.map(fn, aId.get()));
            }

            @Override
            public <A, X> _<X, _<_<µ, M>, A>> sequenceA(Applicative<X> applicative, _<_<µ, M>, _<X, A>> traversable) {
                IdentityT<M, _<X, A>> traversableId = narrow(traversable);
                _<X, _<M, A>> result = traversableM.sequenceA(applicative, traversableId.get());
                return applicative.map(IdentityT.<M,A>wrap(), result);
            }
        };
    }


}


/*

instance (MonadFix m) => MonadFix (IdentityT m) where
    mfix f = IdentityT (mfix (runIdentityT . f))

instance (MonadIO m) => MonadIO (IdentityT m) where
    liftIO = IdentityT . liftIO

instance MonadTrans IdentityT where
    lift = IdentityT


-- | Lift a @callCC@ operation to the new monad.
liftCallCC :: (((a -> m b) -> m a) ->
    m a) -> ((a -> IdentityT m b) -> IdentityT m a) -> IdentityT m a
liftCallCC callCC f =
    IdentityT $ callCC $ \ c -> runIdentityT (f (IdentityT . c))

-- | Lift a @catchError@ operation to the new monad.
liftCatch :: (m a -> (e -> m a) -> m a) ->
    IdentityT m a -> (e -> IdentityT m a) -> IdentityT m a
liftCatch f m h = IdentityT $ f (runIdentityT m) (runIdentityT . h)
*/