package org.highj.data.stateful.io;

import org.derive4j.hkt.__;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asIO;

public interface IOBind extends IOApply, Bind<IO.µ> {
    @Override
    default <A, B> IO<B> bind(__<IO.µ, A> nestedA, Function<A, __<IO.µ, B>> fn){
        return () -> asIO(fn.apply(asIO(nestedA).run())).run();
    }
}
