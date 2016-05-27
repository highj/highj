package org.highj.data.coroutine.producer;

import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.typeclass1.monad.Monad;

public interface ProducerTMonad<E,M> extends ProducerTApplicative<E,M>, ProducerTBind<E,M>, Monad<__<__<ProducerT.µ,E>,M>> {}
