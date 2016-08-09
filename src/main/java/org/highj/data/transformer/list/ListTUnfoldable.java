package org.highj.data.transformer.list;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.ListT;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.unfoldable.Unfoldable;

public interface ListTUnfoldable<M> extends Unfoldable<__<ListT.µ, M>> {

    Monad<M> get();

    @Override
    default <A, B> ListT<M, A> unfoldr(Function<B, Maybe<T2<A, B>>> fn, B b) {
        Function<Maybe<T2<A, B>>, ListT<M,A>> go = new Function<Maybe<T2<A, B>>, ListT<M, A>>() {
            @Override
            public ListT<M, A> apply(Maybe<T2<A, B>> maybe) {
                return maybe.cata$(() -> ListT.nil(get()),
                        t2 -> ListT.cons(get(),
                                () -> t2._1(),
                                () -> this.apply(fn.apply(t2._2()))));
            }
        };
        return go.apply(fn.apply(b));
    }

}
