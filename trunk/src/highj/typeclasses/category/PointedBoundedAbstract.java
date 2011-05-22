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
public abstract class PointedBoundedAbstract<Ctor, Bound> extends FunctorBoundedAbstract<Ctor, Bound> 
    implements PointedBounded<Ctor, Bound> {

    @Override
    public abstract <A extends Bound> _<Ctor, A> pure(A a);
    
}
