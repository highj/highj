package org.highj.data.instance.either;

import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.Either;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifoldable.Bifoldable;

import java.util.function.Function;

public interface EitherBifoldable extends Bifoldable<Either.µ> {

    @Override
    default <M, A, B> M bifoldMap(Monoid<M> monoid, Function<A, M> fn1, Function<B, M> fn2, __2<Either.µ, A, B> nestedAB) {
        return Hkt.asEither(nestedAB).either(fn1, fn2);
    }
}
