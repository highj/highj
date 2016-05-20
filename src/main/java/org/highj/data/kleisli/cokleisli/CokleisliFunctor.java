package org.highj.data.kleisli.cokleisli;

import org.derive4j.hkt.__;
import org.highj.function.Functions;
import org.highj.data.kleisli.Cokleisli;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public class CokleisliFunctor<W, S> implements Functor<__<__<Cokleisli.µ, W>, S>> {


    @Override
    public <A, B> Cokleisli<W, S, B> map(Function<A, B> fn, __<__<__<Cokleisli.µ, W>, S>, A> nestedA) {
        Cokleisli<W, S, A> wsa = Cokleisli.narrow(nestedA);
        return new Cokleisli<>(Functions.compose(fn, wsa));
    }
}
