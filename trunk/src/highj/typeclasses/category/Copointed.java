/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 *
 * @author dgronau
 */
public interface  Copointed<Ctor> extends Functor<Ctor> {

    public <A> A extract(_<Ctor,A> nestedA);
    
    public <A> F<_<Ctor,A>, A> extract();
}
