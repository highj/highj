package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.Hkt.asList;
import static org.highj.data.List.*;

//Todo extends to Traversable1
public interface ListTraversable extends ListFunctor, Traversable<µ> {

    @Override
    default <A, B> List<B> map(final Function<A, B> fn, __<µ, A> nestedA) {
        return ListFunctor.super.map(fn,nestedA);
    }

    @Override
    default <A, B> B foldr(Function<A, Function<B, B>> fn, B b, __<µ, A> nestedA) {
        return asList(nestedA).foldr((x,y) -> fn.apply(x).apply(y), b);
    }

    @Override
    default <A, B> A foldl(Function<A, Function<B, A>> fn, A a, __<µ, B> bs) {
        return asList(bs).foldl(a, (x,y) -> fn.apply(x).apply(y));
    }

    @Override
    default <A, B, X> __<X, __<µ, B>> traverse(Applicative<X> applicative, Function<A, __<X, B>> fn, __<µ, A> traversable) {
        //traverse f = Prelude.foldr cons_f (pure [])
        //  where cons_f x ys = (:) <$> f x <*> ys
        List<A> listA = asList(traversable);
        __<µ, B> emptyB = List.empty();
        return listA.foldr((a, bs) -> {
            __<X,Function<__<µ, B>, __<µ, B>>> mapF =
                    applicative.map(e -> es -> asList(es).plus(e), fn.apply(a));
            return applicative.ap(mapF, bs);
        }, applicative.pure(emptyB));
    }
}
