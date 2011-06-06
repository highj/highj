/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 * http://hackage.haskell.org/packages/archive/comonad/1.0.1/doc/html/Control-Comonad.html
 * 
 * @author DGronau
 */
public interface Extend<Ctor> extends Functor<Ctor> {
    
    public <A> _<Ctor, _<Ctor, A>> duplicate(_<Ctor, A> nestedA);
    
    public <A> F<_<Ctor, A>,_<Ctor, _<Ctor, A>>> duplicate();

    public <A, B> F<_<Ctor, A>, _<Ctor, B>> extend(F<_<Ctor, A>, B> fn);

    // (=>=) 
    public <A, B, C> F<_<Ctor, A>, C> cokleisli(F<_<Ctor, A>, B> f, F<_<Ctor, B>, C> g);
}
