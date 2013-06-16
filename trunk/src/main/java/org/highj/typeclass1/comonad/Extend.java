package org.highj.typeclass1.comonad;

import org.highj._;
import org.highj.data.functions.Functions;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

//Minimal implementation: duplicate(_<W, A>) OR extend(Function<_<W, A>, B>)
public interface Extend<W> extends Functor<W> {

    public default <A> _<W, _<W, A>> duplicate(_<W, A> nestedA) {
        // duplicate = extend id
        return extend(Functions.<_<W, A>>id()).apply(nestedA);
    }

    public default <A, B> Function<_<W, A>, _<W, B>> extend(final Function<_<W, A>, B> fn) {
        //extend f = fmap f . duplicate
        return x -> lift(fn).apply(duplicate(x));
    }

    //(=>=)
    public default <A, B, C> Function<_<W, A>, C> cokleisli(Function<_<W, A>, B> f, Function<_<W, B>, C> g) {
        //f =>= g = g . extend f
        return Functions.compose(g, extend(f));
    }

}