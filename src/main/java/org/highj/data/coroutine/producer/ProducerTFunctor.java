package org.highj.data.coroutine.producer;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.functor.Functor;

import static org.highj.Hkt.asProducerT;

public interface ProducerTFunctor<E,M> extends Functor<__<__<ProducerT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<ProducerT.µ, E>, M>, B> map(Function<A, B> fn, __<__<__<ProducerT.µ, E>, M>, A> nestedA) {
        return ProducerT.bind(asProducerT(nestedA), (A a) -> ProducerT.done(fn.apply(a)));
    }
}
