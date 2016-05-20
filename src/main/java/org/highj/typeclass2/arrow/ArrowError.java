/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.data.tuple.T2;

/**
 *
 * @author clintonselke
 */
public interface ArrowError<EX,A> extends Arrow<A> {
    
    public <B> __2<A,EX,B> raise();
    
    public default <B,C> __2<A,B,C> handle(__2<A,B,C> body, __2<A,T2<B,EX>,C> onError) {
        return tryInUnless(body, arr(T2::_2), onError);
    }
    
    public <B,C,D> __2<A,B,D> tryInUnless(__2<A,B,C> body, __2<A,T2<B,C>,D> onSuccess, __2<A,T2<B,EX>,D> onError);
    
    public default <B,C> __2<A,B,Either<EX,C>> newError(__2<A,B,C> body) {
        return handle(dot(arr(Either::Right), body), arr(F1.compose(Either::Left, T2::_2)));
    }
}
