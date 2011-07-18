/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.P;
import fj.P2;
import fj.Semigroup;
import highj.RC;
import highj._;
import highj.typeclasses.category.ApplyAbstract;

/**
 *
 * @author dgronau
 */
public class PairRCApply<X> extends ApplyAbstract<RC<PairOf, X>> {

    private final Semigroup<X> semigroup;

    public PairRCApply(Semigroup<X> semigroup) {
        this.semigroup = semigroup;
    }

    @Override
    public <A, B> _<RC<PairOf, X>, B> ap(_<RC<PairOf, X>, F<A, B>> fn, _<RC<PairOf, X>, A> nestedA) {
        P2<F<A, B>, X> pairFn = PairOf.unwrapRC(fn);
        P2<A, X> pairA = PairOf.unwrapRC(nestedA);
        P2<B, X> pairB = P.p(pairFn._1().f(pairA._1()), semigroup.sum(pairFn._2(), pairA._2()));
        return PairOf.wrapRC(pairB);
    }

    @Override
    public <A, B> _<RC<PairOf, X>, B> fmap(F<A, B> fn, _<RC<PairOf, X>, A> nestedA) {
        P2<A, X> pair = PairOf.unwrapRC(nestedA);
        return PairOf.wrapRC(pair.map1(fn));
    }
}