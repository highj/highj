package org.highj.typeclass2.arrow;

import org.highj.__;

public interface Semigroupoid<A> {

    // dot (Data.Semigroupoid), (.) (Control.Category)
    <B, C, D> __<A, B, D> dot(__<A, C, D> cd, __<A, B, C> bc);

    // (>>>) (Control.Category, Control.Arrow)
    public default <B, C, D> __<A, B, D> then(__<A, B, C> bc, __<A, C, D> cd) {
        return dot(cd, bc);
    }

    // 2x (>>>)
    public default <B, C, D, E> __<A, B, E> then(__<A, B, C> bc, __<A, C, D> cd, __<A, D, E> de){
        return then(bc, then(cd, de));
    }

    // 3x (>>>)
    public default <B, C, D, E, F> __<A, B, F> then(__<A, B, C> bc, __<A, C, D> cd, __<A, D, E> de, __<A, E, F> ef) {
        return then(bc, cd, then(de, ef));
    }
}