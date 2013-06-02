package org.highj.data.predicates.pred;

import org.highj.data.predicates.Pred;
import org.highj.typeclass0.group.Group;

public class OrGroup<A> implements Group<Pred<A>> {

    @Override
    public Pred<A> inverse(Pred<A> f) {
        return f.not();
    }

    @Override
    public Pred<A> identity() {
        return Pred.False();
    }

    @Override
    public Pred<A> dot(Pred<A> f, Pred<A> g) {
        return x -> f.test(x) || g.test(x);
    }
}