package org.highj.data.collection.list;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.data.collection.List.*;

public interface ZipApplicative extends Applicative<µ>, ListFunctor {
    @Override
    default <A> List<A> pure(A a) {
        return repeat(a);
    }

    @Override
    default <A, B> List<B> ap(_<µ, Function<A, B>> fn, _<µ, A> nestedA) {
        Function<Function<A,B>, Function<A,B>> zipFn = f -> f;
        return zipWith(narrow(fn), narrow(nestedA), zipFn);
    }

}
