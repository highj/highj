package org.highj.data.kleisli;

import org.highj._;
import org.highj.__;
import org.highj.data.kleisli.kleisli.KleisliArrow;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

public class Kleisli<M, A, B> implements __<_<Kleisli.µ, M>, A, B>, Function<A, _<M, B>>, Supplier<Function<A, _<M, B>>> {

    public static class µ {
    }

    private final Function<A, _<M, B>> fun;

    public Kleisli(Function<A, _<M, B>> fun) {
        this.fun = fun;
    }

    @Override
    public _<M, B> apply(A a) {
        return fun.apply(a);
    }

    @Override
    public Function<A, _<M, B>> get() {
        return fun;
    }

    @SuppressWarnings("unchecked")
    public static <M, A, B> Kleisli<M, A, B> narrow(__<_<Kleisli.µ, M>, A, B> nested) {
        return (Kleisli) nested;
    }

    //instance Monad m => Category (Kleisli m)
    public static <M> KleisliArrow<M> arrow(Monad<M> monad) {
        return new KleisliArrow<>(monad);
    }

}

