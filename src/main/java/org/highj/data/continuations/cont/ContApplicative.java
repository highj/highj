package org.highj.data.continuations.cont;

import org.derive4j.hkt.__;
import org.highj.data.continuations.Cont;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.Hkt.asCont;

public interface ContApplicative<S> extends ContFunctor<S>, Applicative<__<Cont.µ, S>> {

    @Override
    default <A> Cont<S, A> pure(A a) {
        return Cont.pure(a);
    }

    @Override
    default <A, B> Cont<S, B> ap(__<__<Cont.µ, S>, Function<A, B>> fn, __<__<Cont.µ, S>, A> nestedA) {
        return asCont(nestedA).ap(asCont(fn));
    }
}
