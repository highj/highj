package org.highj.data.instance.set;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Either;
import org.highj.data.Set;
import org.highj.typeclass1.monad.MonadPlus;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.util.Mutable;

import java.util.function.Function;

import static org.highj.Hkt.asSet;

public interface SetMonadPlus extends SetFunctor, MonadPlus<Set.µ>, MonadRec<Set.µ> {

    @Override
    default <A> Set<A> pure(A a) {
        return Set.of(a);
    }

    @Override
    default <A, B> Set<B> ap(__<Set.µ, Function<A, B>> fn, __<Set.µ, A> nestedA) {
        return asSet(nestedA).ap(asSet(fn));
    }

    @Override
    default <A> Set<A> mzero() {
        return Set.empty();
    }

    @Override
    default <A> Set<A> mplus(__<Set.µ, A> one, __<Set.µ, A> two) {
        return asSet(one).plus(asSet(two));
    }

    @Override
    default <A> Set<A> join(__<Set.µ, __<Set.µ, A>> nestedNestedA) {
        return Set.join(asSet(Hkt.<A>set().subst(nestedNestedA)));
    }

    @Override
    default <A, B> Set<B> bind(__<Set.µ, A> nestedA, Function<A, __<Set.µ, B>> fn) {
        return asSet(nestedA).bind(fn.andThen(Hkt::asSet));
    }

    @Override
    default <A, B> Set<B> tailRec(Function<A, __<Set.µ, Either<A, B>>> function, A startA) {
        Set<Either<A, B>> step = Set.of(Either.Left(startA));
        Mutable<Boolean> hasChanged = Mutable.newMutable();
        do {
            hasChanged.set(false);
            step = step.bind(e -> e.either(
                    left -> {
                        hasChanged.set(true);
                        return asSet(function.apply(left));
                    },
                    right -> Set.of(e)
            ));
        } while (hasChanged.get());
        return step.bind(e -> e.isRight() ? Set.of(e.getRight()) : Set.empty());
    }
}
