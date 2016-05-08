/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import org.derive4j.hkt.__;
import org.highj.data.transformer.RWST;
import org.highj.typeclass1.monad.Monad;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonad<R,W,S,M> extends RWSTApplicative<R,W,S,M>, RWSTBind<R,W,S,M>, Monad<__<__<__<__<RWST.µ,R>,W>,S>,M>> {
}
