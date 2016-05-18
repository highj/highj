package org.highj.data.transformer.generator;

import org.derive4j.hkt.__;
import org.highj.data.transformer.GeneratorT;
import org.highj.typeclass1.monad.Monad;

public interface GeneratorTMonad<E,M> extends GeneratorTApplicative<E,M>, GeneratorTBind<E,M>, Monad<__<__<GeneratorT.µ,E>,M>> {}
