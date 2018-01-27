package org.highj.typeclass1.contravariant;


import org.derive4j.hkt.__;
import org.highj.function.NF;
import org.highj.typeclass1.invariant.Invariant1;

public interface Contravariant1<F> extends Invariant1<F> {

    <A, B> __<F, A> contramap(NF<A, B> fn, __<F, B> nestedB);

    default <A, B> __<F, B> invmap(NF<A, B> fn, NF<B, A> nf, __<F, A> nestedA) {
        return contramap(nf, nestedA);
    }
}
