/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.effect1;

import org.highj.data.stateful.Effect1;
import org.highj.typeclass0.group.Semigroup;

/**
 *
 * @author clintonselke
 */
public interface Effect1Semigroup<A> extends Semigroup<Effect1<A>> {

    @Override
    public default Effect1<A> apply(Effect1<A> x, Effect1<A> y) {
        return (A a) -> { x.run(a); y.run(a); };
    }
}
