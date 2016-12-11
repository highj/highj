package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Maybe;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asMaybe;

public interface MaybeFunctor extends Functor<Maybe.µ> {

    default <A, B> Maybe<B> map(Function<A, B> fn, __<Maybe.µ, A> nestedA) {
        return asMaybe(nestedA).map(fn);
    }

}
