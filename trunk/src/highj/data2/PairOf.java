/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.P2;
import highj.LC;
import highj.RC;
import highj._;
import highj.__;

/**
 *
 * @author DGronau
 */
public final class PairOf {

    private final static PairOf hidden = new PairOf();

    private PairOf() {
    }
    
    public static <A, B> __<PairOf, A, B> wrap(P2<A, B> pair) {
        return new __<PairOf, A, B>(hidden, pair);
    }

    public static <A, B> _<LC<PairOf, A>, B> wrapLC(P2<A, B> pair) {
        return wrap(pair).leftCurry();
    }

    public static <A, B> _<RC<PairOf, B>, A> wrapRC(P2<A, B> pair) {
        return wrap(pair).rightCurry();
    }

    public  static<A, B> P2<A, B> unwrap(__<PairOf, A, B> wrapped) {
        return (P2<A, B>) wrapped.read(hidden);
    }
    
    public static <A, B> P2<A, B> unwrapLC(_<LC<PairOf, A>, B> curried) {
        return unwrap(LC.uncurry(curried));
    }

    public static <A, B> P2<A, B> unwrapRC(_<RC<PairOf, B>, A> curried) {
        return unwrap(RC.uncurry(curried));
    }        
    public static <A, B> A fst(__<PairOf, A, B> wrapped) {
        return unwrap(wrapped)._1();
    }

    public static <A, B> B snd(__<PairOf, A, B> wrapped) {
        return unwrap(wrapped)._2();
    }

}
