package org.highj.data.continuations.cont;

import org.highj._;
import org.highj.__;
import org.highj.data.continuations.Cont;
import org.highj.data.functions.Functions;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.data.continuations.Cont.*;

/**
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public class ContMonad<S>  implements Monad<__.µ<Cont.µ, S>> {
    @Override
    public <A> Cont<S, A> pure(A a) {
        return new Cont<>(Functions.<A,S>flipApply().apply(a));
    }

    @Override
    public <A, B> Cont<S, B> ap(_<__.µ<Cont.µ, S>, Function<A, B>> fn, _<__.µ<Cont.µ, S>, A> nestedA) {
        return bind(fn, (Function<A, B> fn2) -> map(fn2, nestedA));
    }

    @Override
    public <A, B> Cont<S, B> bind(_<__.µ<Cont.µ, S>, A> nestedA, Function<A, _<__.µ<Cont.µ, S>, B>> fn) {
        //m >>= k  = Cont $ \c -> runCont m $ \a -> runCont (k a) c
        Function<Function<A,S>,S> fa = narrow(nestedA).runCont();
        Function<Function<B,S>,S> fb = c -> fa.apply(b -> narrow(fn.apply(b)).runCont().apply(c));
        return new Cont<>(fb);
    }

    @Override
    public <A, B> Cont<S, B> map(Function<A, B> fn, _<__.µ<Cont.µ, S>, A> nestedA) {
        // fmap f m = Cont $ \c -> runCont m (c . f)
        Function<Function<A,S>,S> fa = narrow(nestedA).runCont();
        Function<Function<B,S>,S> fb = c -> fa.apply(x -> c.apply(fn.apply(x)));
        return new Cont<>(fb);
    }
}