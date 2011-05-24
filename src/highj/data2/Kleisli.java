/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import highj._;
import highj.__;

/**
 * Note that there should exist a Monad instance for M, which is needed to
 * create an Kleisli arrow.
 * 
 * @author dgronau
 */
public final class Kleisli<T> {
    
    private Kleisli() {
    }
    
    public static <M,A,B> __<Kleisli<M>,A,B> wrap(F<A,_<M,B>> fn) {
        return new __<Kleisli<M>,A,B>(new Kleisli<M>(), fn);
    }
    
    public static <M,A,B> F<A,_<M,B>> unwrap(__<Kleisli<M>,A,B> wrappedKleisli) {
        return (F<A,_<M,B>>) wrappedKleisli.read(new Kleisli<M>());
    }

    public static <M,A,B> _<M,B> apply(__<Kleisli<M>,A,B> wrappedKleisli, A a) {
        return unwrap(wrappedKleisli).f(a);
    }
    
}
