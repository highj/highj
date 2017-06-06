package org.highj.data.structural.tagged;

import org.derive4j.hkt.__;
import org.highj.data.structural.Tagged;
import org.highj.typeclass1.monad.Monad;

public interface TaggedMonad<S> extends Monad<__<Tagged.µ, S>>, TaggedApplicative<S>, TaggedBind<S> {

}
