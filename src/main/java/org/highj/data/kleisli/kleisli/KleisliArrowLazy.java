/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.kleisli.kleisli;

import java.util.function.Supplier;
import org.highj.__;
import org.highj.___;
import org.highj.data.kleisli.Kleisli;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass2.arrow.ArrowLazy;

/**
 *
 * @author clintonselke
 */
public class KleisliArrowLazy<M> extends KleisliArrow<M> implements ArrowLazy<___.µ<Kleisli.µ,M>> {
    public KleisliArrowLazy(Monad<M> m) {
        super(m);
    }

    @Override
    public <B, C> Kleisli<M, B, C> lazy(Supplier<__<___.µ<Kleisli.µ, M>, B, C>> a) {
        return new Kleisli<>((B x) -> Kleisli.narrow(a.get()).apply(x));
    }
}
