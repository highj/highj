/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__5;
import org.highj.data.transformer.rws.*;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

/**
 *
 * @author clintonselke
 */
public interface RWST<R,W,S,M,A> extends __5<RWST.µ,R,W,S,M,A> {
    interface µ {}

    __<M,T3<A,S,W>> run(R r, S s);

    static <R,W,S,M> RWSTFunctor<R,W,S,M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    static <R,W,S,M> RWSTApply<R,W,S,M> apply(Bind<M> mBind, Semigroup<W> wSemigroup) {
        return new RWSTApply<R,W,S,M>() {
            @Override
            public Bind<M> getM() {
                return mBind;
            }
            @Override
            public Semigroup<W> getW() {
                return wSemigroup;
            }
        };
    }

    static <R,W,S,M> RWSTApplicative<R,W,S,M> applicative(Monad<M> mMonad, Monoid<W> wMonoid) {
        return new RWSTApplicative<R,W,S,M>() {
            @Override
            public Monad<M> getM() {
                return mMonad;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }
    
    static <R,W,S,M> RWSTBind<R,W,S,M> bind(Monad<M> mMonad, Semigroup<W> wSemigroup) {
        return new RWSTBind<R,W,S,M>() {
            @Override
            public Bind<M> getM() {
                return mMonad;
            }
            @Override
            public Semigroup<W> getW() {
                return wSemigroup;
            }
        };
    }

    static <R,W,S,M> RWSTMonad<R,W,S,M> monad(Monad<M> mMonad, Monoid<W> wMonoid) {
        return new RWSTMonad<R,W,S,M>() {
            @Override
            public Monad<M> getM() {
                return mMonad;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }

    static <R,W,S,M> RWSTMonadReader<R,W,S,M> monadReader(Monad<M> mMonad, Monoid<W> wMonoid) {
        return new RWSTMonadReader<R,W,S,M>() {
            @Override
            public Monad<M> getM() {
                return mMonad;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }

    static <R,W,S,M> RWSTMonadWriter<R,W,S,M> monadWriter(Monad<M> mMonad, Monoid<W> wMonoid) {
        return new RWSTMonadWriter<R,W,S,M>() {
            @Override
            public Monad<M> getM() {
                return mMonad;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }

    static <R,W,S,M> RWSTMonadState<R,W,S,M> monadState(Monad<M> mMonad, Monoid<W> wMonoid) {
        return new RWSTMonadState<R,W,S,M>() {
            @Override
            public Monad<M> getM() {
                return mMonad;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }
    
    static <R,W,S,M> RWSTMonadRWS<R,W,S,M> monadRWS(Monad<M> mMonad, Monoid<W> wMonoid) {
        return new RWSTMonadRWS<R,W,S,M>() {
            @Override
            public Monad<M> getM() {
                return mMonad;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }

    static <R,W,S,M> RWSTMonadTrans<R,W,S,M> monadTrans(Monad<M> mMonad, Monoid<W> wMonoid) {
        return new RWSTMonadTrans<R,W,S,M>() {
            @Override
            public Monad<M> getM() {
                return mMonad;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }
    
    static <R,W,S,M> RWSTMonadRec<R,W,S,M> monadRec(MonadRec<M> mMonadRec, Monoid<W> wMonoid) {
        return new RWSTMonadRec<R,W,S,M>() {
            @Override
            public MonadRec<M> getM() {
                return mMonadRec;
            }
            @Override
            public Monoid<W> getW() {
                return wMonoid;
            }
        };
    }

    static <R,W,S,M> RWSTContravariant<R,W,S,M> contravariant(Contravariant<M> mContravariant) {
         return () -> mContravariant;
    }
}
