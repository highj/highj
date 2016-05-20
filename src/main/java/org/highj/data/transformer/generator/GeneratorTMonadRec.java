/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.generator;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.transformer.GeneratorT;
import org.highj.typeclass1.monad.MonadRec;

/**
 *
 * @author clintonselke
 */
public interface GeneratorTMonadRec<E,M> extends GeneratorTMonad<E,M>, MonadRec<__<__<GeneratorT.µ,E>,M>> {

    @Override
    public default <A, B> __<__<__<GeneratorT.µ, E>, M>, B> tailRec(Function<A, __<__<__<GeneratorT.µ, E>, M>, Either<A, B>>> function, A startA) {
        return GeneratorT.<E,M>monad().bind(
            function.apply(startA),
            (Either<A,B> x) -> x.either(
                (A a) -> GeneratorT.suspend(() -> GeneratorT.narrow(tailRec(function, a))),
                (B b) -> GeneratorT.<E,M>applicative().pure(b)
            )
        );
    }
}
