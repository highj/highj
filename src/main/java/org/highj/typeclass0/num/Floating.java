package org.highj.typeclass0.num;

import java.math.BigInteger;

/**
 *
 * @author clintonselke
 */
public interface Floating<A> extends Fractional<A> {
    
    A pi();
    
    A exp(A a);
    
    A log(A a);
    
    default A sqrt(A a) {
        return pow(a, divide(fromBigInteger(BigInteger.ONE), fromBigInteger(BigInteger.valueOf(2))));
    }
    
    default A pow(A a, A b) {
        return exp(times(log(a), b));
    }
    
    default A logBase(A a, A b) {
        return divide(log(b), log(a));
    }
    
    A sin(A a);
    
    A cos(A a);
    
    default A tan(A a) {
        return divide(sin(a), cos(a));
    }
    
    A asin(A a);
    
    A acos(A a);
    
    A atan(A a);
    
    A sinh(A a);
    
    A cosh(A a);
    
    default A tanh(A a) {
        return divide(sinh(a), cosh(a));
    }
    
    A asinh(A a);
    
    A acosh(A a);
    
    A atanh(A a);
}
