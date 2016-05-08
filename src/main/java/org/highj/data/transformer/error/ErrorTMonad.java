/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ErrorT;
import org.highj.typeclass1.monad.Monad;

/**
 *
 * @author clintonselke
 */
public interface ErrorTMonad<E,M> extends ErrorTApplicative<E,M>, ErrorTBind<E,M>, Monad<__<__<ErrorT.µ,E>,M>> {
}
