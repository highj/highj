/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.operational;

import org.highj._;
import org.highj.__;
import org.highj.data.functions.F1;
import org.highj.data.operational.program.ProgramApplicative;
import org.highj.data.operational.program.ProgramApply;
import org.highj.data.operational.program.ProgramBind;
import org.highj.data.operational.program.ProgramFunctor;
import org.highj.data.operational.program.ProgramMonad;

/**
 * https://hackage.haskell.org/package/operational
 * @author clintonselke
 */
public abstract class Program<INSTR,A> implements __<Program.µ,INSTR,A> {
    public static class µ {}
    
    public interface Reduction<INSTR,A,R> {
        R pure(A a);
        <B> R bind(Program<INSTR,B> mb, F1<B,Program<INSTR,A>> f);
        R instr(_<INSTR,A> instr);
    }
    
    public abstract <R> R reduce(Reduction<INSTR,A,R> reduction);
    
    public static <INSTR,A> Program<INSTR,A> narrow(__<Program.µ,INSTR,A> a) {
        return (Program<INSTR,A>)a;
    }
    
    public static <INSTR,A> Program<INSTR,A> narrow(_<__.µ<Program.µ,INSTR>,A> a) {
        return (Program<INSTR,A>)a;
    }
    
    public static <INSTR,A> Program<INSTR,A> pure(A a) {
        return new Program<INSTR,A>() {
            @Override
            public <R> R reduce(Reduction<INSTR, A, R> reduction) {
                return reduction.pure(a);
            }
        };
    }
    
    public static <INSTR,A,B> Program<INSTR,B> bind(Program<INSTR,A> ma, F1<A,Program<INSTR,B>> f) {
        return new Program<INSTR,B>() {
            @Override
            public <R> R reduce(Reduction<INSTR, B, R> reduction) {
                return reduction.bind(ma, f);
            }
        };
    }
    
    public static <INSTR,A> Program<INSTR,A> instr(_<INSTR,A> instr) {
        return new Program<INSTR,A>() {
            @Override
            public <R> R reduce(Reduction<INSTR, A, R> reduction) {
                return reduction.instr(instr);
            }
        };
    }
    
    public ProgramView<INSTR,A> view() {
        return reduce(new Reduction<INSTR,A,ProgramView<INSTR,A>>() {
            @Override
            public ProgramView<INSTR, A> pure(A a) {
                return ProgramView.pure(a);
            }
            @Override
            public <B> ProgramView<INSTR, A> bind(Program<INSTR, B> mb, F1<B, Program<INSTR, A>> f) {
                return mb.reduce(new Reduction<INSTR,B,ProgramView<INSTR,A>>() {
                    @Override
                    public ProgramView<INSTR, A> pure(B b) {
                        return f.apply(b).view();
                    }
                    @Override
                    public <C> ProgramView<INSTR, A> bind(Program<INSTR, C> mc, F1<C, Program<INSTR, B>> f2) {
                        return Program.bind(mc, (C c) -> Program.bind(f2.apply(c), f)).view();
                    }
                    @Override
                    public ProgramView<INSTR, A> instr(_<INSTR, B> instr) {
                        return ProgramView.bind(instr, f);
                    }
                });
            }
            @Override
            public ProgramView<INSTR, A> instr(_<INSTR, A> instr) {
                return ProgramView.bind(instr, (A x) -> Program.pure(x));
            }
        });
    }
    
    public static <INSTR> ProgramFunctor<INSTR> functor() {
        return new ProgramFunctor<INSTR>() {};
    }
    
    public static <INSTR> ProgramApply<INSTR> apply() {
        return new ProgramApply<INSTR>() {};
    }
    
    public static <INSTR> ProgramApplicative<INSTR> applicative() {
        return new ProgramApplicative<INSTR>() {};
    }
    
    public static <INSTR> ProgramBind<INSTR> bind() {
        return new ProgramBind<INSTR>() {};
    }
    
    public static <INSTR> ProgramMonad<INSTR> monad() {
        return new ProgramMonad<INSTR>() {};
    }
}
