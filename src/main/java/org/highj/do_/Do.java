/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.do_;

import java.util.ArrayList;
import java.util.Arrays;
import org.derive4j.hkt.__;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.function.F3;
import org.highj.function.F4;
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
public class Do {
    
    public static class MValue<H,A> {
        private final int ident;
        private MValue(int ident) {
            this.ident = ident;
        }
    }
    
    public static class MContext<H,M> {
        private final Monad<M> monad;
        private final StateTMonadState<Object[],M> stateMonadState;
        private final StateTMonadTrans<Object[],M> stateMonadTrans;
        private final StateTMonad<Object[],M> stateMonad;
        private int nextIdent = 0;
        private final ArrayList<__<__<__<StateT.µ,Object[]>,M>,T0>> comp = new ArrayList<>();
        
        private MContext(Monad<M> monad) {
            this.monad = monad;
            stateMonadState = StateT.monadState(monad);
            stateMonadTrans = StateT.monadTrans(monad);
            stateMonad = stateMonadState;
        }
        
        public <A> void seq(__<M,A> m) {
            comp.add(
                stateMonad.rightSeq(
                    stateMonadTrans.lift(m),
                    stateMonad.pure(T0.of())
                )
            );
        }
        
        public <A> MValue<H,A> assign(__<M,A> m) {
            MValue<H,A> r = newMValue();
            comp.add(
                stateMonad.bind(
                    stateMonadTrans.lift(m),
                    (A a) -> stateMonad.rightSeq(
                        writeMValue(r, a),
                        stateMonad.pure(T0.of())
                    )
                )
            );
            return r;
        }
        
        public __<M,T0> done() {
            return StateT.narrow(done_()).eval(monad, new Object[] {});
        }
        
        public <A> __<M,A> doneRes(MValue<H,A> ref) {
            return StateT.narrow(stateMonad.rightSeq(done_(), readMValue(ref))).eval(monad, new Object[] {});
        }
        
        public <A> MValue<H,A> let0(A a) {
            MValue<H,A> r = newMValue();
            comp.add(writeMValue(r, a));
            return r;
        }
        
        public <A,B> MValue<H,B> let1(MValue<H,A> refA, F1<A,B> f) {
            MValue<H,B> r = newMValue();
            comp.add(
                stateMonad.bind(
                    stateMonad.map(
                        f,
                        readMValue(refA)
                    ),
                    (B b) -> writeMValue(r, b)
                )
            );
            return r;
        }
        
        public <A,B,C> MValue<H,C> let2(MValue<H,A> refA, MValue<H,B> refB, F2<A,B,C> f) {
            MValue<H,C> r = newMValue();
            comp.add(
                stateMonad.bind(
                    stateMonad.apply2(
                        (A a) -> (B b) -> f.apply(a, b),
                        readMValue(refA),
                        readMValue(refB)
                    ),
                    (C c) -> writeMValue(r, c)
                )
            );
            return r;
        }
        
        public <A,B,C,D> MValue<H,D> let3(MValue<H,A> refA, MValue<H,B> refB, MValue<H,C> refC, F3<A,B,C,D> f) {
            MValue<H,D> r = newMValue();
            comp.add(
                stateMonad.bind(
                    stateMonad.apply3(
                        (A a) -> (B b) -> (C c) -> f.apply(a, b, c),
                        readMValue(refA),
                        readMValue(refB),
                        readMValue(refC)
                    ),
                    (D d) -> writeMValue(r, d)
                )
            );
            return r;
        }
        
        public <A,B,C,D,E> MValue<H,E> let4(MValue<H,A> refA, MValue<H,B> refB, MValue<H,C> refC, MValue<H,D> refD, F4<A,B,C,D,E> f) {
            MValue<H,E> r = newMValue();
            comp.add(
                stateMonad.bind(
                    stateMonad.apply4(
                        (A a) -> (B b) -> (C c) -> (D d) -> f.apply(a, b, c, d),
                        readMValue(refA),
                        readMValue(refB),
                        readMValue(refC),
                        readMValue(refD)
                    ),
                    (E e) -> writeMValue(r, e)
                )
            );
            return r;
        }
        
        public <A,B> MValue<H,B> assignBind1(MValue<H,A> refA, F1<A,__<M,B>> f) {
            MValue<H,B> r = newMValue();
            comp.add(
                stateMonad.bind(
                    readMValue(refA),
                    (A a) -> stateMonad.bind(
                        stateMonadTrans.lift(f.apply(a)),
                        (B b) -> writeMValue(r, b)
                    )
                )
            );
            return r;
        }
        
        public <A,B,C> MValue<H,C> assignBind2(MValue<H,A> refA, MValue<H,B> refB, F2<A,B,__<M,C>> f) {
            MValue<H,C> r = newMValue();
            comp.add(
                stateMonad.join(stateMonad.apply2(
                    (A a) -> (B b) -> stateMonad.bind(
                        stateMonadTrans.lift(f.apply(a, b)),
                        (C c) -> writeMValue(r, c)
                    ),
                    readMValue(refA),
                    readMValue(refB)
                ))
            );
            return r;
        }
        
        public <A,B,C,D> MValue<H,D> assignBind3(MValue<H,A> refA, MValue<H,B> refB, MValue<H,C> refC, F3<A,B,C,__<M,D>> f) {
            MValue<H,D> r = newMValue();
            comp.add(
                stateMonad.join(stateMonad.apply3(
                    (A a) -> (B b) -> (C c) -> stateMonad.bind(
                        stateMonadTrans.lift(f.apply(a, b, c)),
                        (D d) -> writeMValue(r, d)
                    ),
                    readMValue(refA),
                    readMValue(refB),
                    readMValue(refC)
                ))
            );
            return r;
        }
        
        public <A,B,C,D,E> MValue<H,E> assignBind4(MValue<H,A> refA, MValue<H,B> refB, MValue<H,C> refC, MValue<H,D> refD, F4<A,B,C,D,__<M,E>> f) {
            MValue<H,E> r = newMValue();
            comp.add(
                stateMonad.join(stateMonad.apply4(
                    (A a) -> (B b) -> (C c) -> (D d) -> stateMonad.bind(
                        stateMonadTrans.lift(f.apply(a, b, c, d)),
                        (E e) -> writeMValue(r, e)
                    ),
                    readMValue(refA),
                    readMValue(refB),
                    readMValue(refC),
                    readMValue(refD)
                ))
            );
            return r;
        }
        
        private __<__<__<StateT.µ,Object[]>,M>,T0> done_() {
            __<__<__<StateT.µ,Object[]>,M>,T0> r = stateMonad.pure(T0.of());
            for (int i = comp.size()-1; i >= 0; --i) {
                r = stateMonad.rightSeq(comp.get(i), r);
            }
            return r;
        }
        
        private <A> MValue<H,A> newMValue() {
            return new MValue<>(nextIdent++);
        }
        
        private <A> __<__<__<StateT.µ,Object[]>,M>,T0> writeMValue(MValue<H,A> ref, A a) {
            return stateMonadState.modify((Object[] x) -> {
                Object[] x2 = Arrays.copyOf(x, nextIdent);
                x2[ref.ident] = a;
                return x2;
            });
        }
        
        private <A> __<__<__<StateT.µ,Object[]>,M>,A> readMValue(MValue<H,A> ref) {
            return stateMonad.map(
                (Object[] x) -> (A)x[ref.ident],
                stateMonadState.get()
            );
        }
    }
    
    public static abstract class DoBlock<M,A> {
        public abstract <H> __<M,A> run(MContext<H,M> ctx);
    }
    
    public static <M,A> __<M,A> do_(Monad<M> monad, DoBlock<M,A> doBlock) {
        MContext<?,M> ctx = new MContext<>(monad);
        return doBlock.run(ctx);
    }
}
