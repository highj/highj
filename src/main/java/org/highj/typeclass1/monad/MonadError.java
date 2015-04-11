/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass1.monad;

import java.util.function.Function;
import org.highj._;

/**
 *
 * @author clintonselke
 */
public interface MonadError<E,M> extends Monad<M> {
    
    public <A> _<M,A> throwError(E error);
    
    public <A> _<M,A> catchError(_<M,A> ma, Function<E,_<M,A>> fn);
}
