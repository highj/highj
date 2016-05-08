package org.highj.data.collection.stream;

import org.derive4j.hkt.__;
import org.highj.data.collection.Stream;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;


public class StreamMonad implements Monad<Stream.µ> {
    @Override
    public <A> Stream<A> pure(A a) {
        return Stream.repeat(a);
    }

    @Override
    public <A, B> Stream<B> ap(__<Stream.µ, Function<A, B>> fn, __<Stream.µ, A> nestedA) {
        return Stream.zipWith(f1 -> f1::apply, fn, nestedA);
    }

    @Override
    public <A, B> Stream<B> map(Function<A, B> fn, __<Stream.µ, A> nestedA) {
        return Stream.narrow(nestedA).map(fn);
    }

    @Override
    public <A> Stream<A> join(__<Stream.µ, __<Stream.µ, A>> nestedNestedA) {
        Stream<__<Stream.µ, A>> nestedStream = Stream.narrow(nestedNestedA);
        Stream<A> xs = Stream.narrow(nestedStream.head());
        final Stream<__<Stream.µ, A>> xss = nestedStream.tail();
        return Stream.newLazyStream(xs.head(), () -> {
            Stream<__<Stream.µ, A>> tails = xss.<__<Stream.µ, A>>map(as -> Stream.narrow(as).tail());
            return join(tails);
        });
    }
}
