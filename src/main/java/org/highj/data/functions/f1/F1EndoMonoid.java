package org.highj.data.functions.f1;

import org.highj.data.functions.F1;
import org.highj.typeclass0.group.Monoid;

public class F1EndoMonoid<A> implements Monoid<F1<A,A>> {
    @Override
    public F1<A, A> identity() {
        return F1.id();
    }

    @Override
    public F1<A, A> dot(F1<A, A> x, F1<A, A> y) {
        return F1.compose(F1.narrow(x), F1.narrow(y));
    }
}
