package org.highj.data.tuple.t4;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface  T4Apply<S,T,U> extends T4Functor<S,T,U>, Apply<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>> {

    public Semigroup<S> getS();

    public Semigroup<T> getT();

    public Semigroup<U> getU();

    @Override
    public default <A, B> _<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, B> ap(_<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, Function<A, B>> fn, _<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, A> nestedA) {
        T4<S, T, U, Function<A, B>> fnQuadruple = Tuple.narrow4(fn);
        T4<S, T, U, A> aQuadruple = Tuple.narrow4(nestedA);
        return Tuple.of(getS().dot(fnQuadruple._1(), aQuadruple._1()),
                getT().dot(fnQuadruple._2(), aQuadruple._2()),
                getU().dot(fnQuadruple._3(), aQuadruple._3()),
                fnQuadruple._4().apply(aQuadruple._4()));

    }
}
