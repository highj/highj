/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.effect0;

import org.highj.data.stateful.Effect0;
import org.highj.typeclass0.group.Semigroup;

/**
 *
 * @author clintonselke
 */
public interface Effect0Semigroup extends Semigroup<Effect0> {

    @Override
    public default Effect0 apply(Effect0 x, Effect0 y) {
        return () -> { x.run(); y.run(); };
    }
}
