/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import highj.LC;
import highj._;
import highj.__;
import highj.typeclasses.category.FunctorAbstract;

/**
 * Takes care of left-currying the arguments when implementing a Functor 
 * for a binary type constructor
 * 
 * @author DGronau
 */
public abstract class LCFunctor<Ctor, X> extends FunctorAbstract<LC<Ctor, X>> {

    public abstract <A, B> __<Ctor, X, B> fmap(F<A, B> fn, __<Ctor, X, A> nestedA);

    @Override
    public <A, B> _<LC<Ctor, X>, B> fmap(F<A, B> fn, _<LC<Ctor, X>, A> nestedA) {
        return LC.curry(fmap(fn, LC.uncurry(nestedA)));
    }
}
