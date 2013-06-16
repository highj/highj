package org.highj.data.kleisli;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.kleisli.kleisli.KleisliArrow;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class Kleisli<M, A, B> implements ___<Kleisli.µ, M, A, B>, Function<A, _<M, B>> {

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

    @SuppressWarnings("unchecked")
    public static <M, A, B> Kleisli<M, A, B> narrow(_<__.µ<___.µ<µ, M>, A>, B> nested) {
        return (Kleisli) nested;
    }

    public static <M> KleisliArrow<M> arrow(Monad<M> monad) {
        return new KleisliArrow<>(monad);
    }

}

