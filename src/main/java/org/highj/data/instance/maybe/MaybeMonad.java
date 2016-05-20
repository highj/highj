package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadFix;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.util.Lazy;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.highj.data.Maybe.Just;
import static org.highj.data.Maybe.narrow;

public interface MaybeMonad extends MaybeFunctor, Monad<Maybe.µ>, MonadFix<Maybe.µ>, MonadRec<Maybe.µ> {

    @Override
    default  <A> Maybe<A> pure(A a) {
        return Just(a);
    }

    @Override
    default <A, B> Maybe<B> ap(__<Maybe.µ, Function<A, B>> fn, __<Maybe.µ, A> nestedA) {
        return narrow(narrow(fn).cata(Maybe.<B>Nothing(), f1 -> narrow(nestedA).<__<Maybe.µ, B>>cata(
                Maybe.<B>Nothing(), a -> Just(f1.apply(a)))
        ));
    }

    @Override
    default <A, B> Maybe<B> bind(__<Maybe.µ, A> nestedA, Function<A, __<Maybe.µ, B>> fn) {
        return narrow(nestedA).bind(x -> Maybe.narrow(fn.apply(x)));
    }

    @Override
    default <A> Maybe<A> mfix(Function<Supplier<A>, __<Maybe.µ, A>> fn) {
        Lazy<A> lazy = new Lazy<>();
        lazy.set(Maybe.narrow(fn.apply(lazy)).get());
        return Maybe.Just(lazy.get());
    }

    @Override
    default <A, B> Maybe<B> tailRec(Function<A, __<Maybe.µ, Either<A, B>>> function, A startValue) {
        Maybe<Either<A, B>> step = Maybe.Just(Either.Left(startValue));
        while(step.isJust() && step.get().isLeft()) {
            step = Maybe.narrow(function.apply(step.get().getLeft()));
        }
        return step.map(Either::getRight);
    }
}
