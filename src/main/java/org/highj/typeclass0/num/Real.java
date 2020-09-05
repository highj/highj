package org.highj.typeclass0.num;

import org.highj.data.ratio.Rational;
import org.highj.data.ord.Ord;

/**
 *
 * @author clintonselke
 */
public interface Real<A> extends Num<A>, Ord<A> {
    
    Rational toRational(A a);
}
