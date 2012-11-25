package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.group.MonoidAbstract;

public abstract class PlusAbstract<mu> extends AltAbstract<mu> implements Plus<mu> {

    @Override
    public abstract <A> _<mu, A> mzero();

    @Override
    public <A> Monoid<_<mu, A>> asMonoid() {
        return new MonoidAbstract<_<mu, A>>(this.<A>mplus(), this.<A>mzero());
    }

}

