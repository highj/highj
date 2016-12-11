package org.highj.data.transformer.state;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asStateT;

/**
 * @author Clinton Selke
 */
public interface StateTBind<S, M> extends StateTApply<S, M>, Bind<__<__<StateT.µ, S>, M>> {

    public Bind<M> m();

    @Override
    public default <A, B> StateT<S, M, B> bind(__<__<__<StateT.µ, S>, M>, A> nestedA, Function<A, __<__<__<StateT.µ, S>, M>, B>> fn) {
        return (S s1) -> m().bind(
                asStateT(nestedA).run(s1),
                (T2<A, S> x) -> {
                    A a = x._1();
                    S s2 = x._2();
                    return asStateT(fn.apply(a)).run(s2);
                }
        );
    }

}