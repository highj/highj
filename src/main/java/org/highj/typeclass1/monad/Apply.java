package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

/**
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public interface Apply<M> extends Functor<M> {

    // <*> (Control.Applicative), ap (Control.Monad)
    public <A, B> _<M, B> ap(_<M, Function<A, B>> fn, _<M, A> nestedA);

    // <* (Control.Applicative)
    public default <A, B> _<M, A> leftSeq(_<M, A> nestedA, _<M, B> nestedB) {
        return lift2((A a) -> (B b) -> a).apply(nestedA).apply(nestedB);
    }

    // *> (Control.Applicative)
    public default <A, B> _<M, B> rightSeq(_<M, A> nestedA, _<M, B> nestedB) {
        return leftSeq(nestedB, nestedA);
    }

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public default <A, B, C> Function<_<M, A>, Function<_<M, B>, _<M, C>>> lift2(final Function<A, Function<B, C>> fn) {
        return a -> b -> ap(map(fn, a), b);
    }

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    public default <A, B, C, D> Function<_<M, A>, Function<_<M, B>, Function<_<M, C>, _<M, D>>>> lift3(final Function<A, Function<B, Function<C, D>>> fn) {
        return a -> b -> c -> ap(lift2(fn).apply(a).apply(b), c);
    }

    public default <A, B, C, D, E> Function<_<M, A>, Function<_<M, B>, Function<_<M, C>, Function<_<M, D>, _<M, E>>>>> lift4(Function<A, Function<B, Function<C, Function<D, E>>>> fn) {
        return (_<M, A> ma) -> (_<M, B> mb) -> (_<M, C> mc) -> (_<M, D> md) -> apply4(fn, ma, mb, mc, md);
    }

    public default <A, B, C, D, E, F> Function<_<M, A>, Function<_<M, B>, Function<_<M, C>, Function<_<M, D>, Function<_<M, E>, _<M, F>>>>>> lift5(Function<A, Function<B, Function<C, Function<D, Function<E, F>>>>> fn) {
        return (_<M, A> ma) -> (_<M, B> mb) -> (_<M, C> mc) -> (_<M, D> md) -> (_<M, E> me) -> apply5(fn, ma, mb, mc, md, me);
    }

    public default <A, B, C, D, E, F, G> Function<_<M, A>, Function<_<M, B>, Function<_<M, C>, Function<_<M, D>, Function<_<M, E>, Function<_<M, F>, _<M, G>>>>>>> lift6(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, G>>>>>> fn) {
        return (_<M, A> ma) -> (_<M, B> mb) -> (_<M, C> mc) -> (_<M, D> md) -> (_<M, E> me) -> (_<M, F> mf) -> apply6(fn, ma, mb, mc, md, me, mf);
    }

    public default <A, B, C, D, E, F, G, H> Function<_<M, A>, Function<_<M, B>, Function<_<M, C>, Function<_<M, D>, Function<_<M, E>, Function<_<M, F>, Function<_<M, G>, _<M, H>>>>>>>> lift7(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, H>>>>>>> fn) {
        return (_<M, A> ma) -> (_<M, B> mb) -> (_<M, C> mc) -> (_<M, D> md) -> (_<M, E> me) -> (_<M, F> mf) -> (_<M, G> mg) -> apply7(fn, ma, mb, mc, md, me, mf, mg);
    }

    public default <A, B, C, D, E, F, G, H, I> Function<_<M, A>, Function<_<M, B>, Function<_<M, C>, Function<_<M, D>, Function<_<M, E>, Function<_<M, F>, Function<_<M, G>, Function<_<M, H>, _<M, I>>>>>>>>> lift8(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, Function<H, I>>>>>>>> fn) {
        return (_<M, A> ma) -> (_<M, B> mb) -> (_<M, C> mc) -> (_<M, D> md) -> (_<M, E> me) -> (_<M, F> mf) -> (_<M, G> mg) -> (_<M, H> mh) -> apply8(fn, ma, mb, mc, md, me, mf, mg, mh);
    }

    public default <A, B, C> _<M, C> apply2(Function<A, Function<B, C>> fn, _<M, A> ma, _<M, B> mb) {
        return ap(map(fn, ma), mb);
    }

    public default <A, B, C, D> _<M, D> apply3(Function<A, Function<B, Function<C, D>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc) {
        return ap(apply2(fn, ma, mb), mc);
    }

    public default <A, B, C, D, E> _<M, E> apply4(Function<A, Function<B, Function<C, Function<D, E>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md) {
        return ap2(apply2(fn, ma, mb), mc, md);
    }

    public default <A, B, C, D, E, F> _<M, F> apply5(Function<A, Function<B, Function<C, Function<D, Function<E, F>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me) {
        return ap2(apply3(fn, ma, mb, mc), md, me);
    }

    public default <A, B, C, D, E, F, G> _<M, G> apply6(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, G>>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me, _<M, F> mf) {
        return ap3(apply3(fn, ma, mb, mc), md, me, mf);
    }

    public default <A, B, C, D, E, F, G, H> _<M, H> apply7(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, H>>>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me, _<M, F> mf, _<M, G> mg) {
        return ap3(apply4(fn, ma, mb, mc, md), me, mf, mg);
    }

    public default <A, B, C, D, E, F, G, H, I> _<M, I> apply8(Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, Function<H, I>>>>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me, _<M, F> mf, _<M, G> mg, _<M, H> mh) {
        return ap4(apply4(fn, ma, mb, mc, md), me, mf, mg, mh);
    }

    public default <A, B, C> _<M, C> ap2(_<M, Function<A, Function<B, C>>> fn, _<M, A> ma, _<M, B> mb) {
        return ap(ap(fn, ma), mb);
    }

    public default <A, B, C, D> _<M, D> ap3(_<M, Function<A, Function<B, Function<C, D>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc) {
        return ap(ap2(fn, ma, mb), mc);
    }

    public default <A, B, C, D, E> _<M, E> ap4(_<M, Function<A, Function<B, Function<C, Function<D, E>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md) {
        return ap2(ap2(fn, ma, mb), mc, md);
    }

    public default <A, B, C, D, E, F> _<M, F> ap5(_<M, Function<A, Function<B, Function<C, Function<D, Function<E, F>>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me) {
        return ap2(ap3(fn, ma, mb, mc), md, me);
    }

    public default <A, B, C, D, E, F, G> _<M, G> ap6(_<M, Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, G>>>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me, _<M, F> mf) {
        return ap3(ap3(fn, ma, mb, mc), md, me, mf);
    }

    public default <A, B, C, D, E, F, G, H> _<M, H> ap7(_<M, Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, H>>>>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me, _<M, F> mf, _<M, G> mg) {
        return ap3(ap4(fn, ma, mb, mc, md), me, mf, mg);
    }

    public default <A, B, C, D, E, F, G, H, I> _<M, I> ap8(_<M, Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, Function<G, Function<H, I>>>>>>>>> fn, _<M, A> ma, _<M, B> mb, _<M, C> mc, _<M, D> md, _<M, E> me, _<M, F> mf, _<M, G> mg, _<M, H> mh) {
        return ap4(ap4(fn, ma, mb, mc, md), me, mf, mg, mh);
    }
}
