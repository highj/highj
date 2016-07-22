package org.highj.control.arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.control.arrow.kleisli.KleisliArrow;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class Kleisli<M, A, B> implements __3<Kleisli.µ, M, A, B>, Function<A, __<M, B>> {

    public interface µ {
    }

    private final Function<A, __<M, B>> fn;

    public Kleisli(Function<A, __<M, B>> fn) {
        this.fn = fn;
    }

    @Override
    public __<M, B> apply(A a) {
        return fn.apply(a);
    }

    @SuppressWarnings("unchecked")
    public static <M, A, B> Kleisli<M, A, B> narrow(__<__<__<µ, M>, A>, B> nested) {
        return (Kleisli) nested;
    }

    public static <M> KleisliArrow<M> arrow(Monad<M> mMonad) {
        return () -> mMonad;
    }
}

