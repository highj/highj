package org.highj.data.kleisli.cokleisli;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.function.F1;
import org.highj.data.kleisli.Cokleisli;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass2.arrow.Arrow;

import java.util.function.Function;

public class CokleisliArrow<W> implements Arrow<__<Cokleisli.µ, W>> {

    private final Comonad<W> comonad;

    public CokleisliArrow(Comonad<W> comonad) {
        this.comonad = comonad;
    }

    @Override
    public <A, B> Cokleisli<W, A, B> arr(Function<A, B> fn) {
        //arr f = CoKleisli (f . extract)
        return new Cokleisli<W, A, B>(wa -> fn.apply(comonad.extract(wa)));
    }

    @Override
    public <A, B, C> Cokleisli<W, T2<A, C>, T2<B, C>> first(__2<__<Cokleisli.µ, W>, A, B> arrow) {
        //first a = a *** CoKleisli extract
        return split(arrow, new Cokleisli<W, C, C>(comonad::extract));
    }

    @Override
    public <A, B, C> Cokleisli<W, T2<C, A>, T2<C, B>> second(__2<__<Cokleisli.µ, W>, A, B> arrow) {
        //second a = CoKleisli extract *** a
        return split(new Cokleisli<W, C, C>(comonad::extract), arrow);
    }

    @Override
    public <A, B, AA, BB> Cokleisli<W, T2<A, AA>, T2<B, BB>> split(__2<__<Cokleisli.µ, W>, A, B> arr1, __2<__<Cokleisli.µ, W>, AA, BB> arr2) {
        //CoKleisli a *** CoKleisli b = CoKleisli (a . fmap fst &&& b . fmap snd)
        F1<__<W, A>, B> wab = Cokleisli.narrow(arr1)::apply;
        F1<__<W, AA>, BB> waabb = Cokleisli.narrow(arr2)::apply;
        F1<__<W, T2<A, AA>>, __<W, A>> fst = wp -> comonad.map(T2<A, AA>::_1, wp);
        F1<__<W, T2<A, AA>>, B> one = F1.arrow.<__<W, T2<A, AA>>, __<W, A>, B>dot(wab, fst);
        F1<__<W, T2<A, AA>>, __<W, AA>> snd = wp -> comonad.map(T2<A, AA>::_2, wp);
        F1<__<W, T2<A, AA>>, BB> two = F1.arrow.<__<W, T2<A, AA>>, __<W, AA>, BB>dot(waabb, snd);
        return new Cokleisli<W, T2<A, AA>, T2<B, BB>>(F1.arrow.<__<W, T2<A, AA>>, B, BB>fanout(one, two));
    }

    @Override
    public <A, B, C> Cokleisli<W, A, T2<B, C>> fanout(__2<__<Cokleisli.µ, W>, A, B> arr1, __2<__<Cokleisli.µ, W>, A, C> arr2) {
        //CoKleisli a &&& CoKleisli b = CoKleisli (a &&& b)
        F1<__<W, A>, B> wab = Cokleisli.narrow(arr1)::apply;
        F1<__<W, A>, C> wac = Cokleisli.narrow(arr2)::apply;
        return new Cokleisli<W, A, T2<B, C>>(F1.arrow.<__<W, A>, B, C>fanout(wab, wac));
    }

    @Override
    public <A> Cokleisli<W, A, A> identity() {
        //id = CoKleisli extract
        return new Cokleisli<W, A, A>(comonad::<A>extract);
    }

    @Override
    public <A, B, C> Cokleisli<W, A, C> dot(__2<__<Cokleisli.µ, W>, B, C> bc, __2<__<Cokleisli.µ, W>, A, B> ab) {
        //CoKleisli b . CoKleisli a = CoKleisli (b . fmap a . duplicate)
        Cokleisli<W, B, C> wbc = Cokleisli.narrow(bc);
        Cokleisli<W, A, B> wab = Cokleisli.narrow(ab);
        return new Cokleisli<W, A, C>(wa -> wbc.apply(comonad.map(wab, comonad.duplicate(wa))));
    }
}
