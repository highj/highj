/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Function;
import fj.Unit;
import highj.TC;
import highj._;
import highj.typeclasses.category.Functor;

/**
 *
 * @author DGronau
 */
public abstract class FunctorAbstract<Ctor extends TC<Ctor>> implements Functor<Ctor> {

   
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
    //.: and binary (Data.Functor.Syntax)
    public <A, B, C2 extends TC<C2>> _<Ctor, _<C2, B>> binary(
            final Functor<C2> functor2, final F<A, B> fn, _<Ctor, _<C2, A>> nestedA) {
        return fmap(functor2.<A, B>fmapFunction().f(fn), nestedA);
    }

    @Override
    //.:: and trinary (Data.Functor.Syntax)
    public <A, B, C2 extends TC<C2>, C3 extends TC<C3>> _<Ctor, _<C2, _<C3, B>>> 
            trinary(final Functor<C2> functor2, final Functor<C3> functor3, 
            final F<A, B> fn, _<Ctor, _<C2, _<C3, A>>> nestedNestedA) {
        return binary(functor2, functor3.<A,B>fmapFunction().f(fn), nestedNestedA);
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
    public <A, B> F<F<A,B>,F< _<Ctor, A>,_<Ctor, B>>> fmapFunction() {
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
