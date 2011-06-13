/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import highj.LC;
import highj.RC;
import highj._;
import highj.__;

/**
 *
 * @author DGronau
 */
public final class FunctionOf {

    private final static FunctionOf hidden = new FunctionOf();

    private FunctionOf() {
    }    
    
    public static <A, B> __<FunctionOf, A, B> wrap(F<A, B> fn) {
        return new __<FunctionOf, A, B>(hidden, fn);
    }

    public static <A, B> _<LC<FunctionOf, A>, B> wrapLC(F<A, B> fn) {
        return wrap(fn).leftCurry();
    }

    public static <A, B> _<RC<FunctionOf, B>, A> wrapRC(F<A, B> fn) {
        return wrap(fn).rightCurry();
    }
    
    public static <A, B> F<A, B> unwrap(__<FunctionOf, A, B> wrapped) {
        return (F<A, B>) wrapped.read(hidden);
    }

    public static <A, B> F<A, B> unwrapLC(_<LC<FunctionOf, A>, B> curried) {
        return unwrap(LC.uncurry(curried));
    }

    public static <A, B> F<A, B> unwrapRC(_<RC<FunctionOf, B>, A> curried) {
        return unwrap(RC.uncurry(curried));
    }

    public static <A, B> B apply(__<FunctionOf, A, B> wrapped, A a) {
        return unwrap(wrapped).f(a);
    }
    
}
