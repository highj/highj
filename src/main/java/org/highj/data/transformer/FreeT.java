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
import org.derive4j.hkt.__3;
import org.highj.data.Either;
import org.highj.data.transformer.free.*;
import org.highj.function.F1;
import org.highj.function.NF;
import org.highj.highjdata;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

@highjdata
public abstract class FreeT<F,M,A> implements __3<FreeT.µ,F,M,A> {
    public static class µ {}

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

    public <N> FreeT<F,N,A> hoist(NF<M,N> nm) {
        return bimap(NF.identity(), nm);
    }

    public <G> FreeT<G,M,A> interpret(NF<F,G> nf) {
        return bimap(nf, NF.identity());
    }

    public <G,N> FreeT<G,N,A> bimap(NF<F,G> nf, NF<M,N> nm) {
        return FreeTImpl
            .<F,M,A>cases()
            .liftF((__<F, A> f) -> FreeT.<G, N, A>liftF(nf.apply(f)))
            .liftM((__<M,A> m) -> FreeT.<G, N, A>liftM(nm.apply(m)))
            .done(FreeT::<G, N, A>done)
            .bind((Bound<F, M, ?, A> bound) -> bimapBound(nf, nm, bound))
            .suspend((Supplier<FreeT<F, M, A>> a) -> suspend(() -> a.get().bimap(nf, nm)))
            .apply(this);
    }

    private static <F,G,M,N,A,B> FreeT<G,N,B> bimapBound(NF<F,G> nf, NF<M,N> nm, Bound<F,M,A,B> bound) {
        return FreeTImpl
            .<F,M,A>cases()
            .liftF((__<F,A> f) -> FreeT.bind(liftF(nf.apply(f)), (A a) -> bound.f().apply(a).bimap(nf, nm)))
            .liftM((__<M,A> m) -> FreeT.bind(liftM(nm.apply(m)), (A a) -> bound.f().apply(a).bimap(nf, nm)))
            .done((A a) -> bound.f().apply(a).bimap(nf, nm))
            .bind((Bound<F,M,?,A> bound2) -> reassociateBind(bound2, bound.f()).bimap(nf, nm))
            .suspend((Supplier<FreeT<F,M,A>> a) -> suspend(() -> bind(a.get(), bound.f()).bimap(nf, nm)))
            .apply(bound.m());
    }

    public __<M,Either<A,__<F,FreeT<F,M,A>>>> resume(MonadRec<M> mMonadRec, Functor<F> fFunctor) {
        return mMonadRec.tailRec(
            FreeTImpl
                .<F, M, A>cases()
                .liftF((__<F, A> fa) -> mMonadRec.pure(Either.<FreeT<F, M, A>, Either<A, __<F, FreeT<F, M, A>>>>Right(Either.Right(fFunctor.map(FreeT::done, fa)))))
                .liftM((__<M, A> ma) -> mMonadRec.map((A a) -> Either.<FreeT<F, M, A>, Either<A, __<F, FreeT<F, M, A>>>>Right(Either.Left(a)), ma))
                .done((A a) -> mMonadRec.pure(Either.<FreeT<F,M,A>,Either<A, __<F, FreeT<F, M, A>>>>Right(Either.Left(a))))
                .bind((Bound<F, M, ?, A> bound) -> resumeBound(mMonadRec, fFunctor, bound))
                .suspend((Supplier<FreeT<F, M, A>> a) -> mMonadRec.pure(Either.Left(a.get()))),
            this
        );
    }

    private static <F,M,A,B> __<M,Either<FreeT<F,M,B>,Either<B,__<F,FreeT<F,M,B>>>>> resumeBound(Monad<M> mMonad, Functor<F> fFunctor, Bound<F,M,A,B> bound) {
        return FreeTImpl
            .<F,M,A>cases()
            .liftF((__<F, A> fa) -> mMonad.pure(Either.<FreeT<F, M, B>, Either<B, __<F, FreeT<F, M, B>>>>Right(Either.Right(fFunctor.map((A a) -> bound.f().apply(a), fa)))))
            .liftM((__<M, A> ma) -> mMonad.map((A a) -> Either.<FreeT<F,M,B>,Either<B,__<F,FreeT<F,M,B>>>>Left(bound.f().apply(a)), ma))
            .done((A a) -> mMonad.pure(Either.<FreeT<F, M, B>, Either<B, __<F, FreeT<F, M, B>>>>Left(bound.f().apply(a))))
            .bind((Bound<F, M, ?, A> bound2) -> mMonad.pure(Either.Left(reassociateBind(bound2, bound.f()))))
            .suspend((Supplier<FreeT<F,M,A>> a) -> mMonad.pure(Either.Left(bind(a.get(), bound.f()))))
            .apply(bound.m());
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
            .liftF((__<F, A> fa) ->
                    mMonad.map(
                        (A a) -> Either.<FreeT<F, M, B>, B>Left(bound.f().apply(a)),
                        interp.apply(fa)
                    )
            )
            .liftM((__<M, A> ma) ->
                    mMonad.map(
                        (A a) -> Either.<FreeT<F, M, B>, B>Left(bound.f().apply(a)),
                        ma
                    )
            )
            .done((A a) -> mMonad.pure(Either.<FreeT<F, M, B>, B>Left(bound.f().apply(a))))
            .bind((Bound<F, M, ?, A> bounds2) -> mMonad.pure(Either.Left(reassociateBind(bounds2, bound.f()))))
            .suspend((Supplier<FreeT<F, M, A>> a) -> mMonad.pure(Either.<FreeT<F, M, B>, B>Left(bind(a.get(), bound.f()))))
            .apply(bound.m());
    }

    public static <F,M> FreeTFunctor<F,M> functor() {
        return new FreeTFunctor<F,M>() {};
    }

    public static <F,M> FreeTApply<F,M> apply() {
        return new FreeTApply<F,M>() {};
    }

    public static <F,M> FreeTApplicative<F,M> applicative() {
        return new FreeTApplicative<F,M>() {};
    }

    public static <F,M> FreeTBind<F,M> bind() {
        return new FreeTBind<F,M>() {};
    }

    public static <F,M> FreeTMonad<F,M> monad() {
        return new FreeTMonad<F,M>() {};
    }

    public static <F,M> FreeTMonadTrans<F,M> monadTrans() {
        return new FreeTMonadTrans<F,M>() {};
    }
}
