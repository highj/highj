/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__2;

/**
 *
 * @author clintonselke
 */
public interface ArrowPlus<A> extends ArrowZero<A> {
    
    public <B,C> __2<A,B,C> plus(__2<A,B,C> arrow1, __2<A,B,C> arrow2);
}
