package org.highj.typeclass1.comonad;

import org.derive4j.hkt.__;
import org.highj.function.Functions;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

//Minimal implementation: duplicate(__<W, A>) OR extend(Function<__<W, A>, B>)
public interface Extend<W> extends Functor<W> {

    default <A> __<W, __<W, A>> duplicate(__<W, A> nestedA) {
        // duplicate = extend id
        return extend(Function.<__<W, A>>identity()).apply(nestedA);
    }

    default <A, B> Function<__<W, A>, __<W, B>> extend(final Function<__<W, A>, B> fn) {
        //extend f = fmap f . duplicate
        return x -> lift(fn).apply(duplicate(x));
    }

    //(=>=)
    default <A, B, C> Function<__<W, A>, C> cokleisli(Function<__<W, A>, B> f, Function<__<W, B>, C> g) {
        //f =>= g = g . extend f
        return Functions.compose(g, extend(f));
    }

}