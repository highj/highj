package org.highj.data.continuations.cont;

import org.derive4j.hkt.__;
import org.highj.data.continuations.Cont;
import org.highj.function.Functions;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.Hkt.asCont;
import static org.highj.data.continuations.Cont.*;

/**
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public interface ContMonad<S> extends ContApplicative<S>, Monad<__<µ, S>> {
    @Override
    default  <A, B> Cont<S, B> bind(__<__<µ, S>, A> nestedA, Function<A, __<__<µ, S>, B>> fn) {
        return asCont(nestedA).bind(a -> asCont(fn.apply(a)));
    }
}