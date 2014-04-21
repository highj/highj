package org.highj.data.structural.endo;

import org.highj.data.functions.Functions;
import org.highj.data.structural.Endo;
import org.highj.typeclass0.group.Monoid;

import java.util.function.Function;

public class EndoMonoid<A> implements Monoid<Endo<A>> {

    private final Endo<A> id =  new Endo<>(Function.identity());

    @Override
    public Endo<A> identity() {
        return id;
    }

    @Override
    public Endo<A> dot(Endo<A> x, Endo<A> y) {
        return new Endo<>(Functions.compose(x.appEndo, y.appEndo));
    }
}
