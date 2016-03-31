package org.highj.data.functions;

import org.highj._;
import org.highj.__;
import org.highj.___;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface F2<A,B,C>  extends ___<F2.µ,A,B,C>, BiFunction<A,B,C> {

    class µ {
    }

    @SuppressWarnings("unchecked")
    static <A, B, C> F2<A, B, C> narrow(_<_<_<µ, A>, B>, C> function) {
        return (F2) function;
    }

    @Override
    C apply(A a, B b);

    default F1<B,C> apply(A a) {
        return b -> apply(a,b);
    }

    @Override
    default <V> F2<A, B, V> andThen(Function<? super C, ? extends V> after) {
        return (a,b) -> after.apply(apply(a,b));
    }

}
