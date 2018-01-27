package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

/**
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public interface Apply<M> extends Functor<M> {

    // <*> (Control.Applicative), ap (Control.Monad)
    <A, B> __<M, B> ap(__<M, Function<A, B>> fn, __<M, A> nestedA);

    // <* (Control.Applicative)
    default <A, B> __<M, A> leftSeq(__<M, A> nestedA, __<M, B> nestedB) {
        return lift2((A a) -> (B b) -> a).apply(nestedA).apply(nestedB);
    }

    // *> (Control.Applicative)
    default <A, B> __<M, B> rightSeq(__<M, A> nestedA, __<M, B> nestedB) {
        return lift2((A a) -> (B b) -> b).apply(nestedA).apply(nestedB);
    }

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    default <A, B, C> Function<__<M, A>, Function<__<M, B>, __<M, C>>> lift2(final Function<A, Function<B, C>> fn) {
        return a -> b -> ap(map(fn, a), b);
    }

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    default <A, B, C, D> Function<__<M, A>, Function<__<M, B>, Function<__<M, C>, __<M, D>>>> lift3(final Function<A, Function<B, Function<C, D>>> fn) {
        return a -> b -> c -> ap(lift2(fn).apply(a).apply(b), c);
    }

    default <A, B, C, D, E> Function<__<M, A>, Function<__<M, B>, Function<__<M, C>, Function<__<M, D>, __<M, E>>>>> lift4(Function<A, Function<B, Function<C, Function<D, E>>>> fn) {
        return (__<M, A> ma) -> (__<M, B> mb) -> (__<M, C> mc) -> (__<M, D> md) -> apply4(fn, ma, mb, mc, md);
    }

    default <A, B, C, D, E, F> Function<__<M, A>, Function<__<M, B>, Function<__<M, C>, Function<__<M, D>, Function<__<M, E>, __<M, F>>>>>> lift5(Function<A, Function<B, Function<C, Function<D, Function<E, F>>>>> fn) {
        return (__<M, A> ma) -> (__<M, B> mb) -> (__<M, C> mc) -> (__<M, D> md) -> (__<M, E> me) -> apply5(fn, ma, mb, mc, md, me);
    }

    default <A, B, C, D, E, F, G> Function<__<M, A>, Function<__<M, B>, Function<__<M, C>, Function<__<M, D>, Function<__<M, E>, Function<__<M, F>, __<M, G>>>>>>> lift6(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, G>>>>>> fn) {
        return (__<M, A> ma) -> (__<M, B> mb) -> (__<M, C> mc) -> (__<M, D> md) -> (__<M, E> me) -> (__<M, F> mf) -> apply6(fn, ma, mb, mc, md, me, mf);
    }

    default <A, B, C, D, E, F, G, H> Function<__<M, A>, Function<__<M, B>, Function<__<M, C>, Function<__<M, D>, Function<__<M, E>, Function<__<M, F>, Function<__<M, G>, __<M, H>>>>>>>> lift7(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, H>>>>>>> fn) {
        return (__<M, A> ma) -> (__<M, B> mb) -> (__<M, C> mc) -> (__<M, D> md) -> (__<M, E> me) -> (__<M, F> mf) -> (__<M, G> mg) -> apply7(fn, ma, mb, mc, md, me, mf, mg);
    }

    default <A, B, C, D, E, F, G, H, I> Function<__<M, A>, Function<__<M, B>, Function<__<M, C>, Function<__<M, D>, Function<__<M, E>, Function<__<M, F>, Function<__<M, G>, Function<__<M, H>, __<M, I>>>>>>>>> lift8(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, Function<H, I>>>>>>>> fn) {
        return (__<M, A> ma) -> (__<M, B> mb) -> (__<M, C> mc) -> (__<M, D> md) -> (__<M, E> me) -> (__<M, F> mf) -> (__<M, G> mg) -> (__<M, H> mh) -> apply8(fn, ma, mb, mc, md, me, mf, mg, mh);
    }

    default <A, B, C> __<M, C> apply2(Function<A, Function<B, C>> fn, __<M, A> ma, __<M, B> mb) {
        return ap(map(fn, ma), mb);
    }

    default <A, B, C, D> __<M, D> apply3(Function<A, Function<B, Function<C, D>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc) {
        return ap(apply2(fn, ma, mb), mc);
    }

    default <A, B, C, D, E> __<M, E> apply4(Function<A, Function<B, Function<C, Function<D, E>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md) {
        return ap2(apply2(fn, ma, mb), mc, md);
    }

    default <A, B, C, D, E, F> __<M, F> apply5(Function<A, Function<B, Function<C, Function<D, Function<E, F>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me) {
        return ap2(apply3(fn, ma, mb, mc), md, me);
    }

    default <A, B, C, D, E, F, G> __<M, G> apply6(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, G>>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me, __<M, F> mf) {
        return ap3(apply3(fn, ma, mb, mc), md, me, mf);
    }

    default <A, B, C, D, E, F, G, H> __<M, H> apply7(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, H>>>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me, __<M, F> mf, __<M, G> mg) {
        return ap3(apply4(fn, ma, mb, mc, md), me, mf, mg);
    }

    default <A, B, C, D, E, F, G, H, I> __<M, I> apply8(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, Function<H, I>>>>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me, __<M, F> mf, __<M, G> mg, __<M, H> mh) {
        return ap4(apply4(fn, ma, mb, mc, md), me, mf, mg, mh);
    }

    default <A, B, C> __<M, C> ap2(__<M, Function<A, Function<B, C>>> fn, __<M, A> ma, __<M, B> mb) {
        return ap(ap(fn, ma), mb);
    }

    default <A, B, C, D> __<M, D> ap3(__<M, Function<A, Function<B, Function<C, D>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc) {
        return ap(ap2(fn, ma, mb), mc);
    }

    default <A, B, C, D, E> __<M, E> ap4(__<M, Function<A, Function<B, Function<C, Function<D, E>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md) {
        return ap2(ap2(fn, ma, mb), mc, md);
    }

    default <A, B, C, D, E, F> __<M, F> ap5(__<M, Function<A, Function<B, Function<C, Function<D, Function<E, F>>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me) {
        return ap2(ap3(fn, ma, mb, mc), md, me);
    }

    default <A, B, C, D, E, F, G> __<M, G> ap6(__<M, Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, G>>>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me, __<M, F> mf) {
        return ap3(ap3(fn, ma, mb, mc), md, me, mf);
    }

    default <A, B, C, D, E, F, G, H> __<M, H> ap7(__<M, Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, H>>>>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me, __<M, F> mf, __<M, G> mg) {
        return ap3(ap4(fn, ma, mb, mc, md), me, mf, mg);
    }

    default <A, B, C, D, E, F, G, H, I> __<M, I> ap8(__<M, Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, Function<H, I>>>>>>>>> fn, __<M, A> ma, __<M, B> mb, __<M, C> mc, __<M, D> md, __<M, E> me, __<M, F> mf, __<M, G> mg, __<M, H> mh) {
        return ap4(ap4(fn, ma, mb, mc, md), me, mf, mg, mh);
    }
}
