package org.highj.control.arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.control.arrow.cokleisli.CokleisliArrow;
import org.highj.control.arrow.cokleisli.CokleisliFunctor;
import org.highj.control.arrow.cokleisli.CokleisliProfunctor;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass1.functor.Functor;

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

    public static <W> CokleisliArrow<W> arrow(Comonad<W> comonad) {
        return () -> comonad;
    }

    public static <W, S> CokleisliFunctor<W, S> functor() {
        return new CokleisliFunctor<W, S>() {
        };
    }

    public static <W> CokleisliProfunctor<W> profunctor(Functor<W> functor) {
        return () -> functor;
    }

}
