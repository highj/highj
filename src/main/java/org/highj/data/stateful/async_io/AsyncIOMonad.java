package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.stateful.AsyncIO;
import org.highj.typeclass1.monad.Monad;

public interface AsyncIOMonad<E> extends AsyncIOApplicative<E>, AsyncIOBind<E>, Monad<__<AsyncIO.µ,E>> {
}
