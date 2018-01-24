package org.highj.typeclass1.contravariant;


import org.derive4j.hkt.__;
import org.highj.typeclass1.invariant.Invariant1;
import org.highj.typeclass2.injective.Function1;

public interface Contravariant1<F> extends Invariant1<F> {

    <A, B> __<F, A> contramap(Function1<A, B> fn, __<F, B> nestedB);

    default <A, B> __<F, B> invmap(Function1<A, B> fn, Function1<B, A> nf, __<F, A> nestedA) {
        return contramap(nf, nestedA);
    }
}
