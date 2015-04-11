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
public interface ArrowLoop<A> extends Arrow<A> {
    
    public <B,C,D> __<A,B,C> loop(__<A,T2<B,D>,T2<C,D>> arrow);
}
