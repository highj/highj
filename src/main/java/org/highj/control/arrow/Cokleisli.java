package org.highj.control.arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.control.arrow.cokleisli.CokleisliArrow;
import org.highj.control.arrow.cokleisli.CokleisliFunctor;
import org.highj.typeclass1.comonad.Comonad;

import java.util.function.Function;

public class Cokleisli<W, A, B> implements __3<Cokleisli.µ, W, A, B>, Function<__<W, A>, B> {
    public interface µ {
    }

    private final Function<__<W, A>, B> fn;

    public Cokleisli(Function<__<W, A>, B> fn) {
        this.fn = fn;
    }

    @Override
    public B apply(__<W, A> a) {
        return fn.apply(a);
    }

    @SuppressWarnings("unchecked")
    public static <W, A, B> Cokleisli<W, A, B> narrow(__<__<__<µ, W>, A>, B> nested) {
        return (Cokleisli) nested;
    }

    public static <W> CokleisliArrow<W> arrow(Comonad<W> wComonad) {
        return () -> wComonad;
    }

    public static <W, S> CokleisliFunctor<W, S> functor() {
        return new CokleisliFunctor<W,S>(){};
    }

}
