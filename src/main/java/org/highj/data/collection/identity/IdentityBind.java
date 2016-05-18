package org.highj.data.collection.identity;

import org.derive4j.hkt.__;
import org.highj.data.collection.Identity;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

public interface IdentityBind extends IdentityApply, Bind<Identity.µ> {
    @Override
    default <A, B> __<Identity.µ, B> bind(__<Identity.µ, A> nestedA, Function<A, __<Identity.µ, B>> fn) {
        return fn.apply(Identity.narrow(nestedA).run());
    }
}
