package org.highj.typeclass1.monad;

import java.util.function.Function;
import org.highj._;

/**
 *
 * @author Clinton Selke
 */
public interface MonadReader<R,M> extends Monad<M> {

    public _<M,R> ask();

    public <A> _<M,A> local(Function<R,R> modFn, _<M,A> nestedA);
}
