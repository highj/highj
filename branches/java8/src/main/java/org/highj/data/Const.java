package org.highj.data;

import org.highj._;
import org.highj.__;
import org.highj.function.F1;
import org.highj.typeclass.monad.Applicative;
import org.highj.typeclass.monad.ApplicativeAbstract;
import org.highj.typeclass.monad.Functor;
import org.highj.typeclass.monad.FunctorAbstract;
import org.highj.typeclass.group.Monoid;

public class Const<A,B> extends __<Const.µ,A,B> {

    private static final µ hidden = new µ();


    public static class µ {
        private µ() {
        }
    }

    private final A value;

    public Const(A value) {
        super(hidden);
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <A, B> Const<A, B> narrow(org.highj._<__.µ<µ, A>, B> value) {
        return (Const) value;
    }

    public A get() {
        return value;
    }

   public static <A,B> F1<A, Const<A,B>> Const(){
       return new F1<A,Const<A,B>>(){

           @Override
           public Const<A, B> $(A a) {
               return new Const<A,B>(a);
           }
       };
   }

   public static <S> Functor<__.µ<µ, S>> functor() {
       return  new FunctorAbstract<__.µ<µ, S>>(){

           @Override
           public <A, B> _<__.µ<µ, S>, B> map(F1<A, B> fn, _<__.µ<µ, S>, A> nestedA) {
               S s = narrow(nestedA).get();
               return new Const<S,B>(s);
           }
       };
   }

   public static <S> Applicative<__.µ<µ, S>>  applicative(final Monoid<S> monoid) {
       return new ApplicativeAbstract<__.µ<µ, S>>() {
           @Override
           public <A> _<__.µ<µ, S>, A> pure(A a) {
               return new Const<S,A>(monoid.identity());
           }

           @Override
           public <A, B> _<__.µ<µ, S>, B> ap(_<__.µ<µ, S>, F1<A, B>> fn, _<__.µ<µ, S>, A> nestedA) {
               S s1 = narrow(fn).get();
               S s2 = narrow(nestedA).get();
               return new Const<S,B>(monoid.dot(s1, s2));
           }

           @Override
           public <A, B> _<__.µ<µ, S>, B> map(F1<A, B> fn, _<__.µ<µ, S>, A> nestedA) {
               return Const.<S>functor().map(fn, nestedA);
           }
       };
   }
}
