package org.highj.typeclass2.profunctor;

import org.derive4j.hkt.__;
import org.highj.typeclass2.arrow.Category;

import java.util.function.Function;

public interface Profunctor<P> {

    <A,B,C,D> __<__<P,A>,D> dimap(Function<A,B> f, Function<C,D> g, __<__<P,B>,C> p);

    default <A,B,C> __<__<P,A>,C> lmap(Function<A,B> f, __<__<P,B>,C> p) {
        return dimap(f, Function.identity(), p);
    }

    default <A,B,C> __<__<P,A>,C> rmap(Function<B,C> g, __<__<P,A>,B> p) {
        return dimap(Function.identity(), g, p);
    }

    default <A,B> __<__<P,A>,B> arr(Category<P> category, Function<A,B> f) {
        return rmap(f, category.identity());
    }
}
