package org.highj.data.tuple.t3;

import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

public interface T3Applicative<S,T> extends T3Apply<S,T>, Applicative<__.µ<___.µ<T3.µ, S>, T>> {

    @Override
    public Monoid<S> getS();

    @Override
    public Monoid<T> getT();

    @Override
    public default <A> T3<S, T, A> pure(A a) {
        return T3.of(getS().identity(), getT().identity(), a);
    }

}
