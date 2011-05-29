/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.Function;
import fj.P;
import fj.P2;
import highj.__;
import highj.typeclasses.category2.Arrow;
import highj.typeclasses.category2.ArrowAbstract;


/**
 *
 * @author DGronau
 */
public class FunctionArrow extends ArrowAbstract<FunctionOf> implements Arrow<FunctionOf> {

    @Override
    public <B> __<FunctionOf, B, B> id() {
        return FunctionOf.wrap(Function.<B>identity());
    }

    @Override
    public <B, C, D> __<FunctionOf, B, D> o(final __<FunctionOf, C, D> cd, 
                                              final __<FunctionOf, B, C> bc) {
        return FunctionOf.wrap(new F<B,D>() {
            @Override
            public D f(B b) {
                return FunctionOf.apply(cd, FunctionOf.apply(bc, b));
            }
        });
    }

    @Override
    public <B, C> __<FunctionOf, B, C> arr(F<B, C> fn) {
        return FunctionOf.wrap(fn);
    }

    @Override
    public <B, C, D> __<FunctionOf, P2<B, D>, P2<C, D>> first(final __<FunctionOf, B, C> arrow) {
        return FunctionOf.wrap(new F<P2<B, D>, P2<C, D>>(){
            @Override
            public P2<C, D> f(P2<B, D> pair) {
                return P.p(FunctionOf.apply(arrow, pair._1()), pair._2());
            }
        });
    }
    
    @Override
    //overwriting ArrowAbstract implementation for better performance
    public <B,C,D> __<FunctionOf, P2<D,B>, P2<D,C>> second(final __<FunctionOf,B,C> arrow) {
        return FunctionOf.wrap(new F<P2<D, B>, P2<D, C>>(){
            @Override
            public P2<D, C> f(P2<D, B> pair) {
                return P.p( pair._1(), FunctionOf.apply(arrow, pair._2()));
            }
        });
    }
    
    private static final Arrow<FunctionOf> INSTANCE = new FunctionArrow();
    
    public static Arrow<FunctionOf> getInstance() {
        return INSTANCE;
    }
    
}
