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
public interface Category<A> {
    
    // id (Control.Category)
    public <B> __<A, B, B> id();
    
    // (.) and (<<<) (Control.Category)
    public <B, C, D> __<A, B, D> dot(__<A, C, D> cd, __<A, B, C> bc);

}
