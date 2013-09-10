package org.highj.data.tuple.t2;

import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.bifunctor.Biapply;

import java.util.function.Function;

public interface T2Biapply extends T2Bifunctor, Biapply<T2.µ> {

    @Override
    public default <A, B, C, D> T2<B, D> biapply(__<T2.µ, Function<A, B>, Function<C, D>> fn, __<T2.µ, A, C> ac) {
        T2<Function<A, B>, Function<C, D>> fnPair = T2.narrow(fn);
        return bimap(fnPair._1(), fnPair._2(), ac);
    }
}
