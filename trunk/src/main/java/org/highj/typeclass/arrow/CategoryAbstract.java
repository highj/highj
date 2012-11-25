package org.highj.typeclass.arrow;

import org.highj.__;

public abstract class CategoryAbstract<µ> extends SemigroupoidAbstract<µ> implements Category<µ> {

    // id (Control.Category)
    public abstract <A> __<µ, A, A> identity();
}
