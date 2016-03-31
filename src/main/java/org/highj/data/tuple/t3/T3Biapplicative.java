package org.highj.data.tuple.t3;

import org.highj._;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifunctor.Biapplicative;

public interface T3Biapplicative<S> extends T3Biapply<S>, Biapplicative<_<T3.µ, S>> {

    @Override
    public Monoid<S> getS();

    @Override
    public default <A, B> T3<S, A, B> bipure(A a, B b) {
        return T3.of(getS().identity(), a,b);
    }
}
