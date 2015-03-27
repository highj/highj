package org.highj.data.transformer.state;

import java.util.function.Function;

import org.highj.HigherKinded;
import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;

/**
 * @author Clinton Selke
 */
public interface StateTApply<S, M> extends StateTFunctor<S, M>, Apply<__.µ<___.µ<StateT.µ, S>, M>> {

    public Bind<M> get();

    @Override
    public default <A, B> StateT<S, M, B> ap(_<__.µ<___.µ<StateT.µ, S>, M>, Function<A, B>> fn, _<__.µ<___.µ<StateT.µ, S>, M>, A> nestedA) {
        return (S s1) -> get().bind(
                StateT.narrow(HigherKinded.uncurry3(HigherKinded.uncurry2(fn))).run(s1),
                (T2<Function<A, B>, S> x1) -> {
                    Function<A, B> fn2 = x1._1();
                    S s2 = x1._2();
                    return get().map(
                            (T2<A, S> x2) -> T2.of(fn2.apply(x2._1()), x2._2()),
                            StateT.narrow(HigherKinded.uncurry3(HigherKinded.uncurry2(nestedA))).run(s2)
                    );
                }
        );
    }
}