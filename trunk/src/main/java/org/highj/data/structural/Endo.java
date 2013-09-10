package org.highj.data.structural;

import org.highj._;
import org.highj.data.structural.endo.EndoInvariant;
import org.highj.data.structural.endo.EndoMonoid;
import org.highj.typeclass0.group.Monoid;

import java.util.function.Function;

public class Endo<A> implements _<Endo.µ, A> {

    public static class µ {
    }

    public final Function<A, A> appEndo;

    public A appEndo(A a) {
        return appEndo.apply(a);
    }

    public Endo(Function<A, A> appEndo) {
        this.appEndo = appEndo;
    }

    @SuppressWarnings("unchecked")
    public static <A> Endo<A> narrow(_<Endo.µ, A> nested) {
        return (Endo) nested;
    }

    public static <A> Monoid<Endo<A>> monoid() {
        return new EndoMonoid<>();
    }

    public static final EndoInvariant invariant = new EndoInvariant();
}
