/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.generator;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.GeneratorT;
import org.highj.typeclass1.monad.MonadTrans;

/**
 *
 * @author clintonselke
 */
public interface GeneratorTMonadTrans<E,M> extends GeneratorTMonad<E,M>, MonadTrans<__<GeneratorT.µ,E>,M> {

    @Override
    public default <A> __2<__<GeneratorT.µ, E>, M, A> lift(__<M, A> nestedA) {
        return GeneratorT.lift(nestedA);
    }
}
