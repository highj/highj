/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import highj._;
import highj.__;

/**
 *
 * @author dgronau
 */
public class Cokleisli<T> {
    
    private Cokleisli() {
    }
    
    public static <M,A,B> __<Cokleisli<M>,A,B> wrap(F<_<M,A>,B> fn) {
        return new __<Cokleisli<M>,A,B>(new Cokleisli<M>(), fn);
    }
    
    public static <M,A,B> F<_<M,A>,B> unwrap(__<Cokleisli<M>,A,B> wrappedCokleisli) {
        return (F<_<M,A>,B>) wrappedCokleisli.read(new Cokleisli<M>());
    }

    public static <M,A,B> B apply(__<Cokleisli<M>,A,B> wrappedCokleisli, _<M,A> ma) {
        return unwrap(wrappedCokleisli).f(ma);
    }

}
