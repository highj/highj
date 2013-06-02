package org.highj.data.predicates.predicates;

import org.highj.data.predicates.Predicates;
import org.highj.typeclass0.group.Group;

import java.util.function.Predicate;

public class AndGroup<A> implements Group<Predicate<A>> {

    @Override
    public Predicate<A> inverse(Predicate<A> f) {
        return Predicates.not(f);
    }

    @Override
    public Predicate<A> identity() {
        return Predicates.True();
    }

    @Override
    public Predicate<A> dot(Predicate<A> f, Predicate<A> g) {
        return x -> f.test(x) && g.test(x);
    }
}
