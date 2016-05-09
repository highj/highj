package org.highj.typeclass1.alternative;

import org.derive4j.hkt.__;
import org.highj.typeclass0.group.Monoid;

public interface Plus<F> extends Alt<F> {

    //mzero (Control.Applicative)
    public <A> __<F, A> mzero();

    public default <A> Monoid<__<F, A>> asMonoid() {
        return Monoid.create(mzero(), this::mplus);
    }
}
