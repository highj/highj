/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import highj.CL;
import highj.TC2;
import highj._;
import highj.__;
import highj.typeclasses.category.FunctorAbstract;

/**
 * Takes care of left-currying the arguments when implementing a Functor 
 * for a binary type constructor
 * 
 * @author DGronau
 */
public abstract class CLFunctor<Ctor extends TC2<Ctor>, X> extends FunctorAbstract<CL<Ctor,X>> {

    private final CL<Ctor,X> cl = new CL<Ctor, X>();
    
    @Override
    public <A, B> _<CL<Ctor, X>, B> fmap(F<A, B> fn, _<CL<Ctor, X>, A> nestedA) {
        return cl.curry(fmap(fn, cl.uncurry(nestedA)));
    }

    public abstract <A, B> __<Ctor, X, B> fmap(F<A, B> fn, __<Ctor, X, A> nestedA);
    
}
