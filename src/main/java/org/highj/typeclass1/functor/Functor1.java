package org.highj.typeclass1.functor;

import org.derive4j.hkt.__;
import org.highj.function.NF;
import org.highj.typeclass1.invariant.Invariant1;

public interface Functor1<F> extends Invariant1<F> {

    <A, B> __<F, B> map(NF<A, B> fn, __<F, A> nestedA);

    default <A, B> __<F, B> invmap(NF<A, B> fn, NF<B, A> nf, __<F, A> nestedA) {
        return map(fn, nestedA);
    }

}
