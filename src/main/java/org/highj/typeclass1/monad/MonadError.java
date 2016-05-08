/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass1.monad;

import java.util.function.Function;
import org.derive4j.hkt.__;

/**
 *
 * @author clintonselke
 */
public interface MonadError<E,M> extends Monad<M> {
    
    public <A> __<M,A> throwError(E error);
    
    public <A> __<M,A> catchError(__<M,A> ma, Function<E,__<M,A>> fn);
}
