/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__2;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;

/**
 *
 * @author clintonselke
 */
public interface ArrowWriter<W,A> extends Arrow<A> {
    
    public __2<A,W,T0> write();
    
    public <B,C> __2<A,B,T2<C,W>> newWriter(__2<A,B,C> a);
}
