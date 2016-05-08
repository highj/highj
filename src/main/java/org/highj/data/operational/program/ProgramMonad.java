/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.operational.program;

import org.derive4j.hkt.__;
import org.highj.data.operational.Program;
import org.highj.typeclass1.monad.Monad;

/**
 *
 * @author clintonselke
 */
public interface ProgramMonad<INSTR> extends ProgramApplicative<INSTR>, ProgramBind<INSTR>, Monad<__<Program.µ,INSTR>> {}
