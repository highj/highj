package org.highj.typeclass.arrow;


import org.highj.__;

public abstract class SemigroupoidAbstract<µ> implements Semigroupoid<µ> {

    public abstract <A, B, C> __<µ, A, C> dot( __<µ, B, C> bc, __<µ, A, B> ab);

    // (>>>) (Control.Category, Control.Arrow)
    public <A, B, C> __<µ, A, C> then(__<µ, A, B> ab, __<µ, B, C> bc) {
        return dot(bc, ab);
    }

    // 2x (>>>)
    public <A, B, C, D> __<µ, A, D> then(__<µ, A, B> ab, __<µ, B, C> bc, __<µ, C, D> cd) {
        return then(ab, then(bc, cd));
    }

    // 3x (>>>)
    public <A, B, C, D, E> __<µ, A, E> then(__<µ, A, B> ab, __<µ, B, C> bc, __<µ, C, D> cd, __<µ, D, E> de) {
        return then(ab, bc, then(cd, de));
    }

}
