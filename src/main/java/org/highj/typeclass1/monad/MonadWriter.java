package org.highj.typeclass1.monad;

import java.util.function.Function;
import org.highj._;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;

/**
 *
 * @author Clinton Selke
 */
public interface MonadWriter<W,M> extends Monad<M> {

    public Monoid<W> wMonoid();

    public _<M,T0> tell(W w);

    public <A> _<M,T2<A,W>> listen(_<M,A> nestedA);

    public <A> _<M,A> pass(_<M,T2<A,Function<W,W>>> m);
}

