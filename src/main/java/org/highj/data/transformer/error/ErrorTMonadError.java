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
import org.highj.typeclass1.monad.MonadError;

/**
 *
 * @author clintonselke
 */
public interface ErrorTMonadError<E,M> extends ErrorTMonad<E,M>, MonadError<E,_<_<ErrorT.µ,E>,M>> {
    
    @Override
    public default <A> ErrorT<E, M, A> throwError(E error) {
        return () -> get().pure(Either.<E,A>newLeft(error));
    }

    @Override
    public default <A> ErrorT<E, M, A> catchError(_<_<_<ErrorT.µ, E>, M>, A> ma, Function<E, _<_<_<ErrorT.µ, E>, M>, A>> fn) {
        return () -> get().bind(
            ErrorT.narrow(ma).run(),
            (Either<E,A> a) -> a.either(
                (E e) -> ErrorT.narrow(fn.apply(e)).run(),
                (A a2) -> get().pure(Either.<E,A>newRight(a2))
            )
        );
    }
}
