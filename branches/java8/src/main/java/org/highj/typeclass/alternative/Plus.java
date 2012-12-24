package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.function.F2;
import org.highj.typeclass.group.Monoid;

public interface Plus<µ> extends Alt<µ> {

    //mzero (Control.Applicative)
    public <A> _<µ, A> mzero();

    public default <A> Monoid<_<µ, A>> asMonoid() {
        return new Monoid<_<µ, A>>(){
            @Override
            public _<µ, A> identity() {
                return mzero();
            }

            @Override
            public F2<_<µ, A>, _<µ, A>, _<µ, A>> dot() {
                return mplus();
            }
        };
    }

}
