/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.tuple.T0;

/**
 *
 * @author clintonselke
 */
public interface ArrowState<S,A> extends Arrow<A> {
    
    public <B> __<A,B,S> fetch();
    
    public __<A,S,T0> store();
}
