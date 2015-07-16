/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.functions.f1;

import java.util.function.Supplier;
import org.highj.__;
import org.highj.data.functions.F1;
import org.highj.typeclass2.arrow.ArrowLazy;

/**
 *
 * @author clintonselke
 */
public class F1ArrowLazy extends F1Arrow implements ArrowLazy<F1.µ> {

    @Override
    public <B, C> F1<B, C> lazy(Supplier<__<F1.µ, B, C>> a) {
        return (B x) -> F1.narrow(a.get()).apply(x);
    }
}
