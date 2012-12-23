package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;

//either bind mplus join needs to be implemented, as they are defined in terms of each other
public abstract class BindAbstract<mu> extends ApplyAbstract<mu> implements Bind<mu> {

    // (>>=) (Control.Monad)
    public <A,B> _<mu, B> bind(_<mu, A> nestedA, F1<A, _<mu, B>> fn) {
        return join(map(fn, nestedA));
    }

    // join (Control.Monad)
    public <A> _<mu, A> join(_<mu, _<mu, A>> nestedNestedA) {
         return bind(nestedNestedA, F1.<_<mu, A>>id());
    }

    // (>>) (Control.Monad)
    public <A, B> _<mu, B> semicolon(_<mu, A> nestedA, _<mu, B> nestedB) {
        return bind(nestedA, F1.<A, _<mu, B>>constant(nestedB));
    }

    // (>=>) (Control.Monad) left-to-right Kleisli composition of monads
    public <A, B, C> F1<A, _<mu, C>> kleisli(final F1<A, _<mu, B>> f, final F1<B, _<mu, C>> g) {
        return new F1<A, _<mu, C>>() {
            @Override
            public _<mu, C> $(A a) {
                return bind(f.$(a), g);
            }
        };
    }
}
