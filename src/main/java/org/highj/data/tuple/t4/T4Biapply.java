package org.highj.data.tuple.t4;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.bifunctor.Biapply;

import java.util.function.Function;

import static org.highj.Hkt.asT4;

public interface T4Biapply<S, T> extends T4Bifunctor<S, T>, Biapply<__<__<T4.µ, S>, T>> {

    Semigroup<S> getS();

    Semigroup<T> getT();

    @Override
    default <A, B, C, D> T4<S, T, B, D> biapply(__2<__<__<T4.µ, S>, T>, Function<A, B>, Function<C, D>> fn, __2<__<__<T4.µ, S>, T>, A, C> ac) {
        T4<S, T, Function<A, B>, Function<C, D>> quadFn = asT4(fn);
        T4<S, T, A, C> quadAC = asT4(ac);
        return T4.of(getS().apply(quadFn._1(), quadAC._1()),
                getT().apply(quadFn._2(), quadAC._2()),
                quadFn._3().apply(quadAC._3()),
                quadFn._4().apply(quadAC._4())
        );
    }
}
