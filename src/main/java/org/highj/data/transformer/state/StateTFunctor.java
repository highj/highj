package org.highj.data.transformer.state;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asStateT;

/**
 * @author Clinton Selke
 */
public interface StateTFunctor<S, M> extends Functor<__<__<StateT.µ, S>, M>> {

    public Functor<M> m();

    @Override
    public default <A, B> StateT<S, M, B> map(Function<A, B> fn, __<__<__<StateT.µ, S>, M>, A> nestedA) {
        return (S s) -> m().map(
                (T2<A, S> x) -> T2.of(fn.apply(x._1()), x._2()),
                asStateT(nestedA).run(s)
        );
    }
}