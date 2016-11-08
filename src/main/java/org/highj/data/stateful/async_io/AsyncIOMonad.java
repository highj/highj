package org.highj.data.stateful.async_io;

import org.highj.data.stateful.AsyncIO;
import org.highj.typeclass1.monad.Monad;

public interface AsyncIOMonad extends AsyncIOApplicative, AsyncIOBind, Monad<AsyncIO.µ> {
}
