/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.effect0;

import org.highj.data.stateful.Effect0;
import org.highj.typeclass0.group.Monoid;

/**
 *
 * @author clintonselke
 */
public interface Effect0Monoid extends Effect0Semigroup, Monoid<Effect0> {

    @Override
    public default Effect0 identity() {
        return () -> {};
    }
    
}
