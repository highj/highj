package org.highj.typeclass1.monad;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;

/**
 *
 * @author Clinton Selke
 */
public interface MonadWriter<W,M> extends Monad<M> {

    Monoid<W> getW();

    __<M,T0> tell(W w);

    <A> __<M,T2<A,W>> listen(__<M,A> nestedA);

    <A> __<M,A> pass(__<M,T2<A,Function<W,W>>> m);
}

