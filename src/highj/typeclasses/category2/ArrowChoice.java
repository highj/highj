/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.data.Either;
import highj.__;

/**
 *
 * @author DGronau
 */
public interface ArrowChoice<Arr> extends Arrow<Arr> {
    
    public <B,C,D> __<Arr, Either<B,D>, Either<C,D>> left(__<Arr, B, C> arr);

    public <B,C,D> __<Arr, Either<D,B>, Either<D,C>> right(__<Arr, B, C> arr);
    
    // (+++)
    public <B,C,BB,CC> __<Arr, Either<B,BB>, Either<C,CC>> merge(__<Arr,B,C> f, __<Arr,BB,CC> g); 

    // (|||)
    public <B,C,D> __<Arr, Either<B,C>, D> fanin(__<Arr,B,D> f, __<Arr,C,D> g);
    
}
