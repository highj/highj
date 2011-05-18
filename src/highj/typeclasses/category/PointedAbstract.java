/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import highj._;

/**
 *
 * @author DGronau
 */
public abstract class PointedAbstract<Ctor> extends FunctorAbstract<Ctor> 
   implements Pointed<Ctor> {

    @Override
    public abstract <A> _<Ctor, A> pure(A a);
    
}
