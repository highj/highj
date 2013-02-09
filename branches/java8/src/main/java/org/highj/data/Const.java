package org.highj.data;

import org.highj._;
import org.highj.__;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Functor;
import org.highj.typeclass0.group.Monoid;

import java.util.function.Function;

public class Const<A,B> implements __<Const.µ,A,B> {

    public static class µ { }

    private final A value;

    public Const(A value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <A, B> Const<A, B> narrow(org.highj._<__.µ<µ, A>, B> value) {
        return (Const) value;
    }

    public A get() {
        return value;
    }

   public static <S> Functor<__.µ<µ, S>> functor() {
       return new Functor<__.µ<µ, S>>(){

           @Override
           public <A, B> _<__.µ<µ, S>, B> map(Function<A, B> fn, _<__.µ<µ, S>, A> nestedA) {
               S s = narrow(nestedA).get();
               return new Const<>(s);
           }
       };
   }

   public static <S> Applicative<__.µ<µ, S>>  applicative(final Monoid<S> monoid) {
       return new Applicative<__.µ<µ, S>>() {
           @Override
           public <A> _<__.µ<µ, S>, A> pure(A a) {
               return new Const<>(monoid.identity());
           }

           @Override
           public <A, B> _<__.µ<µ, S>, B> ap(_<__.µ<µ, S>, Function<A, B>> fn, _<__.µ<µ, S>, A> nestedA) {
               S s1 = narrow(fn).get();
               S s2 = narrow(nestedA).get();
               return new Const<>(monoid.dot(s1, s2));
           }

           @Override
           public <A, B> _<__.µ<µ, S>, B> map(Function<A, B> fn, _<__.µ<µ, S>, A> nestedA) {
               return Const.<S>functor().map(fn, nestedA);
           }
       };
   }
}
