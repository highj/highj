/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.Function;
import highj.__;
import highj.typeclasses.category2.Category;
import highj.typeclasses.category2.CategoryAbstract;


/**
 *
 * @author DGronau
 */
public class FunctionCategory extends CategoryAbstract<FunctionOf> implements Category<FunctionOf> {

    @Override
    public <A> __<FunctionOf, A, A> id() {
        return FunctionOf.wrap(Function.<A>identity());
    }

    @Override
    public <A, B, C> __<FunctionOf, A, C> dot(final __<FunctionOf, B, C> bc, 
                                              final __<FunctionOf, A, B> ab) {
        return FunctionOf.wrap(new F<A,C>() {
            @Override
            public C f(A a) {
                return FunctionOf.apply(bc, FunctionOf.apply(ab, a));
            }
        });
    }
    
}
