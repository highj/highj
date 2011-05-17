/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import highj.TC;
import highj._;

/**
 *
 * @author dgronau
 */
public interface  Copointed<Ctor extends TC<Ctor>> extends Functor<Ctor> {
    
    // copoint (Data.Copointed)
    public <A> A copoint(_<Ctor,A> nestedA);
}
