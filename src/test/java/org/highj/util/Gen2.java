package org.highj.util;

import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.tuple.T2;

/**
 * A generator for testing type constructors.
 *
 * @param <F> the type constuctor type
 */
public interface Gen2<F> {
    <T, U> Gen<__2<F, T, U>> gen(Gen<T> gen1, Gen<U> gen2);

    Gen2<T2.µ> t2Gen2 = new Gen2<T2.µ>() {
        @Override
        public <T, U> Gen<__2<T2.µ, T, U>> gen(Gen<T> gen1, Gen<U> gen2) {
            return Gen.zip(gen1, gen2).map(t2 -> t2);
        }
    };

    Gen2<Either.µ> eitherGen2 = new Gen2<Either.µ>() {
        @Override
        public <T, U> Gen<__2<Either.µ, T, U>> gen(Gen<T> gen1, Gen<U> gen2) {
            return Gen.zip(gen1, gen2).map(
                t2 -> Gen.rnd.nextBoolean() ? Either.Left(t2._1()) : Either.Right(t2._2()));
        }
    };

}
