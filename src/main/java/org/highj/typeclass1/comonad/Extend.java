package org.highj.typeclass1.comonad;

import org.highj._;
import org.highj.data.functions.Functions;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

//Minimal implementation: duplicate(_<µ, A>) OR extend(Function<_<µ, A>, B>)
public interface Extend<µ> extends Functor<µ> {

    public default <A> _<µ, _<µ, A>> duplicate(_<µ, A> nestedA) {
        // duplicate = extend id
        return extend(Functions.<_<µ, A>>id()).apply(nestedA);
    }

    public default <A, B> Function<_<µ, A>, _<µ, B>> extend(final Function<_<µ, A>, B> fn) {
        //extend f = fmap f . duplicate
        return x -> lift(fn).apply(duplicate(x));
    }

    //(=>=)
    public default <A, B, C> Function<_<µ, A>, C> cokleisli(Function<_<µ, A>, B> f, Function<_<µ, B>, C> g) {
        //f =>= g = g . extend f
        return Functions.compose(g, extend(f));
    }

}