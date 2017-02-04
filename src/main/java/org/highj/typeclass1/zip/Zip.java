package org.highj.typeclass1.zip;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.function.F3;
import org.highj.function.F4;
import org.highj.typeclass1.functor.Functor;

import java.util.function.BiFunction;

public interface Zip<F> {

    <A, B, C> __<F, C> zipWith(__<F, A> fa, __<F, B> fb, BiFunction<A, B, C> fn);

    default <A, B> __<F, T2<A, B>> zip(__<F, A> fa, __<F, B> fb) {
        return zipWith(fa, fb, T2::of);
    }

    default <A, B, C, D> __<F, D> zipWith(__<F, A> fa, __<F, B> fb, __<F, C> fc, F3<A, B, C, D> fn) {
        return zipWith(zip(fa, fb), fc, (t2, c) -> fn.apply(t2._1(), t2._2(), c));
    }

    default <A, B, C> __<F, T3<A, B, C>> zip(__<F, A> fa, __<F, B> fb, __<F, C> fc) {
        return zipWith(fa, fb, fc, T3::of);
    }

    default <A, B, C, D, E> __<F, E> zipWith(__<F, A> fa, __<F, B> fb, __<F, C> fc, __<F, D> fd, F4<A, B, C, D, E> fn) {
        return zipWith(zip(fa, fb, fc), fd, (t3, d) -> fn.apply(t3._1(), t3._2(), t3._3(), d));
    }

    default <A, B, C, D> __<F, T4<A, B, C, D>> zip(__<F, A> fa, __<F, B> fb, __<F, C> fc, __<F, D> fd) {
        return zipWith(fa, fb, fc, fd, T4::of);
    }

    static <F, A, B> T2<__<F, A>, __<F, B>> unzip2(Functor<F> functor, __<F, T2<A, B>> f) {
        return T2.of(functor.map(T2::_1, f), functor.map(T2::_2, f));
    }

    static <F, A, B, C> T3<__<F, A>, __<F, B>, __<F, C>> unzip3(Functor<F> functor, __<F, T3<A, B, C>> f) {
        return T3.of(functor.map(T3::_1, f), functor.map(T3::_2, f), functor.map(T3::_3, f));
    }

    static <F, A, B, C, D> T4<__<F, A>, __<F, B>, __<F, C>, __<F, D>> unzip4(Functor<F> functor, __<F, T4<A, B, C, D>> f) {
        return T4.of(functor.map(T4::_1, f), functor.map(T4::_2, f), functor.map(T4::_3, f), functor.map(T4::_4, f));
    }
}
