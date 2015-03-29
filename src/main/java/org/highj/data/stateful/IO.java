package org.highj.data.stateful;

import org.highj._;
import org.highj.data.functions.Functions;
import org.highj.data.stateful.io.IOMonad;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

public class IO<A> implements _<IO.µ, A> {

    public static final class µ {}

    @SuppressWarnings("unchecked")
    public static <A> IO<A> narrow(_<µ, A> value) {
        return (IO) value;
    }

    Supplier<A> thunk;

    public IO(A a) {
        thunk = Functions.constantF0(a);
    }

    public IO(Supplier<A> thunk) {
        this.thunk = thunk;
    }

    public <B> IO<B> map(Function<A, B> fn) {
        return new IO<>(() -> fn.apply(thunk.get()));
    }

    public <B> IO<B> ap(IO<Function<A, B>> fn) {
        return new IO<>(() -> fn.thunk.get().apply(this.thunk.get()));
    }

    public <B> IO<B> bind(Function<A, _<µ, B>> fn) {
        return narrow(fn.apply(thunk.get()));
    }


    public final static IOMonad monad = new IOMonad(){};
}
