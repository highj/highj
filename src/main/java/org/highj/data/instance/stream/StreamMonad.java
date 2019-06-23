package org.highj.data.instance.stream;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Stream;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadZip;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.highj.Hkt.asStream;
import static org.highj.data.Stream.*;

public interface StreamMonad extends StreamFunctor, Monad<µ>, MonadZip<µ> {
    @Override
    default <A> Stream<A> pure(A a) {
        return repeat(a);
    }

    @Override
    default <A, B> Stream<B> ap(__<µ, Function<A, B>> fn, __<µ, A> nestedA) {
        return zipWith(Function::apply, fn, nestedA);
    }

    @Override
    default <A> Stream<A> join(__<µ, __<µ, A>> nestedNestedA) {
        return Stream.join(asStream(nestedNestedA).map(Hkt::asStream));
    }

    @Override
    default <A, B, C> __<µ, C> mzipWith(BiFunction<A, B, C> fn, __<µ, A> ma, __<µ, B> mb) {
        return zipWith(fn, asStream(ma), asStream(mb));
    }
}
