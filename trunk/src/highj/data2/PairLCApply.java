/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.P;
import fj.P2;
import fj.Semigroup;
import highj.LC;
import highj._;
import highj.typeclasses.category.ApplyAbstract;

/**
 *
 * @author DGronau
 */
public class PairLCApply<X> extends ApplyAbstract<LC<PairOf, X>> {

    private final Semigroup<X> semigroup;

    public PairLCApply(Semigroup<X> semigroup) {
        this.semigroup = semigroup;
    }

    @Override
    public <A, B> _<LC<PairOf, X>, B> ap(_<LC<PairOf, X>, F<A, B>> fn, _<LC<PairOf, X>, A> nestedA) {
        P2<X, F<A, B>> pairFn = PairOf.unwrapLC(fn);
        P2<X, A> pairA = PairOf.unwrapLC(nestedA);
        P2<X, B> pairB = P.p(semigroup.sum(pairFn._1(), pairA._1()),
                pairFn._2().f(pairA._2()));
        return PairOf.wrapLC(pairB);
    }

    @Override
    public <A, B> _<LC<PairOf, X>, B> fmap(F<A, B> fn, _<LC<PairOf, X>, A> nestedA) {
        P2<X, A> pair = PairOf.unwrapLC(nestedA);
        return PairOf.wrapLC(pair.map2(fn));
    }
}
