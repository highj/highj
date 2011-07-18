/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.P2;
import highj.RC;
import highj._;
import highj.typeclasses.category.Comonad;
import highj.typeclasses.category.ComonadAbstract;

/**
 *
 * @author dgronau
 */
public class PairRCComonad<X> extends ComonadAbstract<RC<PairOf,X>> implements Comonad<RC<PairOf,X>> {

    @Override
    public <A, B> _<RC<PairOf, X>, B> fmap(F<A, B> fn, _<RC<PairOf, X>, A> nestedA) {
        P2<A, X> pair = PairOf.unwrapRC(nestedA);
        return PairOf.wrapRC(pair.map1(fn));
    }

    @Override
    public <A> A extract(_<RC<PairOf, X>, A> nestedA) {
        P2<A, X> pair = PairOf.unwrapRC(nestedA);
        return pair._1();
    }

}