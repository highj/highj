/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;

/**
 *
 * @author clintonselke
 */
public interface ArrowWriter<W,A> extends Arrow<A> {
    
    public __<A,W,T0> write();
    
    public <B,C> __<A,B,T2<C,W>> newWriter(__<A,B,C> a);
}
