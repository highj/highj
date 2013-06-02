package org.highj.data.collection.list;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.data.collection.List.*;

public class ZipApplicative implements Applicative<µ> {
    @Override
    public <A> List<A> pure(A a) {
        return repeat(a);
    }

    @Override
    public <A, B> List<B> ap(_<µ, Function<A, B>> fn, _<µ, A> nestedA) {
        return zipWith(narrow(fn), narrow(nestedA), f -> f::apply);
    }

    @Override
    public <A, B> List<B> map(Function<A, B> fn, _<µ, A> nestedA) {
        return narrow(nestedA).map(fn);
    }
}
