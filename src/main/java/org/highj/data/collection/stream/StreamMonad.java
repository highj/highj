package org.highj.data.collection.stream;

import org.highj._;
import org.highj.data.collection.Stream;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.data.collection.Stream.µ;
import static org.highj.data.collection.Stream.narrow;
import static org.highj.data.collection.Stream.repeat;
import static org.highj.data.collection.Stream.zipWith;
import static org.highj.data.collection.Stream.Cons;

public class StreamMonad implements Monad<µ> {
    @Override
    public <A> _<µ, A> pure(A a) {
        return repeat(a);
    }

    @Override
    public <A, B> _<µ, B> ap(_<µ, Function<A, B>> fn, _<µ, A> nestedA) {
        return zipWith(f1 -> f1::apply, fn, nestedA);
    }

    @Override
    public <A, B> _<µ, B> map(Function<A, B> fn, _<µ, A> nestedA) {
        return narrow(nestedA).map(fn);
    }

    @Override
    public <A> _<µ, A> join(_<µ, _<µ, A>> nestedNestedA) {
        Stream<_<µ, A>> nestedStream = narrow(nestedNestedA);
        Stream<A> xs = narrow(nestedStream.head());
        final Stream<_<µ, A>> xss = nestedStream.tail();
        return Cons(xs.head(), () -> {
            Stream<_<µ, A>> tails = xss.<_<µ, A>>map(as -> Stream.narrow(as).tail());
            return Stream.narrow(join(tails));
        });
    }
}
