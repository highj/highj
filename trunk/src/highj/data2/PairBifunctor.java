/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.P;
import fj.P2;
import highj.__;
import highj.typeclasses.category2.BifunctorAbstract;

/**
 *
 * @author DGronau
 */
public class PairBifunctor extends BifunctorAbstract<PairOf> {

    private static PairBifunctor INSTANCE = new PairBifunctor();

    @Override
    // bimap (Data.Bifunctor)
    public <A, B, C, D> __<PairOf, B, D> bimap(F<A, B> fn1, F<C, D> fn2, __<PairOf, A, C> nestedAC) {
        P2<A, C> pair = PairOf.unwrap(nestedAC);
        return PairOf.wrap(P.p(fn1.f(pair._1()), fn2.f(pair._2())));
    }

    public static PairBifunctor getInstance() {
        return INSTANCE;
    }
}
