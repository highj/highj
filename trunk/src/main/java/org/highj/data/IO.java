package org.highj.data;

import org.highj._;
import org.highj.function.F0;
import org.highj.function.F1;
import org.highj.typeclass.monad.Monad;
import org.highj.typeclass.monad.MonadAbstract;

public class IO<A> extends _<IO.µ, A> {

    private static final µ hidden = new µ();

    public static final class µ {
        private µ() {
        }
    }

    @SuppressWarnings("unchecked")
    public static <A> IO<A> narrow(_<µ, A> value) {
        return (IO) value;
    }

    F0<A> thunk;

    private IO(A a) {
        super(hidden);
        thunk = F0.constant(a);
    }

    private IO(F0<A> thunk) {
        super(hidden);
        this.thunk = thunk;
    }

    public final static Monad<IO.µ> monad = new MonadAbstract<IO.µ>() {

        @Override
        public <A, B> _<IO.µ, B> bind(_<IO.µ, A> nestedA, F1<A, _<IO.µ, B>> fn) {
            return fn.$(narrow(nestedA).thunk.$());
        }

        @Override
        public <A, B> _<IO.µ, B> ap(_<IO.µ, F1<A, B>> fn, _<IO.µ, A> nestedA) {
            return new IO<B>(narrow(fn).thunk.$().$(narrow(nestedA).thunk.$()));
        }

        @Override
        public <A> _<IO.µ, A> pure(A a) {
            return new IO<A>(a);
        }

        @Override
        public <A> F1<A, _<IO.µ, A>> pure() {
            return new F1<A, _<IO.µ, A>>() {
                @Override
                public _<IO.µ, A> $(A a) {
                    return pure(a);
                }
            };
        }

        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            return new IO<B>(narrow(nestedA).thunk.map(fn));
        }
    };
}
