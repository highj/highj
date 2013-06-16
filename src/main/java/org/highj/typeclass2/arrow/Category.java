package org.highj.typeclass2.arrow;

import org.highj.__;

public interface Category<A> extends Semigroupoid<A>{

    // id (Control.Category)
    public <B> __<A, B, B> identity();

}