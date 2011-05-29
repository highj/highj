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
public abstract class FunctorAbstract<Ctor> extends FunctorBoundedAbstract<Ctor, Object> implements Functor<Ctor> {

    //fmap and <$> (Data.Functor)
    @Override
    public abstract <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);

   
    //void (Control.Monad)
    @Override
    public <A> _<Ctor, Unit> voidF(_<Ctor, A> nestedA) {
        return fmap(Function.<A,Unit>constant(Unit.unit()),nestedA);
    }

    //flip (Data.Functor.Syntax)
    @Override
    public <A, B> _<Ctor, B> flip(_<Ctor, F<A, B>> nestedFn, final A a) {
        return fmap(new F<F<A,B>, B>(){
            @Override
            public B f(F<A, B> fun) {
                return fun.f(a);
            }
        }, nestedFn); 
    }
    
}
