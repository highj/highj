package org.highj.data.tuple.t4;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface T4Functor<S,T,U> extends Functor<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>> {
    @Override
    public default <A, B> T4<S, T, U, B> map(Function<A, B> fn, _<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, A> nestedA) {
        return Tuple.narrow4(nestedA).map_4(fn);
    }
}
