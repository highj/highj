/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful.effect1;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.stateful.Effect1;
import org.highj.typeclass1.contravariant.Contravariant;

import static org.highj.Hkt.asEffect1;

/**
 *
 * @author clintonselke
 */
public interface Effect1Contravariant extends Contravariant<Effect1.µ> {

    @Override
    public default <A, B> Effect1<A> contramap(Function<A, B> fn, __<Effect1.µ, B> nestedB) {
        return (A a) -> asEffect1(nestedB).run(fn.apply(a));
    }
}
