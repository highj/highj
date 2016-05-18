package org.highj.data.collection.identity;

import org.derive4j.hkt.__;
import org.highj.data.collection.Identity;
import org.highj.typeclass1.monad.Applicative;

public interface IdentityApplicative extends IdentityApply, Applicative<Identity.µ> {
    @Override
    default <A> __<Identity.µ, A> pure(A a) {
        return Identity.identity(a);
    }
}
