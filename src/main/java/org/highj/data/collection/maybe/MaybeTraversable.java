package org.highj.data.collection.maybe;

import org.derive4j.hkt.__;
import org.highj.data.collection.Maybe;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.data.collection.Maybe.narrow;
import static org.highj.data.collection.Maybe.µ;

//Todo extends to Traversable1
public interface MaybeTraversable extends MaybeFunctor, Traversable<µ> {

    @Override
    default <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, __<µ, A> as) {
        return narrow(as).<B>cata(mb.identity(), fn::apply);
    }

    @Override
    default <A, B> B foldr(Function<A, Function<B, B>> fn, B b, __<µ, A> as) {
        return narrow(as).cata(b, a -> fn.apply(a).apply(b));
    }

    @Override
    default <A, B> A foldl(Function<A, Function<B, A>> fn, A a, __<µ, B> bs) {
        return narrow(bs).cata(a, b -> fn.apply(a).apply(b));
    }

    @Override
    default <A, B> Maybe<B> map(Function<A, B> fn, __<µ, A> nestedA){
        return MaybeFunctor.super.map(fn, nestedA);
    }

    @Override
    default <A, B, X> __<X, __<µ, B>> traverse(Applicative<X> applicative, Function<A, __<X, B>> fn, __<µ, A> traversable) {
        //traverse __ Nothing = pure Nothing
        //traverse f (Just x) = Just <$> f x
        Maybe<A> maybe = narrow(traversable);
        return maybe.isNothing()
                ? applicative.<__<µ, B>>pure(Maybe.newNothing())
                : applicative.<B,__<µ, B>>map(Maybe::newJust, fn.apply(maybe.get()));
    }

}
