package org.highj.data.collection.set;

import org.highj._;
import org.highj.data.collection.Either;
import org.highj.data.collection.Set;
import org.highj.typeclass1.monad.MonadPlus;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.util.Mutable;

import java.util.function.Function;

public interface SetMonadPlus extends SetFunctor, MonadPlus<Set.µ>, MonadRec<Set.µ> {

    @Override
    default <A> Set<A> pure(A a) {
        return Set.of(a);
    }

    @Override
    default <A, B> Set<B> ap(_<Set.µ, Function<A, B>> fn, _<Set.µ, A> nestedA) {
        return Set.narrow(nestedA).ap(Set.narrow(fn));
    }

    @Override
    default <A> Set<A> mzero() {
        return Set.empty();
    }

    @Override
    default <A> Set<A> mplus(_<Set.µ, A> one, _<Set.µ, A> two) {
        return Set.narrow(one).plus(Set.narrow(two));
    }

    @Override
    default <A> Set<A> join(_<Set.µ, _<Set.µ, A>> nestedNestedA) {
        return Set.join(Set.narrow(nestedNestedA).map(Set::narrow));
    }

    @Override
    default <A, B> Set<B> bind(_<Set.µ, A> nestedA, Function<A, _<Set.µ, B>> fn) {
        return Set.narrow(nestedA).bind(fn.andThen(Set::narrow));
    }

    @Override
    default <A, B> Set<B> tailRec(Function<A, _<Set.µ, Either<A, B>>> function, A startA) {
        Set<Either<A, B>> step = Set.of(Either.newLeft(startA));
        Mutable<Boolean> hasChanged = Mutable.newMutable();
        do {
            hasChanged.set(false);
            step = step.bind(e -> e.either(
                    left -> {
                        hasChanged.set(true);
                        return Set.narrow(function.apply(left));
                    },
                    right -> Set.of(e)
            ));
        } while (hasChanged.get());
        return step.bind(e -> e.isRight() ? Set.of(e.getRight()) : Set.empty());
    }
}
