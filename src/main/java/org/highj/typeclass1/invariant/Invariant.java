package org.highj.typeclass1.invariant;

import org.highj._;

import java.util.function.Function;

public interface Invariant<F> {

    public <A, B> _<F, B> invmap(Function<A, B> fn, Function<B,A> nf, _<F, A> nestedA);

}
