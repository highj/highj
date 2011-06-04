/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import fj.P2;
import highj.LC;
import highj.__;
import highj.typeclasses.category.Applicative;

/**
 *
 * @author DGronau
 */
public interface Arrow<Arr> extends Category<Arr> {
    
    // arr  (Control.Arrow)
    public <B,C> __<Arr,B,C> arr(F<B,C> fn);
        
    // first  (Control.Arrow)
    public <B,C,D> __<Arr, P2<B,D>, P2<C,D>> first(__<Arr,B,C> arrow);
    
    // second  (Control.Arrow)
    public <B,C,D> __<Arr, P2<D,B>, P2<D,C>> second(__<Arr,B,C> arrow);

    // (***) (Control.Arrow)
    public <B,C,BB,CC> __<Arr, P2<B,BB>, P2<C,CC>> split(__<Arr,B,C> arr1, __<Arr,BB,CC> arr2);
    
    // (&&&) (Control.Arrow)
    public <B,C, CC> __<Arr, B, P2<C,CC>> fanout(__<Arr,B,C> arr1, __<Arr,B,CC> arr2);

    //returnA (Control.Arrow)
    public <B> __<Arr, B, B> returnA();

    // (^>>) (Control.Arrow)
    public <B,C,D> F<__<Arr, C, D>, __<Arr, B, D>> precomposition(F<B, C> fn);
 
    // (^<<) (Control.Arrow)
    public <B,C,D> F<__<Arr, B, C>, __<Arr, B, D>> postcomposition(F<C, D> fn);
    
    //the Applicative instance for a left-curried Arrow
    public <X> Applicative<LC<Arr,X>> getApplicative();
}
