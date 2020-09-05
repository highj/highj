package org.highj.typeclass0.num;

import java.math.BigInteger;
import org.highj.data.ratio.Rational;

/**
 *
 * @author clintonselke
 */
public interface Fractional<A> extends Num<A> {
    
    A fromRational(Rational r);
    
    A divide(A a, A b);
    
    default A recip(A a) {
        return divide(fromBigInteger(BigInteger.ONE), a);
    }
}
