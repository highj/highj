package org.highj.typeclass0.num;

import java.math.BigInteger;

/**
 *
 * @author clintonselke
 */
public interface Num<A> {
    
    A add(A a, A b);
    
    A subtract(A a, A b);
    
    A times(A a, A b);
    
    A negate(A a);
    
    A abs(A a);
    
    A signum(A a);
    
    A fromBigInteger(BigInteger a);
}
