/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import highj.RC;
import highj._;
import highj.__;
import highj.typeclasses.category.FunctorAbstract;

/**
 * Takes care of right-currying the arguments when implementing a Functor 
 * for a binary type constructor
 * 
 * @author DGronau
 */
public abstract class RCFunctor<Ctor, X> extends FunctorAbstract<RC<Ctor,X>> {

    public abstract <A, B> __<Ctor, B, X> fmap(F<A, B> fn, __<Ctor, A, X> nestedA);

    @Override
    public <A, B> _<RC<Ctor, X>, B> fmap(F<A, B> fn, _<RC<Ctor, X>, A> nestedA) {
        return RC.curry(fmap(fn, RC.uncurry(nestedA)));
    }

    
}
