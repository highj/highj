package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.typeclass.group.Monoid;

public interface Plus<µ> extends Alt<µ> {

    //mzero (Control.Applicative)
    public <A> _<µ, A> mzero();

    public <A> Monoid<_<µ, A>> asMonoid();

}
