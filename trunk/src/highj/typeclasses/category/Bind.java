/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Bind<Ctor> extends Apply<Ctor> {
    
        // (>>=) (Control.Monad)
    public <A,B> _<Ctor, B> bind(_<Ctor, A> nestedA, F<A, _<Ctor, B>> fn);
   
    // join (Control.Monad)
    public <A> _<Ctor, A> join(_<Ctor, _<Ctor, A>> nestedNestedA);

    // (>>) (Control.Monad)
    public <A, B> _<Ctor, B> semicolon(_<Ctor, A> nestedA, _<Ctor, B> nestedB);
 
    // >=> (Control.Monad)
    public <A, B, C> F<A, _<Ctor, C>> kleisli(F<A, _<Ctor, B>> fn, F<B, _<Ctor, C>> gn);
  
}
