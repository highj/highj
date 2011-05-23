/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import fj.Function;
import fj.P;
import fj.P2;
import highj.CL;
import highj._;
import highj.__;
import highj.typeclasses.category.Applicative;
import highj.typeclasses.category.ApplicativeAbstract;

/**
 *
 * @author DGronau
 */
public abstract class ArrowAbstract<Arr> extends CategoryAbstract<Arr> implements Arrow<Arr> {

    @Override
    // arr  (Control.Arrow)
    public abstract <B, C> __<Arr, B, C> arr(F<B, C> fn);

    @Override
    // first  (Control.Arrow)
    public abstract <B, C, D> __<Arr, P2<B, D>, P2<C, D>> first(__<Arr, B, C> arrow);

    @Override
    // second  (Control.Arrow)
    public <B, C, D> __<Arr, P2<D, B>, P2<D, C>> second(__<Arr, B, C> arrow) {
        __<Arr, P2<D, B>, P2<B, D>> swapBack = arr(this.<D, B>swap());
        __<Arr, P2<B, D>, P2<C, D>> arrowFirst = first(arrow);
        __<Arr, P2<C, D>, P2<D, C>> swapForth = arr(this.<C, D>swap());
        return then(swapBack, then(arrowFirst, swapForth));
    }

    private <X, Y> F<P2<X, Y>, P2<Y, X>> swap() {
        return new F<P2<X, Y>, P2<Y, X>>() {

            @Override
            public P2<Y, X> f(P2<X, Y> pair) {
                return pair.swap();
            }
        };
    }

    @Override
    // (***) (Control.Arrow)
    public <B, C, BB, CC> __<Arr, P2<B, BB>, P2<C, CC>> split(__<Arr, B, C> f, __<Arr, BB, CC> g) {
        __<Arr, P2<B, BB>, P2<C, BB>> firstF = first(f);
        __<Arr, P2<C, BB>, P2<C, CC>> secondG = second(g);
        return then(firstF, secondG);
    }

    @Override
    // (&&&) (Control.Arrow)
    public <B, C, CC> __<Arr, B, P2<C, CC>> fanout(__<Arr, B, C> f, __<Arr, B, CC> g) {
        __<Arr, B, P2<B, B>> duplicated = arr(new F<B, P2<B, B>>() {

            @Override
            public P2<B, B> f(B a) {
                return P.p(a, a);
            }
        });
        __<Arr, P2<B, B>, P2<C, CC>> splitted = split(f, g);
        return then(duplicated, splitted);
    }

    @Override
    // (>>>) (Control.Category, Control.Arrow)
    public <B, C, D> __<Arr, B, D> then(__<Arr, B, C> bc, __<Arr, C, D> cd) {
        return dot(cd, bc);
    }

    @Override
    //the Applicative instance for a left-curried Arrow
    public <X> Applicative<CL<Arr, X>> getApplicative() {
        return new ApplicativeAbstract<CL<Arr, X>>() {

            @Override
            public <A, B> _<CL<Arr, X>, B> star(_<CL<Arr, X>, F<A, B>> fn, _<CL<Arr, X>, A> nestedA) {
                return CL.curry(then(fanout(CL.uncurry(fn), CL.uncurry(nestedA)), arr(
                        new F<P2<F<A, B>, A>, B>() {
                            @Override
                            public B f(P2<F<A, B>, A> pair) {
                                return pair._1().f(pair._2());
                            }
                        })));
            }

            @Override
            public <A> _<CL<Arr, X>, A> pure(A a) {
                return CL.curry(arr(Function.<X, A>constant(a)));
            }

            @Override
            public <A, B> _<CL<Arr, X>, B> fmap(F<A, B> fn, _<CL<Arr, X>, A> nestedA) {
                return CL.curry(then(CL.uncurry(nestedA), arr(fn)));
            }
        };
    }
    
}
