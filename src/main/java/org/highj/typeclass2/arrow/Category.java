package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__2;

public interface Category<A> extends Semigroupoid<A> {

    // id (Control.Category)
    <B> __2<A, B, B> identity();

}