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
public interface Applicative<Ctor> extends Apply<Ctor> {
    
    // pure (Data.Pointed)
    public <A> _<Ctor, A> pure(A a);

   
}
