/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import highj.CL;
import highj.CR;
import highj.__;
import highj.typeclasses.category.Functor;

/**
 *
 * @author DGronau
 */
public interface Bifunctor<Ctor> {

    // bimap (Data.Bifunctor)
    public <A, B, C, D> __<Ctor, B, D> bimap(F<A, B> fn1, F<C, D> fn2, __<Ctor, A, C> nestedAC);
    
    //first (Data.Bifunctor)
    public <A, B, C> __<Ctor, B, C> first(F<A, B> fn, __<Ctor, A, C> nestedAC);
            
    //second (Data.Bifunctor)
    public <A, B, C> __<Ctor, A, C> second(F<B, C> fn, __<Ctor, A, B> nestedAB);

    //functionality of first as a Functor (with right-curried argumets)   
    public <X> Functor<CR<Ctor, X>> getCRFunctor();

    //functionality of second as a Functor (with left-curried argumets)   
    public <X> Functor<CL<Ctor, X>> getCLFunctor();
}
