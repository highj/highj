/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import highj.LC;
import highj.RC;
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

    //first (Data.Bifunctor) curried
    public <A, B, C> F<__<Ctor, A, C>, __<Ctor, B, C>> first(F<A, B> fn);
            
    //second (Data.Bifunctor)
    public <A, B, C> __<Ctor, A, C> second(F<B, C> fn, __<Ctor, A, B> nestedAB);

    //second (Data.Bifunctor)
    public <A, B, C> F<__<Ctor, A, B>,__<Ctor, A, C>> second(F<B, C> fn);

    //functionality of first as a Functor (with right-curried argumets)   
    public <X> Functor<RC<Ctor, X>> getRCFunctor();

    //functionality of second as a Functor (with left-curried argumets)   
    public <X> Functor<LC<Ctor, X>> getLCFunctor();
}
