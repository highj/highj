package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__2;

public interface Semigroupoid<A> {

    // apply (Data.Semigroupoid), (.) (Control.Category)
    <B, C, D> __2<A, B, D> dot(__2<A, C, D> cd, __2<A, B, C> bc);

    // (>>>) (Control.Category, Control.Arrow)
    default <B, C, D> __2<A, B, D> then(__2<A, B, C> bc, __2<A, C, D> cd) {
        return dot(cd, bc);
    }

    // 2x (>>>)
    default <B, C, D, E> __2<A, B, E> then(__2<A, B, C> bc, __2<A, C, D> cd, __2<A, D, E> de) {
        return then(bc, then(cd, de));
    }

    // 3x (>>>)
    default <B, C, D, E, F> __2<A, B, F> then(__2<A, B, C> bc, __2<A, C, D> cd, __2<A, D, E> de, __2<A, E, F> ef) {
        return then(bc, cd, then(de, ef));
    }
}