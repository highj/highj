/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass0.num;

import org.highj.data.ratio.Rational;
import org.highj.data.ord.Ord;

/**
 *
 * @author clintonselke
 */
public interface Real<A> extends Num<A>, Ord<A> {
    
    public Rational toRational(A a);
}
