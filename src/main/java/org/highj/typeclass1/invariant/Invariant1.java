package org.highj.typeclass1.invariant;

import org.derive4j.hkt.__;
import org.highj.typeclass2.injective.Function1;

public interface Invariant1<F> {

    <A, B> __<F, B> invmap(Function1<A, B> fn, Function1<B, A> nf, __<F, A> nestedA);

}
