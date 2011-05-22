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
public interface PointedBounded<Ctor, Bound> extends FunctorBounded<Ctor, Bound> {
    // pure (Data.Pointed)
    public <A extends Bound> _<Ctor, A> pure(A a); 
}
