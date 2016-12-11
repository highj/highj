package org.highj.control.arrow.cokleisli;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.control.arrow.Cokleisli;
import org.highj.function.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass2.arrow.Arrow;

import java.util.function.Function;

import static org.highj.Hkt.asCokleisli;

public interface CokleisliArrow<W> extends Arrow<__<Cokleisli.µ, W>> {

    Comonad<W> getW();

    @Override
    default <A, B> Cokleisli<W, A, B> arr(Function<A, B> fn) {
        return new Cokleisli<W, A, B>(wa -> fn.apply(getW().extract(wa)));
    }

    @Override
    default <A, B, C> Cokleisli<W, T2<A, C>, T2<B, C>> first(__2<__<Cokleisli.µ, W>, A, B> arrow) {
        return split(arrow, new Cokleisli<W, C, C>(getW()::extract));
    }

    @Override
    default <A, B, C> Cokleisli<W, T2<C, A>, T2<C, B>> second(__2<__<Cokleisli.µ, W>, A, B> arrow) {
        return split(new Cokleisli<W, C, C>(getW()::extract), arrow);
    }

    @Override
    default <A, B, AA, BB> Cokleisli<W, T2<A, AA>, T2<B, BB>> split(__2<__<Cokleisli.µ, W>, A, B> arr1, __2<__<Cokleisli.µ, W>, AA, BB> arr2) {
        F1<__<W, A>, B> wab = asCokleisli(arr1)::apply;
        F1<__<W, AA>, BB> waabb = asCokleisli(arr2)::apply;
        F1<__<W, T2<A, AA>>, __<W, A>> fst = wp -> getW().map(T2<A, AA>::_1, wp);
        F1<__<W, T2<A, AA>>, B> one = F1.arrow.<__<W, T2<A, AA>>, __<W, A>, B>dot(wab, fst);
        F1<__<W, T2<A, AA>>, __<W, AA>> snd = wp -> getW().map(T2<A, AA>::_2, wp);
        F1<__<W, T2<A, AA>>, BB> two = F1.arrow.<__<W, T2<A, AA>>, __<W, AA>, BB>dot(waabb, snd);
        return new Cokleisli<W, T2<A, AA>, T2<B, BB>>(F1.arrow.<__<W, T2<A, AA>>, B, BB>fanout(one, two));
    }

    @Override
    default <A, B, C> Cokleisli<W, A, T2<B, C>> fanout(__2<__<Cokleisli.µ, W>, A, B> arr1, __2<__<Cokleisli.µ, W>, A, C> arr2) {
        F1<__<W, A>, B> wab = asCokleisli(arr1)::apply;
        F1<__<W, A>, C> wac = asCokleisli(arr2)::apply;
        return new Cokleisli<W, A, T2<B, C>>(F1.arrow.<__<W, A>, B, C>fanout(wab, wac));
    }

    @Override
    default <A> Cokleisli<W, A, A> identity() {
        return new Cokleisli<W, A, A>(getW()::<A>extract);
    }

    @Override
    default <A, B, C> Cokleisli<W, A, C> dot(__2<__<Cokleisli.µ, W>, B, C> bc, __2<__<Cokleisli.µ, W>, A, B> ab) {
        Cokleisli<W, B, C> wbc = asCokleisli(bc);
        Cokleisli<W, A, B> wab = asCokleisli(ab);
        return new Cokleisli<W, A, C>(wa -> wbc.apply(getW().map(wab, getW().duplicate(wa))));
    }
}
