package org.highj.data.instance.these;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass2.bifoldable.Bitraversable;

import java.util.function.Function;

import static org.highj.data.These.Both;

public interface TheseBitraversable extends TheseBifoldable, Bitraversable<These.µ> {

    @Override
    default <A, B, A1, B1, X> __<X, __2<These.µ, A1, B1>> bitraverse(
            Applicative<X> applicative,
            Function<A, __<X, A1>> fn1,
            Function<B, __<X, B1>> fn2,
            __2<These.µ, A, B> traversable) {
        return Hkt.asThese(traversable).these(
                a -> applicative.map(These::This, fn1.apply(a)),
                b -> applicative.map(These::That, fn2.apply(b)),
                (a, b) -> applicative.ap(
                        applicative.map(a1 -> b1 -> Both(a1, b1), fn1.apply(a)),
                        fn2.apply(b))
        );
    }
}
