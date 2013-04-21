package org.highj.data;

import org.highj._;
import org.highj.function.Functions;
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

    private IO(A a) {
        thunk = Functions.constantF0(a);
    }

    private IO(Supplier<A> thunk) {
        this.thunk = thunk;
    }

    public final static Monad<IO.µ> monad = new Monad<IO.µ>() {

        @Override
        public <A, B> _<IO.µ, B> bind(_<IO.µ, A> nestedA, Function<A, _<µ, B>> fn) {
            return fn.apply(narrow(nestedA).thunk.get());
        }

        @Override
        public <A, B> _<IO.µ, B> ap(_<IO.µ, Function<A, B>> fn, _<IO.µ, A> nestedA) {
            return new IO<>(narrow(fn).thunk.get().apply(narrow(nestedA).thunk.get()));
        }

        @Override
        public <A> _<IO.µ, A> pure(A a) {
            return new IO<>(a);
        }

        @Override
        public <A, B> _<µ, B> map(Function<A, B> fn, _<µ, A> nestedA) {
            Supplier<A> thunk = narrow(nestedA).thunk;
            return new IO<>(() -> fn.apply(thunk.get()));
        }
    };
}
