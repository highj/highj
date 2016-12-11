package org.highj.data.coroutine.producer;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.monad.Apply;

import static org.highj.Hkt.asProducerT;

public interface ProducerTApply<E,M> extends ProducerTFunctor<E,M>, Apply<__<__<ProducerT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<ProducerT.µ, E>, M>, B> ap(__<__<__<ProducerT.µ, E>, M>, Function<A, B>> fn, __<__<__<ProducerT.µ, E>, M>, A> nestedA) {
        return ProducerT.bind(asProducerT(fn),
            (Function<A, B> x1) ->
                ProducerT.bind(asProducerT(nestedA),
                    (A x2) -> ProducerT.done(x1.apply(x2))
                )
        );
    }
}
