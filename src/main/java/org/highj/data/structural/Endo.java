package org.highj.data.structural;

import org.highj._;
import org.highj.data.structural.endo.EndoInvariant;
import org.highj.typeclass0.group.Monoid;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Endo<A> implements _<Endo.µ, A>, UnaryOperator<A> {

    public static class µ {
    }

    public final Function<A, A> appEndo;

    public Endo(UnaryOperator<A> appEndo) {
        this.appEndo = appEndo;
    }

    @Override
    public A apply(A a) {
        return appEndo.apply(a);
    }

    @SuppressWarnings("unchecked")
    public static <A> Endo<A> narrow(_<Endo.µ, A> nested) {
        return (Endo) nested;
    }

    public static <A> Monoid<Endo<A>> monoid() {
        return Monoid.create(new Endo<>(UnaryOperator.identity()), (x,y) ->
                new Endo<>(a -> y.apply(x.apply(a))));
    }

    public static final EndoInvariant invariant = new EndoInvariant();
}
