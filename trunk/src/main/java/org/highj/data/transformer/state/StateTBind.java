package org.highj.data.transformer.state;

import java.util.function.Function;

import org.highj.HigherKinded;
import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Bind;

/**
 * @author Clinton Selke
 */
public interface StateTBind<S, M> extends StateTApply<S, M>, Bind<__.µ<___.µ<StateT.µ, S>, M>> {

    public Bind<M> get();

    @Override
    public default <A, B> StateT<S, M, B> bind(_<__.µ<___.µ<StateT.µ, S>, M>, A> nestedA, Function<A, _<__.µ<___.µ<StateT.µ, S>, M>, B>> fn) {
        return (S s1) -> get().bind(
                StateT.narrow(HigherKinded.uncurry3(HigherKinded.uncurry2(nestedA))).run(s1),
                (T2<A, S> x) -> {
                    A a = x._1();
                    S s2 = x._2();
                    return StateT.narrow(HigherKinded.uncurry3(HigherKinded.uncurry2(fn.apply(a)))).run(s2);
                }
        );
    }

}