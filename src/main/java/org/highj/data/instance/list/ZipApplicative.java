package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.List;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.Hkt.asList;
import static org.highj.data.List.*;

public interface ZipApplicative extends Applicative<µ>, ListFunctor {
    @Override
    default <A> List<A> pure(A a) {
        return cycle(a);
    }

    @Override
    default <A, B> List<B> ap(__<µ, Function<A, B>> fn, __<µ, A> nestedA) {
        return zipWith(asList(fn), asList(nestedA), Function::apply);
    }

}
