/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import highj.CR;
import highj.TC2;
import highj._;
import highj.__;
import highj.typeclasses.category.FunctorAbstract;

/**
 * Takes care of right-currying the arguments when implementing a Functor 
 * for a binary type constructor
 * 
 * @author DGronau
 */
public abstract class CRFunctor<Ctor extends TC2<Ctor>, X> extends FunctorAbstract<CR<Ctor,X>> {

    private final CR<Ctor,X> cr = new CR<Ctor, X>();
    
    @Override
    public <A, B> _<CR<Ctor, X>, B> fmap(F<A, B> fn, _<CR<Ctor, X>, A> nestedA) {
        return cr.curry(fmap(fn, cr.uncurry(nestedA)));
    }

    public abstract <A, B> __<Ctor, B, X> fmap(F<A, B> fn, __<Ctor, A, X> nestedA);
    
}
