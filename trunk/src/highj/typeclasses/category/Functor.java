/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Unit;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Functor<Ctor> extends FunctorBounded<Ctor, Object> {

    //fmap and <$> (Data.Functor)
    @Override
    public <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);

    // <$  (Data.Functor)  
    @Override
    public <A, B> _<Ctor, A> left$(A a, _<Ctor, B> nestedB);
    
    //void (Control.Monad)
    public <A> _<Ctor, Unit> voidF(_<Ctor, A> nestedA);
    
    //flip (Data.Functor.Syntax)
    public <A, B> _<Ctor, B> flip(_<Ctor, F<A, B>> nestedFn, A a);

    @Override
    //liftA (Control.Aplicative), liftM (Control.Monad), curried version of fmap
    public <A, B> F<_<Ctor, A>, _<Ctor, B>> lift(F<A, B> fn);
    
}
