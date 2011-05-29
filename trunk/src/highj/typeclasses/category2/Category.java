/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import highj.__;

/**
 *
 * @author DGronau
 */
public interface Category<A> extends Semigroupoid<A>{
    
    // id (Control.Category)
    public <B> __<A, B, B> id();

}
