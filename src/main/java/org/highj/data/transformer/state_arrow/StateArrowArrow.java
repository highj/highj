/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.state_arrow;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.transformer.StateArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

/**
 *
 * @author clintonselke
 */
public interface StateArrowArrow<S,A> extends StateArrowCategory<S,A>, Arrow<_<_<StateArrow.µ,S>,A>> {
    
    @Override
    public Arrow<A> a();

    @Override
    public default <B, C> StateArrow<S, A, B, C> arr(Function<B, C> fn) {
        return StateArrow.stateArrow(a().arr((T2<B,S> x) -> x.map_1(fn)));
    }

    @Override
    public default <B, C, D> StateArrow<S, A, T2<B, D>, T2<C, D>> first(__<_<_<StateArrow.µ, S>, A>, B, C> arrow) {
        return StateArrow.stateArrow(
            a().dot(
                a().arr((T2<T2<C,S>,D> x) -> T2.of(T2.of(x._1()._1(), x._2()), x._1()._2())),
                a().dot(
                    a().first(StateArrow.narrow(arrow).run()),
                    a().arr((T2<T2<B,D>,S> x) -> T2.of(T2.of(x._1()._1(), x._2()), x._1()._2()))
                )
            )
        );
    }
}
