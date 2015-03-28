package org.highj.data.structural.endo;

import org.highj._;
import org.highj.data.functions.Functions;
import org.highj.data.structural.Endo;
import org.highj.typeclass1.invariant.Invariant;

import java.util.function.Function;

public class EndoInvariant implements Invariant<Endo.µ> {

    @Override
    public <A, B> _<Endo.µ, B> invmap(Function<A, B> fn, Function<B, A> nf, _<Endo.µ, A> nestedA) {
        Endo<A> endoA = Endo.narrow(nestedA);
        return new Endo<>(b -> fn.apply(endoA.apply(nf.apply(b))));
    }
}
