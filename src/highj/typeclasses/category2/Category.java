/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import highj.TC2;
import highj.__;

/**
 *
 * @author DGronau
 */
public interface Category<Ctor extends TC2<Ctor>> {
    
    // id (Control.Category)
    public <A> __<Ctor, A, A> id();
    
    // (.) (Control.Category)
    public <A, B, C> __<Ctor, A, C> dot(__<Ctor, B, C> bc, __<Ctor, A, B> ab);

}
