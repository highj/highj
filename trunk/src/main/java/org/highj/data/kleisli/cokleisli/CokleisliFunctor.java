package org.highj.data.kleisli.cokleisli;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.functions.Functions;
import org.highj.data.kleisli.Cokleisli;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public class CokleisliFunctor<W, S> implements Functor<__.µ<___.µ<Cokleisli.µ, W>, S>> {


    @Override
    public <A, B> Cokleisli<W, S, B> map(Function<A, B> fn, _<__.µ<___.µ<Cokleisli.µ, W>, S>, A> nestedA) {
        Cokleisli<W, S, A> wsa = Cokleisli.narrow(nestedA);
        return new Cokleisli<>(Functions.compose(fn, wsa));
    }
}
