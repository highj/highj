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
public interface ArrowCircuit<A> extends ArrowLoop<A> {
    
    public <B> __2<A,B,B> delay(B b);
}
