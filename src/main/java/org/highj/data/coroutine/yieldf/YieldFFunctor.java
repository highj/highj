/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.coroutine.yieldf;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.coroutine.YieldF;
import org.highj.typeclass1.functor.Functor;

import static org.highj.Hkt.asYieldF;

/**
 *
 * @author clintonselke
 */
public interface YieldFFunctor<V> extends Functor<__<YieldF.µ,V>> {

    @Override
    public default <A, B> __<__<YieldF.µ, V>, B> map(Function<A, B> fn, __<__<YieldF.µ, V>, A> nestedA) {
        YieldF<V,A> x = asYieldF(nestedA);
        return YieldF.yield(x.value(), fn.apply(x.next()));
    }
}
