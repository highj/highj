/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.collection.Either;
import org.highj.data.transformer.ErrorT;
import org.highj.typeclass1.monad.Applicative;

/**
 *
 * @author clintonselke
 */
public interface ErrorTApplicative<E,M> extends ErrorTApply<E,M>, Applicative<_<_<ErrorT.µ,E>,M>> {
    
    public Applicative<M> get();

    @Override
    public default <A> ErrorT<E, M, A> pure(A a) {
        return () -> get().pure(Either.newRight(a));
    }
}
