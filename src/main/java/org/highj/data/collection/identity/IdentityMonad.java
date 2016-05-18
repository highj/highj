package org.highj.data.collection.identity;

import org.highj.data.collection.Identity;
import org.highj.typeclass1.monad.Monad;

public interface IdentityMonad extends IdentityApplicative, IdentityBind, Monad<Identity.µ> {}
