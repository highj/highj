package org.highj.typeclass1.functor;

import org.derive4j.hkt.__;
import org.highj.typeclass1.invariant.Invariant1;
import org.highj.typeclass2.injective.Function1;

public interface Functor1<F> extends Invariant1<F> {

    <A, B> __<F, B> map(Function1<A, B> fn, __<F, A> nestedA);

    default <A, B> __<F, B> invmap(Function1<A, B> fn, Function1<B,A> nf, __<F, A> nestedA) {
        return map(fn, nestedA);
    }

}
