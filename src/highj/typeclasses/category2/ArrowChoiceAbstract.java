/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import fj.data.Either;
import highj.__;

/**
 *
 * @author DGronau
 */
public abstract class ArrowChoiceAbstract<Arr> extends ArrowAbstract<Arr> implements ArrowChoice<Arr> {


    @Override
    public abstract <B, C, D> __<Arr, Either<B, D>, Either<C, D>> left(__<Arr, B, C> arr);

    @Override
    public <B, C, D> __<Arr, Either<D, B>, Either<D, C>> right(__<Arr, B, C> f) {
        __<Arr, Either<D, B>, Either<B, D>> mirror1 = mirror();
        __<Arr, Either<B, D>, Either<C, D>> leftArr = left(f);
        __<Arr, Either<C, D>, Either<D, C>> mirror2 = mirror();
        return then(mirror1, leftArr, mirror2);
    }
    
    private <P,Q> __<Arr, Either<P,Q>, Either<Q,P>> mirror() {
        return arr(new F<Either<P,Q>,Either<Q,P>>() {
            @Override
            public Either<Q, P> f(Either<P, Q> either) {
                return either.swap();
            }
        });
    }

    @Override
    // (+++)
    public <B, C, BB, CC> __<Arr, Either<B, BB>, Either<C, CC>> merge(__<Arr, B, C> f, __<Arr, BB, CC> g) {
        __<Arr, Either<B, BB>, Either<C,BB>> leftF = left(f);
        __<Arr, Either<C, BB>, Either<C,CC>> rightG = right(g);
        return then(leftF, rightG);
    }

    @Override
    // (|||)
    public <B, C, D> __<Arr, Either<B, C>, D> fanin(__<Arr, B, D> f, __<Arr, C, D> g) {
        __<Arr, Either<B, C>, Either<D, D>> mergeFG = merge(f,g);
        __<Arr, Either<D, D>, D> untag = arr(new F<Either<D, D>, D>(){
            @Override
            public D f(Either<D, D> either) {
                return Either.reduce(either);
            }
        });
        return then(mergeFG, untag);
    }
    
}
