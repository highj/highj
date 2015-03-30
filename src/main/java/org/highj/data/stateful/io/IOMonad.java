package org.highj.data.stateful.io;

import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.Monad;

public interface IOMonad extends IOBind, IOApplicative, Monad<IO.µ> {
}
