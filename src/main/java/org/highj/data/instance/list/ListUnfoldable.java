package org.highj.data.instance.list;

import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.unfoldable.Unfoldable;

import java.util.function.Function;

public interface ListUnfoldable extends Unfoldable<List.µ> {
    @Override
    default <A, B> List<A> unfoldr(Function<B, Maybe<T2<A, B>>> fn, B b) {
        Maybe<T2<A, B>> step = fn.apply(b);
        return step.map(t2 -> List.Cons$(t2._1(), () -> unfoldr(fn, t2._2()))).getOrElse(List.Nil());
    }
}
