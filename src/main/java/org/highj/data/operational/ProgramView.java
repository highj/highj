/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.operational;

import org.highj._;
import org.highj.data.functions.F1;

/**
 *
 * @author clintonselke
 */
public abstract class ProgramView<INSTR,A> {
    public interface Reduction<INSTR,A,R> {
        R pure(A a);
        <B> R bind(_<INSTR,B> mb, F1<B,Program<INSTR,A>> f);
    }
    
    public abstract <R> R reduce(Reduction<INSTR,A,R> reduction);
    
    public static <INSTR,A> ProgramView<INSTR,A> pure(A a) {
        return new ProgramView<INSTR,A>() {
            @Override
            public <R> R reduce(Reduction<INSTR, A, R> reduction) {
                return reduction.pure(a);
            }
        };
    }
    
    public static <INSTR,A,B> ProgramView<INSTR,B> bind(_<INSTR,A> ma, F1<A,Program<INSTR,B>> f) {
        return new ProgramView<INSTR,B>() {
            @Override
            public <R> R reduce(Reduction<INSTR, B, R> reduction) {
                return reduction.bind(ma, f);
            }
        };
    }
}
