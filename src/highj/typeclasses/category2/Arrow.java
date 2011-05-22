/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import fj.P2;
import highj.__;

/**
 *
 * @author DGronau
 */
public interface Arrow<A> extends Category<A> {
    
    // arr  (Control.Arrow)
    public <B,C> __<A,B,C> arr(F<B,C> fn);
        
    // first  (Control.Arrow)
    public <B,C,D> __<A, P2<B,D>, P2<C,D>> first(__<A,B,C> arrow);
    
    // second  (Control.Arrow)
    public <B,C,D> __<A, P2<D,B>, P2<D,C>> second(__<A,B,C> arrow);

    // (***) (Control.Arrow)
    public <B,C,BB,CC> __<A, P2<B,BB>, P2<C,CC>> split(__<A,B,C> arr1, __<A,BB,CC> arr2);
    
    // (&&&) (Control.Arrow)
    public <B,C, CC> __<A, B, P2<C,CC>> fanout(__<A,B,C> arr1, __<A,B,CC> arr2);

    // (>>>) (Control.Category, Control.Arrow)
    public <B, C, D> __<A, B, D> then(__<A, B, C> bc, __<A, C, D> cd);

}
