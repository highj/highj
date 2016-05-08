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

    public Monoid<W> wMonoid();

    public __<M,T0> tell(W w);

    public <A> __<M,T2<A,W>> listen(__<M,A> nestedA);

    public <A> __<M,A> pass(__<M,T2<A,Function<W,W>>> m);
}

