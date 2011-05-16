/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import highj.__;
import highj.typeclasses.category2.CLFunctor;

/**
 *
 * @author DGronau
 */
public class FunctionFunctor<X> extends CLFunctor<FunctionOf, X> {

    @Override
    public <A, B> __<FunctionOf, X, B> fmap(final F<A, B> fn, final __<FunctionOf, X, A> nestedA) {
        final FunctionOf functionOf = FunctionOf.getInstance();
        return functionOf.wrap(new F<X,B>(){
            @Override
            public B f(X x) {
                return fn.f(functionOf.apply(nestedA, x));
            }
        });
    }
    
}
