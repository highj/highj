/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.P2;
import highj.LC;
import highj._;
import highj.typeclasses.category.Comonad;
import highj.typeclasses.category.ComonadAbstract;

/**
 *
 * @author dgronau
 */
public class PairLCComonad<X> extends ComonadAbstract<LC<PairOf,X>> implements Comonad<LC<PairOf,X>> {

    @Override
    public <A, B> _<LC<PairOf, X>, B> fmap(F<A, B> fn, _<LC<PairOf, X>, A> nestedA) {
        P2<X, A> pair = PairOf.unwrapLC(nestedA);
        return PairOf.wrapLC(pair.map2(fn));
    }

    @Override
    public <A> A extract(_<LC<PairOf, X>, A> nestedA) {
        P2<X, A> pair = PairOf.unwrapLC(nestedA);
        return pair._2();
    }

}
