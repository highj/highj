package org.highj.typeclass.arrow;

import org.highj.__;

public interface Category<µ> extends Semigroupoid<µ>{

    // id (Control.Category)
    public <A> __<µ, A, A> identity();

}