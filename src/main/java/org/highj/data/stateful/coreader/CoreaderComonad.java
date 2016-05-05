package org.highj.data.stateful.coreader;

import org.highj._;
import org.highj.data.stateful.Coreader;
import org.highj.typeclass1.comonad.Comonad;

import java.util.function.Function;

public interface CoreaderComonad<R> extends Comonad<_<Coreader.µ, R>> {

    @Override
    default <A> A extract(_<_<Coreader.µ, R>, A> nestedA) {
        return Coreader.narrow(nestedA).extract();
    }

    @Override
    default <A, B> Coreader<R, B> map(Function<A, B> fn, _<_<Coreader.µ, R>, A> nestedA) {
        return Coreader.narrow(nestedA).map(fn);
    }

    @Override
    default <A> Coreader<R, _<_<Coreader.µ, R>, A>> duplicate(_<_<Coreader.µ, R>, A> nestedA) {
        return new Coreader<>(Coreader.narrow(nestedA).ask(), nestedA);
    }

    @Override
    default <A, B> Function<_<_<Coreader.µ, R>, A>, _<_<Coreader.µ, R>, B>> extend(final Function<_<_<Coreader.µ, R>, A>, B> fn) {
        return cra -> Coreader.narrow(cra).duplicate().map(fn::apply);
    }
}
