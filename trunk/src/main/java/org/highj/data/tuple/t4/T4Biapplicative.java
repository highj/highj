package org.highj.data.tuple.t4;

import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifunctor.Biapplicative;

public interface T4Biapplicative<S, T> extends T4Biapply<S, T>, Biapplicative<___.µ<____.µ<T4.µ, S>, T>> {
    @Override
    public Monoid<S> getS();

    @Override
    public Monoid<T> getT();

    @Override
    public default <A, B> T4<S, T, A, B> bipure(A a, B b) {
        return T4.of(getS().identity(), getT().identity(), a, b);
    }
}
