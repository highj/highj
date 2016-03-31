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
import org.highj.typeclass1.monad.Bind;

/**
 *
 * @author clintonselke
 */
public interface ProgramBind<INSTR> extends ProgramApply<INSTR>, Bind<_<Program.µ,INSTR>> {

    @Override
    public default <A, B> Program<INSTR, B> bind(_<_<Program.µ, INSTR>, A> nestedA, Function<A, _<_<Program.µ, INSTR>, B>> fn) {
        return Program.bind(Program.narrow(nestedA), (A x) -> Program.narrow(fn.apply(x)));
    }
}
