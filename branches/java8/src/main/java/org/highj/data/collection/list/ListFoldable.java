package org.highj.data.collection.list;

import org.highj._;
import org.highj.typeclass1.foldable.Foldable;

import java.util.function.Function;
import static  org.highj.data.collection.List.*;

public class ListFoldable implements Foldable<µ> {
    @Override
    public <A, B> B foldr(Function<A, Function<B, B>> fn, B b, _<µ, A> nestedA) {
        return narrow(nestedA).foldr(fn, b);
    }

    @Override
    public <A, B> A foldl(Function<A, Function<B, A>> fn, A a, _<µ, B> bs) {
        return narrow(bs).foldl(a, fn);
    }
}
