package org.highj.data.coroutine.producer;

import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.monad.Monad;

public interface GeneratorTMonad<E,M> extends GeneratorTApplicative<E,M>, GeneratorTBind<E,M>, Monad<__<__<ProducerT.µ,E>,M>> {}
