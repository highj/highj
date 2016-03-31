package org.highj.data.transformer.state;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.StateT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

/**
 * @author Clinton Selke
 */
public interface StateTBind<S, M> extends StateTApply<S, M>, Bind<_<_<StateT.µ, S>, M>> {

    public Bind<M> m();

    @Override
    public default <A, B> StateT<S, M, B> bind(_<_<_<StateT.µ, S>, M>, A> nestedA, Function<A, _<_<_<StateT.µ, S>, M>, B>> fn) {
        return (S s1) -> m().bind(
                StateT.narrow(nestedA).run(s1),
                (T2<A, S> x) -> {
                    A a = x._1();
                    S s2 = x._2();
                    return StateT.narrow(fn.apply(a)).run(s2);
                }
        );
    }

}