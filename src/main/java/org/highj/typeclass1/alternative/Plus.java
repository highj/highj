package org.highj.typeclass1.alternative;

import org.highj._;
import org.highj.typeclass0.group.Monoid;

public interface Plus<µ> extends Alt<µ> {

    //mzero (Control.Applicative)
    public <A> _<µ, A> mzero();

    public default <A> Monoid<_<µ, A>> asMonoid() {
        return new Monoid<_<µ, A>>() {
            @Override
            public _<µ, A> identity() {
                return mzero();
            }

            @Override
            public _<µ, A> dot(_<µ, A> x, _<µ, A> y) {
                return mplus(x, y);
            }
        };
    }
}
