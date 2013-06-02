package org.highj.data.collection.set;

import org.highj._;
import org.highj.data.collection.Set;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.function.Function;

public class SetMonadPlus extends SetFunctor implements MonadPlus<Set.µ> {

    @Override
    public <A> _<Set.µ, A> pure(A a) {
        return Set.of(a);
    }

    @Override
    public <A, B> _<Set.µ, B> ap(_<Set.µ, Function<A, B>> fn, _<Set.µ, A> nestedA) {
        Set<B> result = Set.empty();
        for (Function<A, B> f : Set.narrow(fn)) {
            for (A a : Set.narrow(nestedA)) {
                result = result.plus(f.apply(a));
            }
        }
        return result;
    }

    @Override
    public <A> _<Set.µ, A> mzero() {
        return Set.empty();
    }

    @Override
    public <A> _<Set.µ, A> mplus(_<Set.µ, A> one, _<Set.µ, A> two) {
        return Set.narrow(one).plus(Set.narrow(two));
    }

    @Override
    public <A> _<Set.µ, A> join(_<Set.µ, _<Set.µ, A>> nestedNestedA) {
        Set<A> result = Set.empty();
        for (_<Set.µ, A> innerSet : Set.narrow(nestedNestedA)) {
            result = result.plus(Set.narrow(innerSet));
        }
        return result;
    }

}
