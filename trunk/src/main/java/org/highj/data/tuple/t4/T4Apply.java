package org.highj.data.tuple.t4;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface  T4Apply<S,T,U> extends T4Functor<S,T,U>, Apply<__.µ<___.µ<____.µ<T4.µ,S>, T>, U>> {

    public Semigroup<S> getS();

    public Semigroup<T> getT();

    public Semigroup<U> getU();

    @Override
    public default <A, B> T4<S, T, U, B> ap(_<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, Function<A, B>> fn, _<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, A> nestedA) {
        T4<S, T, U, Function<A, B>> fnQuadruple = T4.narrow(fn);
        T4<S, T, U, A> aQuadruple = T4.narrow(nestedA);
        return T4.of(getS().dot(fnQuadruple._1(), aQuadruple._1()),
                getT().dot(fnQuadruple._2(), aQuadruple._2()),
                getU().dot(fnQuadruple._3(), aQuadruple._3()),
                fnQuadruple._4().apply(aQuadruple._4()));

    }
}
