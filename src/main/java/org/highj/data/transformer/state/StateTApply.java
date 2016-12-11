package org.highj.data.transformer.state;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asStateT;

/**
 * @author Clinton Selke
 */
public interface StateTApply<S, M> extends StateTFunctor<S, M>, Apply<__<__<StateT.µ, S>, M>> {

    public Bind<M> m();

    @Override
    default <A, B> StateT<S, M, B> ap(__<__<__<StateT.µ, S>, M>, Function<A, B>> fn, __<__<__<StateT.µ, S>, M>, A> nestedA) {
        return (S s1) -> m().bind(asStateT(fn).run(s1),
                (T2<Function<A, B>, S> x1) -> {
                    Function<A, B> fn2 = x1._1();
                    S s2 = x1._2();
                    return m().map(
                            (T2<A, S> x2) -> T2.of(fn2.apply(x2._1()), x2._2()),
                            asStateT(nestedA).run(s2)
                    );
                }
        );
    }
}