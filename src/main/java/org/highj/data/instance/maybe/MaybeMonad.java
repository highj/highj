package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadFix;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.typeclass1.monad.MonadZip;
import org.highj.util.Lazy;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.highj.Hkt.asMaybe;
import static org.highj.data.Maybe.Just;

public interface MaybeMonad extends MaybeFunctor, Monad<Maybe.µ>, MonadFix<Maybe.µ>, MonadRec<Maybe.µ>, MonadZip<Maybe.µ> {

    @Override
    default  <A> Maybe<A> pure(A a) {
        return Just(a);
    }

    @Override
    default <A, B> Maybe<B> ap(__<Maybe.µ, Function<A, B>> fn, __<Maybe.µ, A> nestedA) {
        return asMaybe(asMaybe(fn).cata(Maybe.<B>Nothing(), f1 -> asMaybe(nestedA).<__<Maybe.µ, B>>cata(
                Maybe.<B>Nothing(), a -> Just(f1.apply(a)))
        ));
    }

    @Override
    default <A, B> Maybe<B> bind(__<Maybe.µ, A> nestedA, Function<A, __<Maybe.µ, B>> fn) {
        return asMaybe(nestedA).bind(x -> asMaybe(fn.apply(x)));
    }

    @Override
    default <A> Maybe<A> mfix(Function<Supplier<A>, __<Maybe.µ, A>> fn) {
        Lazy<A> lazy = new Lazy<>();
        lazy.set(asMaybe(fn.apply(lazy)).get());
        return Maybe.Just(lazy.get());
    }

    @Override
    default <A, B> Maybe<B> tailRec(Function<A, __<Maybe.µ, Either<A, B>>> function, A startValue) {
        Maybe<Either<A, B>> step = Maybe.Just(Either.Left(startValue));
        while(step.isJust() && step.get().isLeft()) {
            step = asMaybe(function.apply(step.get().getLeft()));
        }
        return step.map(Either::getRight);
    }

    @Override
    default <A, B, C> __<Maybe.µ, C> mzipWith(BiFunction<A, B, C> fn, __<Maybe.µ, A> ma, __<Maybe.µ, B> mb) {
        return Maybe.zipWith(Hkt.asMaybe(ma), Hkt.asMaybe(mb), fn);
    }
}
