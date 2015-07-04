/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.functions.F1;
import org.highj.data.tuple.T2;

/**
 *
 * @author clintonselke
 */
public interface ArrowError<EX,A> extends Arrow<A> {
    
    public <B> __<A,EX,B> raise();
    
    public default <B,C> __<A,B,C> handle(__<A,B,C> body, __<A,T2<B,EX>,C> onError) {
        return tryInUnless(body, arr(T2::_2), onError);
    }
    
    public <B,C,D> __<A,B,D> tryInUnless(__<A,B,C> body, __<A,T2<B,C>,D> onSuccess, __<A,T2<B,EX>,D> onError);
    
    public default <B,C> __<A,B,Either<EX,C>> newError(__<A,B,C> body) {
        return handle(dot(arr(Either::newRight), body), arr(F1.compose(Either::newLeft, T2::_2)));
    }
}
