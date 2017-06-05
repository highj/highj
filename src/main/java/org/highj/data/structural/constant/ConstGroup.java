package org.highj.data.structural.constant;

import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Group;

public interface ConstGroup<S, A> extends Group<Const<S, A>>, ConstMonoid<S, A> {

    @Override
    Group<S> getS();

    @Override
    default Const<S, A> inverse(Const<S, A> sa) {
        return sa.map1(getS()::inverse);
    }
}
