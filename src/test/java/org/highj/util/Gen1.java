package org.highj.util;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.Maybe;

/**
 * A generator for testing type constructors.
 *
 * @param <F> the type constuctor type
 */
public interface Gen1<F> {
    <T> Gen<__<F,T>> gen(Gen<T> gen);

    Gen1<Maybe.µ> maybeGen1 = new Gen1<Maybe.µ>() {
        @Override
        public <T> Gen<__<Maybe.µ, T>> gen(Gen<T> gen) {
            return gen.map(s ->
                    Gen.rnd.nextDouble() < 0.9 ? Maybe.Just(s) : Maybe.Nothing());
        }
    };

    Gen1<List.µ> listGen1 = new Gen1<List.µ>() {
        @Override
        public <T> Gen<__<List.µ, T>> gen(Gen<T> gen) {
            return maxSize -> List.range(0, 1, maxSize-1).map(i ->
                    List.fromIterable(gen.get(i)));
        }
    };
}
