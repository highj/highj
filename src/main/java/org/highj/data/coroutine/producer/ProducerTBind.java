package org.highj.data.coroutine.producer;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.monad.Bind;

import static org.highj.Hkt.asProducerT;

public interface ProducerTBind<E,M> extends ProducerTApply<E,M>, Bind<__<__<ProducerT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<ProducerT.µ, E>, M>, B> bind(__<__<__<ProducerT.µ, E>, M>, A> nestedA, Function<A, __<__<__<ProducerT.µ, E>, M>, B>> fn) {
        return ProducerT.bind(asProducerT(nestedA), (A a) -> asProducerT(fn.apply(a)));
    }
}
