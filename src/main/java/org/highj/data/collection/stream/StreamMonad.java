package org.highj.data.collection.stream;

import org.highj._;
import org.highj.data.collection.Stream;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;


public class StreamMonad implements Monad<Stream.µ> {
    @Override
    public <A> Stream<A> pure(A a) {
        return Stream.repeat(a);
    }

    @Override
    public <A, B> Stream<B> ap(_<Stream.µ, Function<A, B>> fn, _<Stream.µ, A> nestedA) {
        return Stream.zipWith(f1 -> f1::apply, fn, nestedA);
    }

    @Override
    public <A, B> Stream<B> map(Function<A, B> fn, _<Stream.µ, A> nestedA) {
        return Stream.narrow(nestedA).map(fn);
    }

    @Override
    public <A> Stream<A> join(_<Stream.µ, _<Stream.µ, A>> nestedNestedA) {
        Stream<_<Stream.µ, A>> nestedStream = Stream.narrow(nestedNestedA);
        Stream<A> xs = Stream.narrow(nestedStream.head());
        final Stream<_<Stream.µ, A>> xss = nestedStream.tail();
        return Stream.newLazyStream(xs.head(), () -> {
            Stream<_<Stream.µ, A>> tails = xss.<_<Stream.µ, A>>map(as -> Stream.narrow(as).tail());
            return join(tails);
        });
    }
}
