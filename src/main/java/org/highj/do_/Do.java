/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.do_;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.functions.F1;
import org.highj.data.functions.F2;
import org.highj.data.functions.F3;
import org.highj.data.transformer.StateT;
import org.highj.data.transformer.state.StateTMonad;
import org.highj.data.transformer.state.StateTMonadState;
import org.highj.data.transformer.state.StateTMonadTrans;
import org.highj.data.tuple.T0;
import org.highj.typeclass1.monad.Monad;

/**
 *
 * @author clintonselke
 */
public class Do<M,R,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> {
    private final Monad<M> monad;
    private final StateT<DoState,M,R> mr;
    private final StateTMonadTrans<DoState,M> stateMonadTrans;
    private final StateTMonadState<DoState,M> stateMonadState;
    private final StateTMonad<DoState,M> stateMonad;
    
    private Do(Monad<M> monad, StateT<DoState,M,R> mr) {
        this(monad, mr, StateT.monadTrans(monad), StateT.monadState(monad));
    }
    
    private Do(Monad<M> monad, StateT<DoState,M,R> mr, StateTMonadTrans<DoState,M> stateMonadTrans, StateTMonadState<DoState,M> stateMonadState) {
        this.monad = monad;
        this.mr = mr;
        this.stateMonadTrans = stateMonadTrans;
        this.stateMonadState = stateMonadState;
        this.stateMonad = this.stateMonadTrans;
    }
    
    private <R2,A2,B2,C2,D2,E2,F2,G2,H2,I2,J2,K2,L2,M2,N2,O2,P2,Q2,R2_,S2,T2,U2,V2,W2,X2,Y2,Z2> Do<M,R2,A2,B2,C2,D2,E2,F2,G2,H2,I2,J2,K2,L2,M2,N2,O2,P2,Q2,R2_,S2,T2,U2,V2,W2,X2,Y2,Z2> changeMr(_<__.µ<___.µ<StateT.µ,DoState>,M>,R2> mr2) {
        return new Do<>(monad, StateT.narrow(mr2), stateMonadTrans, stateMonadState);
    }
    
    public static <M> Do<M,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0,T0> with(Monad<M> monad) {
        return new Do<>(monad, StateT.<DoState,M>monad(monad).pure(T0.of()));
    }
    
    public <R2> Do<M,R2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> seq(_<M,R2> m) {
        return changeMr(stateMonad.rightSeq(mr, stateMonadTrans.lift(m)));
    }
    
    private <X,A2,B2,C2,D2,E2,F2,G2,H2,I2,J2,K2,L2,M2,N2,O2,P2,Q2,R2,S2,T2,U2,V2,W2,X2,Y2,Z2> Do<M,X,A2,B2,C2,D2,E2,F2,G2,H2,I2,J2,K2,L2,M2,N2,O2,P2,Q2,R2,S2,T2,U2,V2,W2,X2,Y2,Z2> assign(char var, _<M,X> m) {
        return changeMr(stateMonad.rightSeq(mr,
            stateMonad.bind(
                stateMonadTrans.lift(m),
                (X x) -> stateMonad.bind(
                    stateMonadState.getState(),
                    (DoState s) -> stateMonad.rightSeq(
                        stateMonadState.putState(s.set(var, x)),
                        stateMonad.pure(x)
                    )
                )
            )
        ));
    }
    
    public <X2> Do<M,X2,X2,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.A var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,X2,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.B var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,X2,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.C var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,X2,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.D var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,X2,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.E var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,X2,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.F var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,X2,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.G var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,X2,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.H var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,X2,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.I var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,X2,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.J var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,X2,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.K var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,X2,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.L var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,X2,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.M var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,X2,O,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.N var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,X2,P,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.O var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,X2,Q,R_,S,T,U,V,W,X,Y,Z> assign(Var.P var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,X2,R_,S,T,U,V,W,X,Y,Z> assign(Var.Q var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,X2,S,T,U,V,W,X,Y,Z> assign(Var.R var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,X2,T,U,V,W,X,Y,Z> assign(Var.S var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,X2,U,V,W,X,Y,Z> assign(Var.T var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,X2,V,W,X,Y,Z> assign(Var.U var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,X2,W,X,Y,Z> assign(Var.V var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,X2,X,Y,Z> assign(Var.W var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X2,Y,Z> assign(Var.X var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,X2,Z> assign(Var.Y var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    public <X2> Do<M,X2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,X2> assign(Var.Z var, _<M,X2> m) {
        return assign(var.letter(), m);
    }
    
    private <X3,A2,B2,C2,D2,E2,F2,G2,H2,I2,J2,K2,L2,M2,N2,O2,P2,Q2,R2,S2,T2,U2,V2,W2,X2,Y2,Z2> Do<M,X3,A2,B2,C2,D2,E2,F2,G2,H2,I2,J2,K2,L2,M2,N2,O2,P2,Q2,R2,S2,T2,U2,V2,W2,X2,Y2,Z2> returnVar(char var) {
        return changeMr(stateMonad.rightSeq(mr,
            stateMonad.map(
                (DoState state) -> (X3)state.get(var),
                stateMonadState.getState()
            )
        ));
    }
    
    public Do<M,A,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.A var) {
        return returnVar(var.letter());
    }
    
    public Do<M,B,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.B var) {
        return returnVar(var.letter());
    }
    
    public Do<M,C,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.C var) {
        return returnVar(var.letter());
    }
    
    public Do<M,D,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.D var) {
        return returnVar(var.letter());
    }
    
    public Do<M,E,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.E var) {
        return returnVar(var.letter());
    }
    
    public Do<M,F,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.F var) {
        return returnVar(var.letter());
    }
    
    public Do<M,G,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.G var) {
        return returnVar(var.letter());
    }
    
    public Do<M,H,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.H var) {
        return returnVar(var.letter());
    }
    
    public Do<M,I,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.I var) {
        return returnVar(var.letter());
    }
    
    public Do<M,J,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.J var) {
        return returnVar(var.letter());
    }
    
    public Do<M,K,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.K var) {
        return returnVar(var.letter());
    }
    
    public Do<M,L,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.L var) {
        return returnVar(var.letter());
    }
    
    public Do<M,M_,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.M var) {
        return returnVar(var.letter());
    }
    
    public Do<M,N,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.N var) {
        return returnVar(var.letter());
    }
    
    public Do<M,O,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.O var) {
        return returnVar(var.letter());
    }
    
    public Do<M,P,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.P var) {
        return returnVar(var.letter());
    }
    
    public Do<M,Q,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.Q var) {
        return returnVar(var.letter());
    }
    
    public Do<M,R_,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.R var) {
        return returnVar(var.letter());
    }
    
    public Do<M,S,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.S var) {
        return returnVar(var.letter());
    }
    
    public Do<M,T,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.T var) {
        return returnVar(var.letter());
    }
    
    public Do<M,U,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.U var) {
        return returnVar(var.letter());
    }
    
    public Do<M,V,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.V var) {
        return returnVar(var.letter());
    }
    
    public Do<M,W,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.W var) {
        return returnVar(var.letter());
    }
    
    public Do<M,X,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.X var) {
        return returnVar(var.letter());
    }
    
    public Do<M,Y,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.Y var) {
        return returnVar(var.letter());
    }
    
    public Do<M,Z,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> return_(Var.Z var) {
        return returnVar(var.letter());
    }
    
    public With1<A> with(Var.A var) {
        return new With1<>(var.letter());
    }
    
    public With1<B> with(Var.B var) {
        return new With1<>(var.letter());
    }
    
    public With1<C> with(Var.C var) {
        return new With1<>(var.letter());
    }
    
    public With1<D> with(Var.D var) {
        return new With1<>(var.letter());
    }
    
    public With1<E> with(Var.E var) {
        return new With1<>(var.letter());
    }
    
    public With1<F> with(Var.F var) {
        return new With1<>(var.letter());
    }
    
    public With1<G> with(Var.G var) {
        return new With1<>(var.letter());
    }
    
    public With1<H> with(Var.H var) {
        return new With1<>(var.letter());
    }
    
    public With1<I> with(Var.I var) {
        return new With1<>(var.letter());
    }
    
    public With1<J> with(Var.J var) {
        return new With1<>(var.letter());
    }
    
    public With1<K> with(Var.K var) {
        return new With1<>(var.letter());
    }
    
    public With1<L> with(Var.L var) {
        return new With1<>(var.letter());
    }
    
    public With1<M_> with(Var.M var) {
        return new With1<>(var.letter());
    }
    
    public With1<N> with(Var.N var) {
        return new With1<>(var.letter());
    }
    
    public With1<O> with(Var.O var) {
        return new With1<>(var.letter());
    }
    
    public With1<P> with(Var.P var) {
        return new With1<>(var.letter());
    }
    
    public With1<Q> with(Var.Q var) {
        return new With1<>(var.letter());
    }
    
    public With1<R_> with(Var.R var) {
        return new With1<>(var.letter());
    }
    
    public With1<S> with(Var.S var) {
        return new With1<>(var.letter());
    }
    
    public With1<T> with(Var.T var) {
        return new With1<>(var.letter());
    }
    
    public With1<U> with(Var.U var) {
        return new With1<>(var.letter());
    }
    
    public With1<V> with(Var.V var) {
        return new With1<>(var.letter());
    }
    
    public With1<W> with(Var.W var) {
        return new With1<>(var.letter());
    }
    
    public With1<X> with(Var.X var) {
        return new With1<>(var.letter());
    }
    
    public With1<Y> with(Var.Y var) {
        return new With1<>(var.letter());
    }
    
    public With1<Z> with(Var.Z var) {
        return new With1<>(var.letter());
    }
    
    public _<M,R> done() {
        return mr.eval(monad, DoState.repeat(T0.of(), 8));
    }
    
    public class With1<X1> {
        private final char var1;
        private With1(char var1) {
            this.var1 = var1;
        }
        public <Y2> Do<M,Y2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> apply(F1<X1,Y2> fn) {
            return changeMr(stateMonad.rightSeq(
                mr,
                stateMonad.map(
                    (DoState state) -> fn.apply((X1)state.get(var1)),
                    stateMonadState.getState()
                )
            ));
        }
        public With2<X1,A> and(Var.A var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,B> and(Var.B var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,C> and(Var.C var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,D> and(Var.D var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,E> and(Var.E var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,F> and(Var.F var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,G> and(Var.G var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,H> and(Var.H var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,I> and(Var.I var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,J> and(Var.J var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,K> and(Var.K var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,L> and(Var.L var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,M_> and(Var.M var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,N> and(Var.N var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,O> and(Var.O var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,P> and(Var.P var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,Q> and(Var.Q var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,R_> and(Var.R var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,S> and(Var.S var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,T> and(Var.T var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,U> and(Var.U var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,V> and(Var.V var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,W> and(Var.W var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,X> and(Var.X var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,Y> and(Var.Y var) {
            return new With2<>(var1, var.letter());
        }
        public With2<X1,Z> and(Var.Z var) {
            return new With2<>(var1, var.letter());
        }
    }
    
    public class With2<X1,X2> {
        private final char var1;
        private final char var2;
        private With2(char var1, char var2) {
            this.var1 = var1;
            this.var2 = var2;
        }
        public <Y2> Do<M,Y2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> apply(F2<X1,X2,Y2> fn) {
            return changeMr(stateMonad.rightSeq(
                mr,
                stateMonad.map(
                    (DoState state) -> fn.apply((X1)state.get(var1), (X2)state.get(var2)),
                    stateMonadState.getState()
                )
            ));
        }
        public <Y2> Do<M,Y2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> apply(F1<X1,F1<X2,Y2>> fn) {
            return apply((X1 x1, X2 x2) -> fn.apply(x1).apply(x2));
        }
        public With3<X1,X2,A> and(Var.A var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,B> and(Var.B var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,C> and(Var.C var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,D> and(Var.D var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,E> and(Var.E var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,F> and(Var.F var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,G> and(Var.G var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,H> and(Var.H var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,I> and(Var.I var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,J> and(Var.J var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,K> and(Var.K var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,L> and(Var.L var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,M_> and(Var.M var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,N> and(Var.N var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,O> and(Var.O var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,P> and(Var.P var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,Q> and(Var.Q var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,R_> and(Var.R var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,S> and(Var.S var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,T> and(Var.T var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,U> and(Var.U var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,V> and(Var.V var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,W> and(Var.W var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,X> and(Var.X var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,Y> and(Var.Y var) {
            return new With3<>(var1, var2, var.letter());
        }
        public With3<X1,X2,Z> and(Var.Z var) {
            return new With3<>(var1, var2, var.letter());
        }
    }
    
    public class With3<X1,X2,X3> {
        private final char var1;
        private final char var2;
        private final char var3;
        private With3(char var1, char var2, char var3) {
            this.var1 = var1;
            this.var2 = var2;
            this.var3 = var3;
        }
        public <Y2> Do<M,Y2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> apply(F3<X1,X2,X3,Y2> fn) {
            return changeMr(stateMonad.rightSeq(
                mr,
                stateMonad.map(
                    (DoState state) -> fn.apply((X1)state.get(var1), (X2)state.get(var2), (X3)state.get(var3)),
                    stateMonadState.getState()
                )
            ));
        }
        public <Y2> Do<M,Y2,A,B,C,D,E,F,G,H,I,J,K,L,M_,N,O,P,Q,R_,S,T,U,V,W,X,Y,Z> apply(F1<X1,F1<X2,F1<X3,Y2>>> fn) {
            return apply((X1 x1, X2 x2, X3 x3) -> fn.apply(x1).apply(x2).apply(x3));
        }
    }
    
    private static class DoState {
        public final Object[] x;
        
        public DoState(Object[] x) {
            this.x = x;
        }
        
        public static DoState repeat(Object a, int n) {
            Object[] x = new Object[n];
            for (int i = 0; i < n; ++i) {
                x[i] = a;
            }
            return new DoState(x);
        }
        
        public Object get(char var) {
            return x[var - 'a'];
        }
        
        public DoState set(char var, Object a) {
            Object[] x2 = x.clone();
            x2[var - 'a'] = a;
            return new DoState(x2);
        }
    }
}
