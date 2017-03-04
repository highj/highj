package org.highj.data.bool;

import org.highj.typeclass0.group.*;

/**
 * Useful type classes of booleans.
 */
public interface Booleans {

    /**
     * The and {@link Monoid} of booleans.
     */
    Monoid<Boolean> andMonoid = Monoid.create(Boolean.TRUE, (x,y) -> x && y);

    /**
     * The or {@link Monoid} of booleans.
     */
    Monoid<Boolean> orMonoid = Monoid.create(Boolean.FALSE, (x, y) -> x || y);

    /**
     * The xor {@link Monoid} of booleans.
     */
    Monoid<Boolean> xorMonoid = Monoid.create(Boolean.FALSE, (x,y) -> x ^ y);
}
