/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

public interface FunctorBounded<Ctor, Bound> {

    //fmap and <$> (Data.Functor)
    public <A extends Bound, B extends Bound> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);

    // <$  (Data.Functor)  
    public <A extends Bound, B extends Bound> _<Ctor, A> left$(A a, _<Ctor, B> nestedB);
    
    //liftA (Control.Aplicative), liftM (Control.Monad), curried version of fmap
    public <A extends Bound, B extends Bound> F<_<Ctor, A>, _<Ctor, B>> lift(F<A, B> fn);
    
}