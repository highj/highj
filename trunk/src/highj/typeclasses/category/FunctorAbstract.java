/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Function;
import fj.Unit;
import highj._;

/**
 *
 * @author DGronau
 */
public abstract class FunctorAbstract<Ctor> implements Functor<Ctor> {

   
    @Override
    public abstract <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);
    
    @Override
    // (<$)
    public <A, B> _<Ctor, A> left$(A a, _<Ctor, B> nestedB) {
        return fmap(Function.<B,A>constant().f(a), nestedB);
    }

    @Override
    //void (Control.Monad)
    public <A> _<Ctor, Unit> voidF(_<Ctor, A> nestedA) {
        return fmap(Function.<A,Unit>constant(Unit.unit()),nestedA);
    }

    @Override
    //flip (Data.Functor.Syntax)
    public <A, B> _<Ctor, B> flip(_<Ctor, F<A, B>> nestedFn, final A a) {
        return fmap(new F<F<A,B>, B>(){
            @Override
            public B f(F<A, B> fun) {
                return fun.f(a);
            }
        }, nestedFn); 
    }
    
    @Override
    public <A, B> F<F<A,B>,F< _<Ctor, A>,_<Ctor, B>>> fmapFn() {
        return new F<F<A,B>,F< _<Ctor, A>,_<Ctor, B>>>(){
            @Override
            public F<_<Ctor, A>, _<Ctor, B>> f(final F<A, B> f) {
                return new F<_<Ctor, A>, _<Ctor, B>>() {
                    @Override
                    public _<Ctor, B> f(_<Ctor, A> a) {
                        return fmap(f,a);
                    }
                };
            }
        }; 
    }
    
}
