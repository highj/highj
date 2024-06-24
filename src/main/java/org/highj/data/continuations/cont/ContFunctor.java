package org.highj.data.continuations.cont;

import org.derive4j.hkt.__;
import org.highj.data.continuations.Cont;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asCont;

public interface ContFunctor<S> extends Functor<__<Cont.µ, S>> {

    @Override
    default <A, B> Cont<S, B> map(Function<A, B> fn, __<__<Cont.µ, S>, A> nestedA) {
        return asCont(asCont(nestedA).map(fn));
    }
}
