package org.highj.data.stateful;

import java.io.IOException;
import org.derive4j.hkt.__;
import org.highj.data.stateful.io.IOMonad;

import java.util.function.Function;
import org.highj.data.Either;
import org.highj.data.stateful.io.IOApplicative;
import org.highj.data.stateful.io.IOApply;
import org.highj.data.stateful.io.IOBind;
import org.highj.data.stateful.io.IOFunctor;
import org.highj.data.stateful.io.IOMonadError;
import org.highj.data.stateful.io.IOMonadIO;
import org.highj.data.stateful.io.IOMonadRec;

public interface IO<A> extends __<IO.µ, A> {

    final class µ {}
    
    A run() throws IOException;
    
    default SafeIO<Either<IOException,A>> toSafeIO() {
        return () -> {
            try {
                return Either.<IOException,A>Right(run());
            } catch (IOException ex) {
                return Either.<IOException,A>Left(ex);
            }
        };
    }

    default <B> IO<B> map(Function<A, B> fn) {
        return functor.map(fn, this);
    }

    default <B> IO<B> ap(IO<Function<A, B>> fn) {
        return apply.ap(fn, this);
    }

    default <B> IO<B> bind(Function<A, __<µ, B>> fn) {
        return bind.bind(this, fn);
    }

    IOFunctor functor = new IOFunctor() {};
    
    IOApply apply = new IOApply() {};
    
    IOApplicative applicative = new IOApplicative() {};
    
    IOBind bind = new IOBind() {};

    IOMonad monad = new IOMonad() {};
    
    IOMonadError monadError = new IOMonadError() {};
    
    IOMonadIO monadIO = new IOMonadIO() {};
    
    IOMonadRec monadRec = new IOMonadRec() {};
}
