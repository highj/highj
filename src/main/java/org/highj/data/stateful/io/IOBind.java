package org.highj.data.stateful.io;

import org.highj._;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

public interface IOBind extends IOApply, Bind<IO.µ> {
    @Override
    default <A, B> IO<B> bind(_<IO.µ, A> nestedA, Function<A, _<IO.µ, B>> fn){
        return () -> IO.narrow(fn.apply(IO.narrow(nestedA).run())).run();
    }
}
