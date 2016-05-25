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
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.function.NF;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

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

    public __<M,A> run(MonadRec<M> mMonadRec, NF<F,M> interp) {
        return mMonadRec.tailRec(
            FreeTImpl
                .<F,M,A>cases()
                .liftF((__<F, A> fa) -> mMonadRec.map(Either::<FreeT<F, M, A>, A>Right, interp.apply(fa)))
                .liftM((__<M, A> ma) -> mMonadRec.map(Either::<FreeT<F, M, A>, A>Right, ma))
                .done((A a) -> mMonadRec.pure(Either.<FreeT<F, M, A>, A>Right(a)))
                .bind((Bound<F, M, ?, A> bound) -> runBound(mMonadRec, interp, bound))
                .suspend((Supplier<FreeT<F, M, A>> a) -> mMonadRec.pure(Either.<FreeT<F, M, A>, A>Left(a.get()))),
            this
        );
    }

    private static <F,M,A,B> __<M,Either<FreeT<F,M,B>,B>> runBound(Monad<M> mMonad, NF<F,M> interp, Bound<F,M,A,B> bound) {
        return FreeTImpl
            .<F,M,A>cases()
            .liftF((__<F,A> fa) ->
                mMonad.map(
                    (A a) -> Either.<FreeT<F,M,B>,B>Left(bound.f().apply(a)),
                    interp.apply(fa)
                )
            )
            .liftM((__<M,A> ma) ->
                mMonad.map(
                    (A a) -> Either.<FreeT<F,M,B>,B>Left(bound.f().apply(a)),
                    ma
                )
            )
            .done((A a) -> mMonad.pure(Either.<FreeT<F,M,B>,B>Left(bound.f().apply(a))))
            .bind((Bound<F,M,?,A> bounds2) -> mMonad.pure(Either.Left(reassociateBind(bounds2, bound.f()))))
            .suspend((Supplier<FreeT<F,M,A>> a) -> mMonad.pure(Either.<FreeT<F,M,B>,B>Left(bind(a.get(), bound.f()))))
            .apply(bound.m());
    }
}
