package org.highj.data.kleisli;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.kleisli.kleisli.KleisliArrow;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class Kleisli<M, A, B> implements __3<Kleisli.µ, M, A, B>, Function<A, __<M, B>> {

    public static class µ {
    }

    private final Function<A, __<M, B>> fun;

    public Kleisli(Function<A, __<M, B>> fun) {
        this.fun = fun;
    }

    @Override
    public __<M, B> apply(A a) {
        return fun.apply(a);
    }

    @SuppressWarnings("unchecked")
    public static <M, A, B> Kleisli<M, A, B> narrow(__<__<__<µ, M>, A>, B> nested) {
        return (Kleisli) nested;
    }

    public static <M> KleisliArrow<M> arrow(Monad<M> monad) {
        return new KleisliArrow<>(monad);
    }
}

