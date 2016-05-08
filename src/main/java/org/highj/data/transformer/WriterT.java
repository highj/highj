package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.transformer.writer.*;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface WriterT<W, M, A> extends __3<WriterT.µ, W, M, A> {
    public static class µ {
    }

    public static <W, M, A> WriterT<W, M, A> narrow(__<__<__<µ, W>, M>, A> a) {
        return (WriterT<W, M, A>) a;
    }

    public __<M, T2<A, W>> run();

    public default __<M, W> exec(Functor<M> mFunctor) {
        return mFunctor.map(T2::_2, run());
    }

    public static <W, M> WriterTFunctor<W, M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    public static <W, M> WriterTApply<W, M> apply(Semigroup<W> wSemigroup, Apply<M> mApply) {
        return new WriterTApply<W, M>() {
            @Override
            public Semigroup<W> wSemigroup() {
                return wSemigroup;
            }

            @Override
            public Apply<M> get() {
                return mApply;
            }
        };
    }

    public static <W, M> WriterTApplicative<W, M> applicative(Monoid<W> wMonoid, Applicative<M> mApplicative) {
        return new WriterTApplicative<W, M>() {
            @Override
            public Monoid<W> wMonoid() {
                return wMonoid;
            }

            @Override
            public Semigroup<W> wSemigroup() {
                return wMonoid;
            }

            @Override
            public Applicative<M> get() {
                return mApplicative;
            }
        };
    }

    public static <W, M> WriterTBind<W, M> bind(Semigroup<W> wSemigroup, Bind<M> mBind) {
        return new WriterTBind<W, M>() {
            @Override
            public Semigroup<W> wSemigroup() {
                return wSemigroup;
            }

            @Override
            public Bind<M> get() {
                return mBind;
            }
        };
    }

    public static <W, M> WriterTMonad<W, M> monad(Monoid<W> wMonoid, Monad<M> mMonad) {
        return new WriterTMonad<W, M>() {
            @Override
            public Monoid<W> wMonoid() {
                return wMonoid;
            }

            @Override
            public Semigroup<W> wSemigroup() {
                return wMonoid;
            }

            @Override
            public Monad<M> get() {
                return mMonad;
            }
        };
    }

    public static <W, M> WriterTMonadWriter<W, M> monadWriter(Monoid<W> wMonoid, Monad<M> mMonad) {
        return new WriterTMonadWriter<W, M>() {
            @Override
            public Monoid<W> wMonoid() {
                return wMonoid;
            }

            @Override
            public Semigroup<W> wSemigroup() {
                return wMonoid;
            }

            @Override
            public Monad<M> get() {
                return mMonad;
            }
        };
    }

    public static <W, M> WriterTMonadTrans<W, M> monadTrans(Monoid<W> wMonoid, Monad<M> mMonad) {
        return new WriterTMonadTrans<W, M>() {
            @Override
            public Monoid<W> wMonoid() {
                return wMonoid;
            }

            @Override
            public Semigroup<W> wSemigroup() {
                return wMonoid;
            }

            @Override
            public Monad<M> get() {
                return mMonad;
            }
        };
    }
}
