/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.collection.Either;

/**
 *
 * @author clintonselke
 */
public interface ErrorT<E,M,A> extends ___<ErrorT.µ,E,M,A> {
    public static class µ {}
    
    public static <E,M,A> ErrorT<E,M,A> narrow(___<ErrorT.µ,E,M,A> a) {
        return (ErrorT<E,M,A>)a;
    }
    
    public static <E,M,A> ErrorT<E,M,A> narrow(_<__.µ<___.µ<ErrorT.µ, E>, M>, A> a) {
        return (ErrorT<E,M,A>)a;
    }
    
    public _<M,Either<E,A>> run();
    
    
}
