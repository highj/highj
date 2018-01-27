package org.highj.typeclass1.invariant;

import org.derive4j.hkt.__;
import org.highj.function.NF;

public interface Invariant1<F> {

    <A, B> __<F, B> invmap(NF<A, B> fn, NF<B, A> nf, __<F, A> nestedA);

}
