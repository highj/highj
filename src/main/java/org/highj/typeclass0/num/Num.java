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
public interface Num<A> {
    
    public A add(A a, A b);
    
    public A subtract(A a, A b);
    
    public A times(A a, A b);
    
    public A negate(A a);
    
    public A abs(A a);
    
    public A signum(A a);
    
    public A fromBigInteger(BigInteger a);
}
