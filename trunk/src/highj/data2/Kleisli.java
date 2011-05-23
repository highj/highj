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
public final class Kleisli<T> {
    
    private Kleisli() {
    }
    
    public static <A,B,M> __<Kleisli<M>,A,B> wrap(F<A,_<M,B>> fn) {
        return new __<Kleisli<M>,A,B>(new Kleisli<M>(), fn);
    }
    
    public static <A,B,M> F<A,_<M,B>> unwrap(__<Kleisli<M>,A,B> wrappedKleisli) {
        return (F<A,_<M,B>>) wrappedKleisli.read(new Kleisli<M>());
    }

    public static <A,B,M> _<M,B> apply(__<Kleisli<M>,A,B> wrappedKleisli, A a) {
        return unwrap(wrappedKleisli).f(a);
    }
    
}
