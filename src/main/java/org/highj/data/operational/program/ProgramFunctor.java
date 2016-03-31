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
import org.highj.typeclass1.functor.Functor;

/**
 *
 * @author clintonselke
 */
public interface ProgramFunctor<INSTR> extends Functor<_<Program.µ,INSTR>> {

    @Override
    public default <A, B> Program<INSTR, B> map(Function<A, B> fn, _<_<Program.µ, INSTR>, A> nestedA) {
        return Program.bind(Program.narrow(nestedA), (A x) -> Program.pure(fn.apply(x)));
    }
}
