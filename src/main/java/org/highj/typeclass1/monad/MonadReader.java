package org.highj.typeclass1.monad;

import java.util.function.Function;
import org.derive4j.hkt.__;

/**
 *
 * @author Clinton Selke
 */
public interface MonadReader<R,M> extends Monad<M> {

    public __<M,R> ask();

    public <A> __<M,A> local(Function<R,R> modFn, __<M,A> nestedA);
}
