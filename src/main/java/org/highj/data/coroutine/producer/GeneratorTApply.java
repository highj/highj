package org.highj.data.coroutine.producer;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.monad.Apply;

public interface GeneratorTApply<E,M> extends GeneratorTFunctor<E,M>, Apply<__<__<ProducerT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<ProducerT.µ, E>, M>, B> ap(__<__<__<ProducerT.µ, E>, M>, Function<A, B>> fn, __<__<__<ProducerT.µ, E>, M>, A> nestedA) {
        return ProducerT.bind(ProducerT.narrow(fn),
            (Function<A, B> x1) ->
                ProducerT.bind(ProducerT.narrow(nestedA),
                    (A x2) -> ProducerT.done(x1.apply(x2))
                )
        );
    }
}
