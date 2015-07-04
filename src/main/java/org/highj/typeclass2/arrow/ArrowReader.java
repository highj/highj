/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.tuple.T2;

/**
 *
 * @author clintonselke
 */
public interface ArrowReader<R,A> extends Arrow<A> {
    
    public <B> __<A,B,R> readState();
    
    public <B,C> __<A,T2<B,R>,C> newReader(__<A,B,C> a);
}
