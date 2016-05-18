package org.highj.data.collection.identity;

import org.derive4j.hkt.__;
import org.highj.data.collection.Identity;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface IdentityFunctor extends Functor<Identity.µ> {
    @Override
    default <A, B> __<Identity.µ, B> map(Function<A, B> fn, __<Identity.µ, A> nestedA) {
        return Identity.identity(fn.apply(Identity.narrow(nestedA).run()));
    }
}
