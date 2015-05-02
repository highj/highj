/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.effect1;

import org.highj.data.stateful.Effect1;
import org.highj.typeclass0.group.Monoid;

/**
 *
 * @author clintonselke
 */
public interface Effect1Monoid<A> extends Effect1Semigroup<A>, Monoid<Effect1<A>> {

    @Override
    public default Effect1<A> identity() {
        return (A a) -> {};
    }
}
