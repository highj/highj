package org.highj.data.collection.list;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;
import static  org.highj.data.collection.List.*;

//Todo extends to Traversable1
public class ListTraversable extends ListFunctor implements Traversable<µ> {
    @Override
    public <A, B> B foldr(Function<A, Function<B, B>> fn, B b, _<µ, A> nestedA) {
        return narrow(nestedA).foldr(fn, b);
    }

    @Override
    public <A, B> A foldl(Function<A, Function<B, A>> fn, A a, _<µ, B> bs) {
        return narrow(bs).foldl(a, fn);
    }

    @Override
    public <A, B, X> _<X, _<µ, B>> traverse(Applicative<X> applicative, Function<A, _<X, B>> fn, _<µ, A> traversable) {
        //traverse f = Prelude.foldr cons_f (pure [])
        //  where cons_f x ys = (:) <$> f x <*> ys
        List<A> listA = narrow(traversable);
        _<µ, B> emptyB = List.<B>empty();
        return listA.foldr(a -> bs -> {
            _<X,Function<_<µ, B>, _<µ, B>>> mapF =
                    applicative.map(e -> es -> List.<B>narrow(es).plus(e), fn.apply(a));
            return applicative.ap(mapF, bs);
        }, applicative.pure(emptyB));
    }
}
