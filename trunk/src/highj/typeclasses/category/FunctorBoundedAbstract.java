/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Function;
import highj._;

/**
 *
 * @author DGronau
 */
public abstract class FunctorBoundedAbstract<Ctor, Bound> implements FunctorBounded<Ctor, Bound> {
   
    @Override
    public abstract <A extends Bound, B  extends Bound> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);
    
    @Override
    // (<$)
    public <A extends Bound, B extends Bound> _<Ctor, A> left$(A a, _<Ctor, B> nestedB) {
        return fmap(Function.<B,A>constant().f(a), nestedB);
    }

    //liftA (Control.Aplicative), liftM (Control.Monad), curried version of fmap
    @Override
    public <A extends Bound, B extends Bound> F<_<Ctor, A>, _<Ctor, B>> lift(final F<A, B> fn) {
         return new F< _<Ctor, A>,_<Ctor, B>>(){
            @Override
            public _<Ctor, B> f(_<Ctor, A> a) {
                return fmap(fn, a);
            }
        }; 
    }
    
}
