package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asList;

public interface ListFunctor extends Functor<List.µ> {
    @Override
    default <A, B> List<B> map(final Function<A, B> fn, __<List.µ, A> nestedA) {
        return asList(nestedA).map(fn);
    }
}
