package org.highj.data.tuple.t3;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface T3Functor<S,T> extends Functor<__.µ<___.µ<T3.µ, S>,T>> {

        @Override
        public default <A, B> T3<S, T, B> map(Function<A, B> fn, _<__.µ<___.µ<T3.µ, S>,T>, A> nestedA) {
            return Tuple.narrow3(nestedA).map_3(fn);
        }
}
