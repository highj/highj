package org.highj.data.structural.endo;

import org.derive4j.hkt.__;
import org.highj.data.structural.Endo;
import org.highj.typeclass1.invariant.Invariant;

import java.util.function.Function;

import static org.highj.Hkt.asEndo;

public class EndoInvariant implements Invariant<Endo.µ> {

    @Override
    public <A, B> __<Endo.µ, B> invmap(Function<A, B> fn, Function<B, A> nf, __<Endo.µ, A> nestedA) {
        Endo<A> endoA = asEndo(nestedA);
        return new Endo<>(b -> fn.apply(endoA.apply(nf.apply(b))));
    }
}
