package org.highj.typeclass.arrow;

import org.highj.__;

public interface Semigroupoid<µ> {

    // dot (Data.Semigroupoid), (.) (Control.Category)
    <A, B, C> __<µ, A, C> dot(__<µ, B, C> bc, __<µ, A, B> ab);

    // (>>>) (Control.Category, Control.Arrow)
    public <A, B, C> __<µ, A, C> then(__<µ, A, B> ab, __<µ, B, C> bc);

    // 2x (>>>)
    public <A, B, C, D> __<µ, A, D> then(__<µ, A, B> ab, __<µ, B, C> bc, __<µ, C, D> cd);

    // 3x (>>>)
    public <A, B, C, D, E> __<µ, A, E> then(__<µ, A, B> ab, __<µ, B, C> bc, __<µ, C, D> cd, __<µ, D, E> de);
}