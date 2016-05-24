/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import java.util.function.Supplier;
import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Visibility;
import org.derive4j.hkt.__;
import org.highj.function.F1;

/**
 *
 * @author clintonselke
 */
@Data(@Derive(inClass = "FreeTImpl", withVisibility = Visibility.Package))
public abstract class FreeT<F,M,A> {
    
    public interface Cases<R,F,M,A> {
        R liftF(__<F,A> f);
        R liftM(__<M,A> m);
        R done(A done);
        R bind(Bound<F,M,?,A> bound);
        R suspend(Supplier<FreeT<F,M,A>> suspend);
    }
    
    public abstract <R> R match(Cases<R,F,M,A> cases);
    
    public static class Bound<F,M,A,B> {
        private final FreeT<F,M,A> m;
        private final F1<A,FreeT<F,M,B>> f;
        
        private Bound(FreeT<F,M,A> m, F1<A,FreeT<F,M,B>> f) {
            this.m = m;
            this.f = f;
        }
        
        public static <F,M,A,B> Bound<F,M,A,B> bound(FreeT<F,M,A> m, F1<A,FreeT<F,M,B>> f) {
            return new Bound<>(m, f);
        }
        
        public FreeT<F,M,A> m() {
            return m;
        }
        
        public F1<A,FreeT<F,M,B>> f() {
            return f;
        }
    }
    
    public static <F,M,A> FreeT<F,M,A> liftF(__<F,A> f) {
        return FreeTImpl.liftF(f);
    }
    
    public static <F,M,A> FreeT<F,M,A> liftM(__<M,A> m) {
        return FreeTImpl.liftM(m);
    }
    
    public static <F,M,A> FreeT<F,M,A> done(A a) {
        return FreeTImpl.done(a);
    }
    
    public static <F,M,A,B> FreeT<F,M,B> bind(FreeT<F,M,A> m, F1<A,FreeT<F,M,B>> f) {
        return FreeTImpl
            .<F,M,A>cases()
            .done(f)
            .bind((Bound<F,M,?,A> bound) -> reassociateBind(bound, f))
            .otherwise(() -> FreeTImpl.bind(Bound.bound(m, f)))
            .apply(m);
    }
    
    public static <F,M,A> FreeT<F,M,A> suspend(Supplier<FreeT<F,M,A>> a) {
        return FreeTImpl.suspend(a);
    }
    
    private static <F,M,A,B,C> FreeT<F,M,C> reassociateBind(Bound<F,M,A,B> bound, F1<B,FreeT<F,M,C>> f) {
        return suspend(() -> bind(bound.m(), (A a) -> bind(bound.f().apply(a), f)));
    }
}
