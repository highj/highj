/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.operational.program;

import org.highj._;
import org.highj.__;
import org.highj.data.operational.Program;
import org.highj.typeclass1.monad.Applicative;

/**
 *
 * @author clintonselke
 */
public interface ProgramApplicative<INSTR> extends ProgramApply<INSTR>, Applicative<_<Program.µ,INSTR>> {

    @Override
    public default <A> Program<INSTR, A> pure(A a) {
        return Program.pure(a);
    }
}
