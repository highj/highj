package org.highj.data.bool;

import org.highj.typeclass0.group.*;

/**
 * Useful type classes of booleans.
 */
public interface Booleans {

    /**
     * The and {@link Group} of booleans.
     */
    Group<Boolean> andGroup = Group.create(Boolean.TRUE, (x,y) -> x && y, z -> !z);

    /**
     * The or {@link Group} of booleans.
     */
    Group<Boolean> orGroup = Group.create(Boolean.FALSE, (x,y) -> x || y, z -> !z);

    /**
     * The xor {@link Monoid} of booleans.
     */
    Monoid<Boolean> xorMonoid = Monoid.create(Boolean.FALSE, (x,y) -> x ^ y);
}
