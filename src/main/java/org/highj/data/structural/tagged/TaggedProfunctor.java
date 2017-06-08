package org.highj.data.structural.tagged;

import org.derive4j.hkt.__;
import org.highj.data.structural.Tagged;
import org.highj.typeclass2.profunctor.Profunctor;

import java.util.function.Function;

import static org.highj.Hkt.asTagged;

public interface TaggedProfunctor extends Profunctor<Tagged.µ> {

    @Override
    default <A, B, C, D> Tagged<A, D> dimap(Function<A, B> f, Function<C, D> g, __<__<Tagged.µ, B>, C> p) {
        return rmap(g, p).retag();
    }

    @Override
    default <A, B, C> Tagged<A, C> lmap(Function<A, B> f, __<__<Tagged.µ, B>, C> p) {
        return asTagged(p).retag();
    }

    @Override
    default <A, B, C> Tagged<A, C> rmap(Function<B, C> g, __<__<Tagged.µ, A>, B> p) {
        return asTagged(p).map(g);
    }
}
