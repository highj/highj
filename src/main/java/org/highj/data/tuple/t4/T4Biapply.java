package org.highj.data.tuple.t4;

import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.bifunctor.Biapply;

import java.util.function.Function;

public interface T4Biapply<S, T> extends T4Bifunctor<S, T>, Biapply<___.µ<____.µ<T4.µ, S>, T>> {

    public Semigroup<S> getS();

    public Semigroup<T> getT();

    @Override
    public default <A, B, C, D> T4<S, T, B, D> biapply(__<___.µ<____.µ<T4.µ, S>, T>, Function<A, B>, Function<C, D>> fn, __<___.µ<____.µ<T4.µ, S>, T>, A, C> ac) {
        T4<S, T, Function<A, B>, Function<C, D>> quadFn = T4.narrow(fn);
        T4<S, T, A, C> quadAC = T4.narrow(ac);
        return T4.of(getS().dot(quadFn._1(), quadAC._1()),
                getT().dot(quadFn._2(), quadAC._2()),
                quadFn._3().apply(quadAC._3()),
                quadFn._4().apply(quadAC._4())
        );
    }
}
