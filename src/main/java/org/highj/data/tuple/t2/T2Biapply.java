package org.highj.data.tuple.t2;

import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.bifunctor.Biapply;

import java.util.function.Function;

import static org.highj.Hkt.asT2;

public interface T2Biapply extends T2Bifunctor, Biapply<T2.µ> {

    @Override
    default <A, B, C, D> T2<B, D> biapply(__2<T2.µ, Function<A, B>, Function<C, D>> fn, __2<T2.µ, A, C> ac) {
        T2<Function<A, B>, Function<C, D>> fnPair = asT2(fn);
        return bimap(fnPair._1(), fnPair._2(), ac);
    }
}
