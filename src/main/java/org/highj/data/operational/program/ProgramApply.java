/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.operational.program;

import java.util.function.Function;
import org.highj._;
import org.highj.__;
import org.highj.data.operational.Program;
import org.highj.typeclass1.monad.Apply;

/**
 *
 * @author clintonselke
 */
public interface ProgramApply<INSTR> extends ProgramFunctor<INSTR>, Apply<_<Program.µ,INSTR>> {

    @Override
    public default <A, B> Program<INSTR, B> ap(_<_<Program.µ, INSTR>, Function<A, B>> fn, _<_<Program.µ, INSTR>, A> nestedA) {
        return Program.bind(
            Program.narrow(fn),
            (Function<A,B> fn2) -> Program.bind(
                Program.narrow(nestedA),
                (A a) -> Program.pure(fn2.apply(a))
            )
        );
    }
}
