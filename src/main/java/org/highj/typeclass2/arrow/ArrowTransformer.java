/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;

/**
 *
 * @author clintonselke
 */
public interface ArrowTransformer<F,A> extends Arrow<__<F,A>> {
    
    public <B,C> __3<F,A,B,C> lift(__2<A,B,C> arrow);
}
