package org.highj.data.stateful;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.typeclass1.monad.MonadLaw;
import org.highj.util.Gen;
import org.highj.util.Gen1;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.highj.Hkt.asAsyncIO;
import static org.highj.Hkt.asSafeIO;

public class AsyncIOTest {

    // XXX: HACK
    private final Gen1<AsyncIO.µ> gen1 = new Gen1<AsyncIO.µ>() {
        @Override
        public <T> Gen<__<AsyncIO.µ, T>> gen(Gen<T> gen) {
            return gen.map(s ->
                               Gen.rnd.nextDouble() < 0.5 ?
                                   pureAsync(s) :
                                   pureSync(s)
            );
        }
    };

    // XXX: HACK
    private final Eq1<AsyncIO.µ> eq1 = new Eq1<AsyncIO.µ>() {
        @Override
        public <T> Eq<__<AsyncIO.µ, T>> eq1(Eq<? super T> eq) {
            @SuppressWarnings("unchecked") Eq<T> eq2 = (Eq<T>) eq;
            Eq<IOException> ioExceptionEq =
                (IOException e1, IOException e2) ->
                    true;
            Eq<Either<IOException, T>> eitherEq = Either.eq(ioExceptionEq, eq2);
            return (__<AsyncIO.µ, T> a, __<AsyncIO.µ, T> b) -> {
                class Util {
                    Either<IOException, T> aResult;
                    Either<IOException, T> bResult;
                    boolean aAsync;
                    boolean bAsync;
                }
                final Util util = new Util();
                Maybe<Either<IOException, T>> a3Op = asAsyncIO(a).toIO(
                    (Either<IOException, T> a2) ->
                        () -> {
                            util.aResult = a2;
                            util.aAsync = true;
                            return T0.of();
                        }
                ).run();
                for (Either<IOException, T> a3 : a3Op) {
                    util.aResult = a3;
                    util.aAsync = false;
                }
                Maybe<Either<IOException, T>> b3Op = asAsyncIO(b).toIO(
                    (Either<IOException, T> b2) ->
                        () -> {
                            util.bResult = b2;
                            util.bAsync = true;
                            return T0.of();
                        }
                ).run();
                for (Either<IOException, T> b3 : b3Op) {
                    util.bResult = b3;
                    util.bAsync = false;
                }
                return eitherEq.eq(util.aResult, util.bResult) && util.aAsync == util.bAsync;
            };
        }
    };

    // XXX: HACK
    private static <A> AsyncIO<A> pureAsync(A a) {
        return (F1<Either<IOException, A>, SafeIO<T0>> handler) ->
                   asSafeIO(
                       SafeIO.functor.left$(
                           Maybe.<Either<IOException, A>>Nothing(),
                           handler.apply(Either.Right(a))
                       )
                   );
    }

    private static <A> AsyncIO<A> pureSync(A a) {
        return asAsyncIO(AsyncIO.applicative.pure(a));
    }

    @Test
    public void monadLaw() {
        new MonadLaw<>(AsyncIO.monad, gen1, eq1).test();
    }

}
