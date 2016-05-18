package org.highj.data.collection.identity;

import org.derive4j.hkt.__;
import org.highj.data.collection.Identity;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface IdentityApply extends IdentityFunctor, Apply<Identity.µ> {
    @Override
    default <A, B> __<Identity.µ, B> ap(__<Identity.µ, Function<A, B>> fn, __<Identity.µ, A> nestedA) {
        Function<A,B> x = Identity.narrow(fn).run();
        return Identity.identity(x.apply(Identity.narrow(nestedA).run()));
    }
}
