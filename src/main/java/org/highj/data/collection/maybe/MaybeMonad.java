package org.highj.data.collection.maybe;

import org.highj._;
import org.highj.data.collection.Either;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadFix;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.util.Lazy;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.highj.data.collection.Maybe.newJust;
import static org.highj.data.collection.Maybe.narrow;

public interface MaybeMonad extends MaybeFunctor, Monad<Maybe.µ>, MonadFix<Maybe.µ>, MonadRec<Maybe.µ> {

    @Override
    default  <A> Maybe<A> pure(A a) {
        return newJust(a);
    }

    @Override
    default <A, B> Maybe<B> ap(_<Maybe.µ, Function<A, B>> fn, _<Maybe.µ, A> nestedA) {
        return narrow(narrow(fn).cata(Maybe.<B>newNothing(), f1 -> narrow(nestedA).<_<Maybe.µ, B>>cata(
                Maybe.<B>newNothing(), a -> newJust(f1.apply(a)))
        ));
    }

    @Override
    default <A, B> Maybe<B> bind(_<Maybe.µ, A> nestedA, Function<A, _<Maybe.µ, B>> fn) {
        return narrow(nestedA).bind(x -> Maybe.narrow(fn.apply(x)));
    }

    @Override
    default <A> Maybe<A> mfix(Function<Supplier<A>, _<Maybe.µ, A>> fn) {
        Lazy<A> lazy = new Lazy<>();
        lazy.set(Maybe.narrow(fn.apply(lazy)).get());
        return Maybe.newJust(lazy.get());
    }

    @Override
    default <A, B> Maybe<B> tailRec(Function<A, _<Maybe.µ, Either<A, B>>> function, A startValue) {
        Maybe<Either<A, B>> step = Maybe.newJust(Either.newLeft(startValue));
        while(step.isJust() && step.get().isLeft()) {
            step = Maybe.narrow(function.apply(step.get().getLeft()));
        }
        return step.map(Either::getRight);
    }
}
