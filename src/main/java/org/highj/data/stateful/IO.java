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

    public static final class µ {}

    public static <A> IO<A> narrow(__<µ, A> value) {
        return (IO<A>)value;
    }
    
    public A run() throws IOException;
    
    public default SafeIO<Either<IOException,A>> toSafeIO() {
        return () -> {
            try {
                return Either.<IOException,A>Right(run());
            } catch (IOException ex) {
                return Either.<IOException,A>Left(ex);
            }
        };
    }

    public default <B> IO<B> map(Function<A, B> fn) {
        return functor.map(fn, this);
    }

    public default <B> IO<B> ap(IO<Function<A, B>> fn) {
        return apply.ap(fn, this);
    }

    public default <B> IO<B> bind(Function<A, __<µ, B>> fn) {
        return bind.bind(this, fn);
    }

    public static final IOFunctor functor = new IOFunctor() {};
    
    public static final IOApply apply = new IOApply() {};
    
    public static final IOApplicative applicative = new IOApplicative() {};
    
    public static final IOBind bind = new IOBind() {};

    public static final IOMonad monad = new IOMonad() {};
    
    public static final IOMonadError monadError = new IOMonadError() {};
    
    public static final IOMonadIO monadIO = new IOMonadIO() {};
    
    public static final IOMonadRec monadRec = new IOMonadRec() {};
}
