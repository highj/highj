package org.highj.typeclass0.num;

import java.math.BigInteger;
import org.highj.data.tuple.T2;

/**
 *
 * @author clintonselke
 */
public interface Integral<A> extends Real<A>, Enum<A> {
    
    T2<A,A> quotRem(A a, A b);
    
    BigInteger toBigInteger(A a);
    
    default A quot(A a, A b) {
        return quotRem(a, b)._1();
    }
    
    default A rem(A a, A b) {
        return quotRem(a, b)._2();
    }
    
    default A div(A a, A b) {
        return divMod(a, b)._1();
    }
    
    default A mod(A a, A b) {
        return divMod(a, b)._2();
    }
    
    default T2<A,A> divMod(A a, A b) {
        T2<A,A> x = quotRem(a, b);
        if (signum(x._2()) == negate(signum(b))) {
            return T2.of(
                subtract(x._1(), fromBigInteger(BigInteger.valueOf(1))),
                add(x._2(), b)
            );
        } else {
            return x;
        }
    }
}
