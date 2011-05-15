/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import highj.TC;
import highj._;
import highj.typeclasses.category.Pointed;

/**
 *
 * @author DGronau
 */
public abstract class PointedAbstract<Ctor extends TC<Ctor>> extends FunctorAbstract<Ctor> 
   implements Pointed<Ctor> {

    @Override
    public abstract <A> _<Ctor, A> pure(A a);
    
}
