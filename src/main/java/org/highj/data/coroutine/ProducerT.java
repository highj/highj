package org.highj.data.coroutine;

import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Visibility;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import org.highj.data.coroutine.producer.ProducerTApplicative;
import org.highj.data.coroutine.producer.ProducerTApply;
import org.highj.data.coroutine.producer.ProducerTBind;
import org.highj.data.coroutine.producer.ProducerTFunctor;
import org.highj.data.coroutine.producer.ProducerTMonad;
import org.highj.data.coroutine.producer.ProducerTMonadRec;
import org.highj.data.coroutine.producer.ProducerTMonadTrans;

/**
 * JavaScript / Python style generator, expressed as a Monad.
 * 
 * @param <E> The element type that gets yielded.
 * @param <M> The base monad type.
 * @param <A> The final return type after the full execution.
 */
@Data(@Derive(inClass = "ProducerTImpl", withVisibility = Visibility.Package))
public abstract class ProducerT<E,M,A> implements __3<ProducerT.µ,E,M,A> {
    public static class µ {
    }

    public static <E,M,A> ProducerT<E,M,A> narrow(__<__<__<µ,E>,M>,A> a) {
        return (ProducerT<E,M,A>)a;
    }

    public interface Cases<R,E,M,A> {
        R done(A result);
        R yield(E emitValue, ProducerT<E,M,A> rest);
        R bind(Bound<E,M,?,A> bound);
        R suspend(Supplier<ProducerT<E,M,A>> suspend);
        R lift(__<M,A> ma);
    }

    public abstract <R> R match(Cases<R,E,M,A> cases);

    public static class Bound<E,M,A,B> {
        private final ProducerT<E,M,A> _m;
        private final F1<A,ProducerT<E,M,B>> _f;

        private Bound(ProducerT<E,M,A> m, F1<A,ProducerT<E,M,B>> f) {
            this._m = m;
            this._f = f;
        }

        public static <E,M,A,B> Bound<E,M,A,B> mkBound(ProducerT<E,M,A> m, F1<A,ProducerT<E,M,B>> f) {
            return new Bound(m, f);
        }

        public ProducerT<E,M,A> m() {
            return _m;
        }

        public F1<A,ProducerT<E,M,B>> f() {
            return _f;
        }
    }

    /**
     * Executes a step of the generator.
     * 
     * @param mMonadRec The base monad. (It must support a MonadRec instance.)
     * @return Either a completion value (left side of Either), or an yielded element tupled with the remaining
     *         computation to be resumed later (right side of Either).
     */
    public __<M,Either<A, T2<E,ProducerT<E,M,A>>>> run(MonadRec<M> mMonadRec) {
        return mMonadRec.tailRec(ProducerTImpl
                .<E,M,A>cases()
                .done((A result) -> mMonadRec.pure(Either.<ProducerT<E,M,A>,Either<A, T2<E,ProducerT<E,M,A>>>>Right(Either.<A,T2<E,ProducerT<E,M,A>>>Left(result))))
                .yield((E emitValue, ProducerT<E, M, A> rest) -> mMonadRec.pure(Either.<ProducerT<E,M,A>,Either<A, T2<E,ProducerT<E,M,A>>>>Right(Either.<A,T2<E,ProducerT<E,M,A>>>Right(T2.of(emitValue, rest)))))
                .bind((Bound<E,M,?,A> bound) -> runBound(mMonadRec, bound))
                .suspend((Supplier<ProducerT<E,M,A>> suspend) -> mMonadRec.pure(Either.<ProducerT<E,M,A>,Either<A, T2<E,ProducerT<E,M,A>>>>Left(suspend.get())))
                .lift((__<M,A> ma) -> mMonadRec.map((A a) -> Either.<ProducerT<E,M,A>,Either<A, T2<E,ProducerT<E,M,A>>>>Right(Either.<A,T2<E,ProducerT<E,M,A>>>Left(a)),
                    ma
                )),
            this
        );
    }

    /**
     * Converts a Generator based on the identity monad into a Java Iterator.
     * 
     * @param <E> The element type that gets yielded.
     * @param generator The generator to be converted into an iterator.
     * @return An iterator that will produce the same elements as the generator.
     */
    public static <E> Iterator<E> toIterator(ProducerT<E,T1.µ,T0> generator) {
        final Maybe<T2<E,ProducerT<E,T1.µ,T0>>> initState = T1.narrow(generator.run(T1.monadRec))._1().maybeRight();
        return new Iterator<E>() {
            private Maybe<T2<E,ProducerT<E,T1.µ,T0>>> state = initState;
            @Override
            public boolean hasNext() {
                return state.isJust();
            }
            @Override
            public E next() {
                if (state.isNothing()) {
                    throw new NoSuchElementException();
                }
                T2<E,ProducerT<E,T1.µ,T0>> x = state.get();
                E element = x._1();
                state = T1.narrow(x._2().run(T1.monadRec))._1().maybeRight();
                return element;
            }
        };
    }

    public static <E,M,A,B> __<M,Either<ProducerT<E,M,A>,Either<A,T2<E,ProducerT<E,M,A>>>>> runBound(Monad<M> mMonad, Bound<E,M,B,A> bound) {
        return ProducerTImpl
            .<E,M,B>cases()
            .done((B b) -> mMonad.pure(Either.<ProducerT<E, M, A>, Either<A, T2<E, ProducerT<E, M, A>>>>Left(bound.f().apply(b))))
            .yield((E e, ProducerT<E, M, B> rest) -> mMonad.pure(Either.<ProducerT<E, M, A>, Either<A, T2<E, ProducerT<E, M, A>>>>Right(Either.<A, T2<E, ProducerT<E, M, A>>>Right(T2.of(e, ProducerT.bind(rest, bound.f()))))))
            .bind((Bound<E, M, ?, B> bound2) ->
                mMonad.map((Either<ProducerT<E, M, B>, Either<B, T2<E, ProducerT<E, M, B>>>> x) ->
                        x.<Either<ProducerT<E, M, A>, Either<A, T2<E, ProducerT<E, M, A>>>>>either((ProducerT<E, M, B> genB) ->
                                Either.<ProducerT<E, M, A>, Either<A, T2<E, ProducerT<E, M, A>>>>Left(ProducerT.bind(genB, bound.f())),
                            (Either<B, T2<E, ProducerT<E, M, B>>> x2) ->
                                x2.either((B b) ->
                                        Either.<ProducerT<E, M, A>, Either<A, T2<E, ProducerT<E, M, A>>>>Left(bound.f().apply(b)),
                                    (T2<E, ProducerT<E, M, B>> x3) ->
                                        Either.<ProducerT<E, M, A>, Either<A, T2<E, ProducerT<E, M, A>>>>Right(Either.<A, T2<E, ProducerT<E, M, A>>>Right(T2.of(x3._1(), ProducerT.bind(x3._2(), bound.f()))))
                                )
                        ),
                    runBound(mMonad, bound2)
                )
            )
            .suspend((Supplier<ProducerT<E,M,B>> suspend) -> mMonad.pure(Either.Left(ProducerT.bind(suspend.get(), bound.f()))))
            .lift((__<M, B> mb) -> mMonad.map((B b) -> Either.<ProducerT<E, M, A>, Either<A, T2<E, ProducerT<E, M, A>>>>Left(bound.f().apply(b)), mb))
            .apply(bound.m());
    }

    /**
     * A Generator that has finished.
     * 
     * @param <E> Element type.
     * @param <M> Base monad.
     * @param <A> Finished return type.
     * @param r The finished return value.
     * @return A generator in its finished state returning value r.
     */
    public static <E,M,A> ProducerT<E,M,A> done(A r) {
        return ProducerTImpl.done(r);
    }

    public static <E,M,A> ProducerT<E,M,A> yield(E a, ProducerT<E,M,A> rest) {
        return ProducerTImpl.yield(a, rest);
    }

    /**
     * Produces a generator that will suspend its computation returning a yielded value,
     * plus the rest of the computation to be completed later.
     * 
     * @param <E> Element type.
     * @param <M> Base monad.
     * @param e The yielded element value.
     * @return The generator that performs the yield.
     */
    public static <E,M> ProducerT<E,M,T0> yield(E e) {
        return yield(e, done(T0.of()));
    }

    public static <E,M,A,B> ProducerT<E,M,B> bind(ProducerT<E,M,A> ma, F1<A,ProducerT<E,M,B>> f) {
        return ProducerTImpl
            .<E,M,A>cases()
            .done((A a) -> f.apply(a))
            .bind((Bound<E,M,?,A> bound) -> reassociateBind(bound, f))
            .otherwise(() -> ProducerTImpl.bind(Bound.mkBound(ma, f)))
            .apply(ma);
    }

    private static <E,M,A,B,C> ProducerT<E,M,B> reassociateBind(Bound<E,M,C,A> m, F1<A,ProducerT<E,M,B>> f) {
        return suspend(() -> bind(m.m(), (C c) -> bind(m.f().apply(c), f)));
    }

    public static <E,M,A> ProducerT<E,M,A> suspend(Supplier<ProducerT<E,M,A>> a) {
        return ProducerTImpl.suspend(a);
    }

    public static <E,M,A> ProducerT<E,M,A> lift(__<M,A> ma) {
        return ProducerTImpl.lift(ma);
    }

    public static <E,M> ProducerTFunctor<E,M> functor() {
        return new ProducerTFunctor<E,M>() {};
    }

    public static <E,M> ProducerTApply<E,M> apply() {
        return new ProducerTApply<E,M>() {};
    }

    public static <E,M> ProducerTApplicative<E,M> applicative() {
        return new ProducerTApplicative<E,M>() {};
    }

    public static <E,M> ProducerTBind<E,M> bind() {
        return new ProducerTBind<E,M>() {};
    }

    public static <E,M> ProducerTMonad<E,M> monad() {
        return new ProducerTMonad<E,M>() {};
    }

    public static <E,M> ProducerTMonadTrans<E,M> monadTrans() {
        return new ProducerTMonadTrans<E,M>() {};
    }
    
    public static <E,M> ProducerTMonadRec<E,M> monadRec() {
        return new ProducerTMonadRec<E,M>() {};
    }
}
