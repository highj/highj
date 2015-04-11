/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass0.num;

import java.math.BigInteger;

/**
 *
 * @author clintonselke
 */
public interface Floating<A> extends Fractional<A> {
    
    public A pi();
    
    public A exp(A a);
    
    public A log(A a);
    
    public default A sqrt(A a) {
        return pow(a, divide(fromBigInteger(BigInteger.ONE), fromBigInteger(BigInteger.valueOf(2))));
    }
    
    public default A pow(A a, A b) {
        return exp(times(log(a), b));
    }
    
    public default A logBase(A a, A b) {
        return divide(log(b), log(a));
    }
    
    public A sin(A a);
    
    public A cos(A a);
    
    public default A tan(A a) {
        return divide(sin(a), cos(a));
    }
    
    public A asin(A a);
    
    public A acos(A a);
    
    public A atan(A a);
    
    public A sinh(A a);
    
    public A cosh(A a);
    
    public default A tanh(A a) {
        return divide(sinh(a), cosh(a));
    }
    
    public A asinh(A a);
    
    public A acosh(A a);
    
    public A atanh(A a);
}
