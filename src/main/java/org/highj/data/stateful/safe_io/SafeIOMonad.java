/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.safe_io;

import org.highj.data.stateful.SafeIO;
import org.highj.typeclass1.monad.Monad;

/**
 *
 * @author clintonselke
 */
public interface SafeIOMonad extends SafeIOApplicative, SafeIOBind, Monad<SafeIO.µ> {
}
