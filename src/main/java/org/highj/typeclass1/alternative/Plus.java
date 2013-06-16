package org.highj.typeclass1.alternative;

import org.highj._;
import org.highj.typeclass0.group.Monoid;

public interface Plus<F> extends Alt<F> {

    //mzero (Control.Applicative)
    public <A> _<F, A> mzero();

    public default <A> Monoid<_<F, A>> asMonoid() {
        return new Monoid<_<F, A>>() {
            @Override
            public _<F, A> identity() {
                return mzero();
            }

            @Override
            public _<F, A> dot(_<F, A> x, _<F, A> y) {
                return mplus(x, y);
            }
        };
    }
}
