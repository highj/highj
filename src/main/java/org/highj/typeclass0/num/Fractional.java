/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass0.num;

import java.math.BigInteger;
import org.highj.data.ratio.Rational;

/**
 *
 * @author clintonselke
 */
public interface Fractional<A> extends Num<A> {
    
    public A fromRational(Rational r);
    
    public A divide(A a, A b);
    
    public default A recip(A a) {
        return divide(fromBigInteger(BigInteger.ONE), a);
    }
}
