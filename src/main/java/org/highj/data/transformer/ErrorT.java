/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.collection.Either;
import org.highj.data.transformer.error.ErrorTApplicative;
import org.highj.data.transformer.error.ErrorTApply;
import org.highj.data.transformer.error.ErrorTBind;
import org.highj.data.transformer.error.ErrorTFunctor;
import org.highj.data.transformer.error.ErrorTMonad;
import org.highj.data.transformer.error.ErrorTMonadError;
import org.highj.data.transformer.error.ErrorTMonadRec;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

/**
 *
 * @author clintonselke
 */
public interface ErrorT<E,M,A> extends __3<ErrorT.µ,E,M,A> {
    public static class µ {}
    
    public static <E,M,A> ErrorT<E,M,A> narrow(__3<µ,E,M,A> a) {
        return (ErrorT<E,M,A>)a;
    }
    
    public static <E,M,A> ErrorT<E,M,A> narrow(__<__<__<µ, E>, M>, A> a) {
        return (ErrorT<E,M,A>)a;
    }
    
    public __<M,Either<E,A>> run();
    
    public static <E,M> ErrorTFunctor<E,M> functor(Functor<M> mFunctor) {
        return (ErrorTFunctor<E,M>)() -> mFunctor;
    }
    
    public static <E,M> ErrorTApply<E,M> apply(Apply<M> mApply) {
        return (ErrorTApply<E,M>)() -> mApply;
    }
    
    public static <E,M> ErrorTApplicative<E,M> applicative(Applicative<M> mApplicative) {
        return (ErrorTApplicative<E,M>)() -> mApplicative;
    }
    
    public static <E,M> ErrorTBind<E,M> bind(Monad<M> mMonad) {
        return (ErrorTBind<E,M>)() -> mMonad;
    }
    
    public static <E,M> ErrorTMonad<E,M> monad(Monad<M> mMonad) {
        return (ErrorTMonad<E,M>)() -> mMonad;
    }
    
    public static <E,M> ErrorTMonadError<E,M> monadError(Monad<M> mMonad) {
        return (ErrorTMonadError<E,M>)() -> mMonad;
    }
    
    public static <E,M> ErrorTMonadRec<E,M> monadRec(MonadRec<M> mMonadRec) {
        return (ErrorTMonadRec<E,M>)() -> mMonadRec;
    }
}
