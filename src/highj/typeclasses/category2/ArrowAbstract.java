/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import fj.Function;
import fj.P;
import fj.P2;
import highj.LC;
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
    //This is only a fallback method. Please override for better performance.
    public <B, C, D> __<Arr, P2<D, B>, P2<D, C>> second(__<Arr, B, C> arrow) {
        __<Arr, P2<D, B>, P2<B, D>> swapForth = arr(this.<D, B>swap());
        __<Arr, P2<B, D>, P2<C, D>> arrowFirst = first(arrow);
        __<Arr, P2<C, D>, P2<D, C>> swapBack = arr(this.<C, D>swap());
        return then(swapForth, arrowFirst, swapBack);
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

    //returnA (Control.Arrow)
    @Override
    public <B> __<Arr, B, B> returnA() {
        return arr(Function.<B>identity());
    }

    // (^>>) (Control.Arrow)
    @Override
    public <B, C, D> F<__<Arr, C, D>, __<Arr, B, D>> precomposition(final F<B, C> fn) {
        //(^>>) :: Arrow a => (b -> c) -> a c d -> a b d
        //f ^>> a = arr f >>> a
        return new F<__<Arr, C, D>, __<Arr, B, D>>() {
            private __<Arr, B, C> arrow = arr(fn);

            @Override
            public __<Arr, B, D> f(__<Arr, C, D> a) {
                return then(arrow, a);
            }
        };
    }

    // (^<<) (Control.Arrow)
    @Override
    public <B, C, D> F<__<Arr, B, C>, __<Arr, B, D>> postcomposition(final F<C, D> fn) {
        //(^<<) :: Arrow a => (c -> d) -> a b c -> a b d
        //f ^<< a = arr f <<< a 
        return new F<__<Arr, B, C>, __<Arr, B, D>>() {
            private __<Arr, C, D> arrow = arr(fn);

            @Override
            public __<Arr, B, D> f(__<Arr, B, C> a) {
                return then(a, arrow);
            }
        };
    }

    @Override
    //the Applicative instance for a left-curried Arrow
    public <X> Applicative<LC<Arr, X>> getApplicative() {
        return new ApplicativeAbstract<LC<Arr, X>>() {
            @Override
            public <A, B> _<LC<Arr, X>, B> ap(_<LC<Arr, X>, F<A, B>> fn, _<LC<Arr, X>, A> nestedA) {
                return then(fanout(LC.uncurry(fn), LC.uncurry(nestedA)), arr(
                        new F<P2<F<A, B>, A>, B>() {
                            @Override
                            public B f(P2<F<A, B>, A> pair) {
                                return pair._1().f(pair._2());
                            }
                        })).leftCurry();
            }

            @Override
            public <A> _<LC<Arr, X>, A> pure(A a) {
                return arr(Function.<X, A>constant(a)).leftCurry();
            }

            @Override
            public <A, B> _<LC<Arr, X>, B> fmap(F<A, B> fn, _<LC<Arr, X>, A> nestedA) {
                return then(LC.uncurry(nestedA), arr(fn)).leftCurry();
            }
        };
    }
}
