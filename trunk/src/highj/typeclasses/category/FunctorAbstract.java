/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Function;
import fj.P2;
import fj.Unit;
import highj._;

/**
 *
 * @author DGronau
 */
public abstract class FunctorAbstract<Ctor> extends FunctorBoundedAbstract<Ctor, Object> implements Functor<Ctor> {

   
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
    
}
