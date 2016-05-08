package org.highj.typeclass1.monad;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.tuple.T0;

/**
 * @author Clinton Selke
 */
public interface MonadState<S, M> extends Monad<M> {

    public __<M, S> get();

    public __<M, T0> put(S s);

    public default __<M, T0> modify(Function<S, S> fn) {
        return bind(get(), (S s) -> put(fn.apply(s)));
    }

}
