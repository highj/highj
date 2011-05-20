/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 * Functor utilies
 * @author dgronau
 */
public final class Functors {
    
    private Functors() { 
        throw new UnsupportedOperationException(); 
    }
    
    //.: and binary (Data.Functor.Syntax)
    public static <A, B, Ctor1, Ctor2> _<Ctor1, _<Ctor2, B>> binary(
            Functor<Ctor1> functor1, final Functor<Ctor2> functor2, 
            F<A, B> fn, _<Ctor1, _<Ctor2, A>> nestedA) {
        return functor1.fmap(functor2.<A, B>fmapFn().f(fn), nestedA);
    }

    //.:: and trinary (Data.Functor.Syntax)
    public static <A, B, Ctor1, Ctor2, Ctor3> _<Ctor1, _<Ctor2, _<Ctor3, B>>> 
            trinary(Functor<Ctor1> functor1, final Functor<Ctor2> functor2, final Functor<Ctor3> functor3, 
            final F<A, B> fn, _<Ctor1, _<Ctor2, _<Ctor3, A>>> nestedNestedA) {
        return binary(functor1, functor2, functor3.<A,B>fmapFn().f(fn), nestedNestedA);
    }
    
}
