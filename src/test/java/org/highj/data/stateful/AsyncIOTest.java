package org.highj.data.stateful;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.eq.Eq;
import org.highj.data.eq.PartialEq;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.*;
import org.highj.util.Gen;
import org.highj.util.PartialGen;
import org.junit.Test;

import java.io.IOException;

public class AsyncIOTest {

    // XXX: HACK
    private final PartialGen<AsyncIO.µ> partialGen = new PartialGen<AsyncIO.µ>() {
        @Override
        public <T> Gen<__<AsyncIO.µ, T>> deriveGen(Gen<T> gen) {
            return gen.map(s ->
                Gen.rnd.nextDouble() < 0.5 ?
                    pureAsync(s) :
                    pureSync(s)
            );
        }
    };

    // XXX: HACK
    private final PartialEq<AsyncIO.µ> partialEq = new PartialEq<AsyncIO.µ>() {
        @Override
        public <T> Eq<__<AsyncIO.µ, T>> deriveEq(Eq<? super T> eq) {
            @SuppressWarnings("unchecked") Eq<T> eq2 = (Eq<T>)eq;
            Eq<IOException> ioExceptionEq =
                (IOException e1, IOException e2) ->
                    true;
            Eq<Either<IOException,T>> eitherEq = Either.eq(ioExceptionEq, eq2);
            return (__<AsyncIO.µ, T> a, __<AsyncIO.µ, T> b) -> {
                class Util {
                    Either<IOException,T> aResult;
                    Either<IOException,T> bResult;
                    boolean aAsync;
                    boolean bAsync;
                }
                final Util util = new Util();
                Maybe<Either<IOException,T>> a3Op = AsyncIO.narrow(a).toIO(
                    (Either<IOException,T> a2) ->
                        (SafeIO<T0>)() -> {
                            util.aResult = a2;
                            util.aAsync = true;
                            return T0.of();
                        }
                ).run();
                for (Either<IOException,T> a3 : a3Op) {
                    util.aResult = a3;
                    util.aAsync = false;
                }
                Maybe<Either<IOException,T>> b3Op = AsyncIO.narrow(b).toIO(
                    (Either<IOException,T> b2) ->
                        (SafeIO<T0>)() -> {
                            util.bResult = b2;
                            util.bAsync = true;
                            return T0.of();
                        }
                ).run();
                for (Either<IOException,T> b3 : b3Op) {
                    util.bResult = b3;
                    util.bAsync = false;
                }
                return eitherEq.eq(util.aResult, util.bResult) && util.aAsync == util.bAsync;
            };
        }
    };

    // XXX: HACK
    private static <A> AsyncIO<A> pureAsync(A a) {
        return (F1<Either<IOException,A>,SafeIO<T0>> handler) ->
            SafeIO.narrow(
                SafeIO.functor.left$(
                    Maybe.<Either<IOException,A>>Nothing(),
                    handler.apply(Either.Right(a))
                )
            );
    }

    private static <A> AsyncIO<A> pureSync(A a) {
        return AsyncIO.narrow(AsyncIO.applicative.pure(a));
    }

    @Test
    public void monadLaw() {
        new MonadLaw<>(AsyncIO.monad, partialGen, partialEq).testAll();
    }

}
