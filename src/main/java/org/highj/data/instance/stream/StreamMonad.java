package org.highj.data.instance.stream;

import org.derive4j.hkt.__;
import org.highj.data.Stream;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadZip;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.highj.Hkt.asStream;
import static org.highj.data.Stream.*;

public interface StreamMonad extends Monad<µ>, MonadZip<µ> {
    @Override
    default <A> Stream<A> pure(A a) {
        return repeat(a);
    }

    @Override
    default <A, B> Stream<B> ap(__<µ, Function<A, B>> fn, __<µ, A> nestedA) {
        return zipWith(Function::apply, fn, nestedA);
    }

    @Override
    default <A, B> Stream<B> map(Function<A, B> fn, __<µ, A> nestedA) {
        return asStream(nestedA).map(fn);
    }

    @Override
    default <A> Stream<A> join(__<µ, __<µ, A>> nestedNestedA) {
        Stream<__<µ, A>> nestedStream = asStream(nestedNestedA);
        Stream<A> xs = asStream(nestedStream.head());
        final Stream<__<µ, A>> xss = nestedStream.tail();
        return newLazyStream(xs.head(), () -> {
            Stream<__<µ, A>> tails = xss.<__<µ, A>>map(as -> asStream(as).tail());
            return join(tails);
        });
    }

    @Override
    default <A, B, C> __<µ, C> mzipWith(BiFunction<A, B, C> fn, __<µ, A> ma, __<µ, B> mb) {
        return zipWith(fn, asStream(ma), asStream(mb));
    }
}
