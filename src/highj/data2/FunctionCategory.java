/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.Function;
import highj.__;
import highj.typeclasses.category2.Category;


/**
 *
 * @author DGronau
 */
public class FunctionCategory implements Category<FunctionOf> {

    @Override
    public <A> __<FunctionOf, A, A> id() {
        return FunctionOf.getInstance().wrap(Function.<A>identity());
    }

    @Override
    public <A, B, C> __<FunctionOf, A, C> dot(final __<FunctionOf, B, C> bc, 
                                              final __<FunctionOf, A, B> ab) {
        final FunctionOf functionOf = FunctionOf.getInstance();
        return functionOf.wrap(new F<A,C>() {
            @Override
            public C f(A a) {
                return functionOf.apply(bc, functionOf.apply(ab, a));
            }
        });
    }
    
}
