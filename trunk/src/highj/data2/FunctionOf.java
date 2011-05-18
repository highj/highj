/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import highj.CL;
import highj.CR;
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

    public static <A, B> _<CL<FunctionOf, A>, B> wrapCL(F<A, B> fn) {
        return CL.curry(wrap(fn));
    }

    public static <A, B> _<CR<FunctionOf, B>, A> wrapCR(F<A, B> fn) {
        return CR.curry(wrap(fn));
    }
    
    public static <A, B> F<A, B> unwrap(__<FunctionOf, A, B> wrapped) {
        return (F<A, B>) wrapped.read(hidden);
    }

    public static <A, B> F<A, B> unwrapCL(_<CL<FunctionOf, A>, B> curried) {
        return unwrap(CL.uncurry(curried));
    }

    public static <A, B> F<A, B> unwrapCR(_<CR<FunctionOf, B>, A> curried) {
        return unwrap(CR.uncurry(curried));
    }

    public static <A, B> B apply(__<FunctionOf, A, B> wrapped, A a) {
        return unwrap(wrapped).f(a);
    }
    
}
