package org.highj.data.stateful.coreader;

import org.derive4j.hkt.__;
import org.highj.data.stateful.Coreader;
import org.highj.typeclass1.comonad.Comonad;

import java.util.function.Function;

import static org.highj.Hkt.asCoreader;

public interface CoreaderComonad<R> extends Comonad<__<Coreader.µ, R>> {

    @Override
    default <A> A extract(__<__<Coreader.µ, R>, A> nestedA) {
        return asCoreader(nestedA).extract();
    }

    @Override
    default <A, B> Coreader<R, B> map(Function<A, B> fn, __<__<Coreader.µ, R>, A> nestedA) {
        return asCoreader(nestedA).map(fn);
    }

    @Override
    default <A> Coreader<R, __<__<Coreader.µ, R>, A>> duplicate(__<__<Coreader.µ, R>, A> nestedA) {
        return new Coreader<>(asCoreader(nestedA).ask(), nestedA);
    }

    @Override
    default <A, B> Function<__<__<Coreader.µ, R>, A>, __<__<Coreader.µ, R>, B>> extend(final Function<__<__<Coreader.µ, R>, A>, B> fn) {
        return cra -> asCoreader(cra).duplicate().map(fn::apply);
    }
}
