package org.highj.data.tuple.t4;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

public interface T4Applicative<S,T,U> extends T4Apply<S,T,U>, Applicative<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>> {

    @Override
    public Monoid<S> getS();

    @Override
    public Monoid<T> getT();

    @Override
    public Monoid<U> getU();

    @Override
    public default <A> _<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>, A> pure(A a) {
        return Tuple.of(getS().identity(), getT().identity(), getU().identity(), a);
    }

}
