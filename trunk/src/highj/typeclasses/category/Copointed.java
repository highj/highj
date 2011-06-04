/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 * TODO: deprecate, use Extend from
 * http://hackage.haskell.org/packages/archive/comonad/1.1.0/doc/html/Data-Functor-Extend.html
 * @author dgronau
 */
public interface  Copointed<Ctor> extends Functor<Ctor> {

    public <A> A extract(_<Ctor,A> nestedA);
    
    public <A> F<_<Ctor,A>, A> extract();
}
