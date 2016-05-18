package org.highj.data.collection;

import org.derive4j.hkt.__;
import org.highj.data.collection.identity.*;

public class Identity<A> implements __<Identity.µ,A> {
    public static class µ {}

    private final A value;

    private Identity(A value) {
        this.value = value;
    }

    public static <A> Identity<A> narrow(__<Identity.µ,A> a) {
        return (Identity<A>)a;
    }

    public static <A> Identity<A> identity(A a) {
        return new Identity<>(a);
    }

    public A run() {
        return value;
    }

    public static final IdentityFunctor functor = new IdentityFunctor() {};

    public static final IdentityApply apply = new IdentityApply() {};

    public static final IdentityApplicative applicative = new IdentityApplicative() {};

    public static final IdentityBind bind = new IdentityBind() {};

    public static final IdentityMonad monad = new IdentityMonad() {};

    public static final IdentityMonadRec monadRec = new IdentityMonadRec() {};
}
