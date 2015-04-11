/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error;

import java.util.function.Function;
import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.collection.Either;
import org.highj.data.transformer.ErrorT;
import org.highj.typeclass1.functor.Functor;

/**
 *
 * @author clintonselke
 */
public interface ErrorTFunctor<E,M> extends Functor<__.µ<___.µ<ErrorT.µ,E>,M>> {
    
    public Functor<M> get();

    @Override
    public default <A, B> ErrorT<E, M, B> map(Function<A, B> fn, _<__.µ<___.µ<ErrorT.µ, E>, M>, A> nestedA) {
        return () -> get().map(
            (Either<E,A> x) -> x.rightMap(fn),
            ErrorT.narrow(nestedA).run()
        );
    }
}
