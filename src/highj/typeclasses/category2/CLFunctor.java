/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import highj.CL;
import highj._;
import highj.__;
import highj.typeclasses.category.FunctorAbstract;

/**
 * Takes care of left-currying the arguments when implementing a Functor 
 * for a binary type constructor
 * 
 * @author DGronau
 */
public abstract class CLFunctor<Ctor, X> extends FunctorAbstract<CL<Ctor,X>> {

    @Override
    public <A, B> _<CL<Ctor, X>, B> fmap(F<A, B> fn, _<CL<Ctor, X>, A> nestedA) {
        return CL.curry(fmap(fn, CL.uncurry(nestedA)));
    }

    public abstract <A, B> __<Ctor, X, B> fmap(F<A, B> fn, __<Ctor, X, A> nestedA);
    
}
