package org.highj.data.tuple.t4;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

public interface T4Bind<S,T,U> extends T4Apply<S,T,U>, Bind<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>> {
    @Override
    public default <A, B> T4<S, T, U, B> bind(_<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>, A> nestedA,
                                                             Function<A, _<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>, B>> fn) {
        T4<S, T, U, A> ta = Tuple.narrow4(nestedA);
        T4<S, T, U, B> tb = Tuple.narrow4(fn.apply(ta._4()));
        return Tuple.of(getS().dot(ta._1(), tb._1()),
                getT().dot(ta._2(), tb._2()),
                getU().dot(ta._3(), tb._3()),
                tb._4());
    }
}
