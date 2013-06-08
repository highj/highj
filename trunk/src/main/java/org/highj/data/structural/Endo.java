package org.highj.data.structural;

import org.highj._;
import org.highj.data.structural.endo.EndoMonoid;
import org.highj.typeclass0.group.Monoid;

import java.util.function.Function;

public class Endo<A>  {

   public final Function<A,A> appEndo;

    public Endo(Function<A,A> appEndo) {
        this.appEndo = appEndo;
    }

    public static <A> Monoid<Endo<A>> monoid() {
        return new EndoMonoid<>();
    }
}
