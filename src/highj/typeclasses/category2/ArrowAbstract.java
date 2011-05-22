/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import fj.P;
import fj.P2;
import highj.__;

/**
 *
 * @author DGronau
 */
public abstract class ArrowAbstract<A> extends CategoryAbstract<A> implements Arrow<A> {

    @Override
    // arr  (Control.Arrow)
    public abstract <B, C> __<A, B, C> arr(F<B, C> fn);

    @Override
    // first  (Control.Arrow)
    public abstract <B, C, D> __<A, P2<B, D>, P2<C, D>> first(__<A, B, C> arrow);

    @Override
    // second  (Control.Arrow)
    public <B, C, D> __<A, P2<D, B>, P2<D, C>> second(__<A, B, C> arrow) {
        __<A,P2<D,B>, P2<B,D>> swapBack = arr(this.<D,B>swap());
        __<A,P2<B,D>, P2<C,D>> arrowFirst = first(arrow);
        __<A,P2<C,D>, P2<D,C>> swapForth = arr(this.<C,D>swap());
        return then(swapBack, then(arrowFirst,swapForth));
    }
    
    private <X,Y> F<P2<X,Y>,P2<Y,X>> swap(){
        return new F<P2<X,Y>,P2<Y,X>>(){
            @Override
            public P2<Y, X> f(P2<X, Y> pair) {
                return pair.swap();
            }
        };
    } 

    @Override
    // (***) (Control.Arrow)
    public <B, C, BB, CC> __<A, P2<B, BB>, P2<C, CC>> split(__<A, B, C> f, __<A, BB, CC> g) {
        __<A, P2<B,BB>, P2<C,BB>> firstF = first(f);
        __<A, P2<C,BB>, P2<C,CC>> secondG = second(g);
        return then(firstF, secondG);
    }

    @Override
    // (&&&) (Control.Arrow)
    public <B, C, CC> __<A, B, P2<C, CC>> fanout(__<A, B, C> f, __<A, B, CC> g) {
        __<A, B, P2<B,B>> duplicated = arr(new F<B,P2<B,B>>(){
            @Override
            public P2<B, B> f(B a) {
                return P.p(a, a);
            }
        });
        __<A, P2<B,B>, P2<C,CC>> splitted = split(f,g);
        return then(duplicated, splitted);
    }

    @Override
    // (>>>) (Control.Category, Control.Arrow)
    public <B, C, D> __<A, B, D> then(__<A, B, C> bc, __<A, C, D> cd) {
        return dot(cd, bc);
    }
    
}
