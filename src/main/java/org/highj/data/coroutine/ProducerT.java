package org.highj.data.coroutine;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
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
import org.highj.data.transformer.FreeT;

import static org.highj.Hkt.asT1;
import static org.highj.Hkt.asYieldF;

/**
 * JavaScript / Python style generator, expressed as a Monad.
 * 
 * @param <E> The element type that gets yielded.
 * @param <M> The base monad type.
 * @param <A> The final return type after the full execution.
 */
public class ProducerT<E,M,A> implements __3<ProducerT.µ,E,M,A> {
    public static class µ {
    }
    
    private final FreeT<__<YieldF.µ,E>,M,A> _toFreeT;
    
    private ProducerT(FreeT<__<YieldF.µ,E>,M,A> toFreeT) {
        this._toFreeT = toFreeT;
    }
    
    public static <E,M,A> ProducerT<E,M,A> producerT(FreeT<__<YieldF.µ,E>,M,A> toFreeT) {
        return new ProducerT<>(toFreeT);
    }
    
    public FreeT<__<YieldF.µ,E>,M,A> toFreeT() {
        return _toFreeT;
    }
    
    /**
     * Executes a step of the generator.
     * 
     * @param mMonadRec The base monad. (It must support a MonadRec instance.)
     * @return Either a completion value (left side of Either), or an yielded element tupled with the remaining
     *         computation to be resumed later (right side of Either).
     */
    public __<M,Either<A, T2<E,ProducerT<E,M,A>>>> run(MonadRec<M> mMonadRec) {
        return mMonadRec.map(
            (Either<A,__<__<YieldF.µ,E>,FreeT<__<YieldF.µ,E>,M,A>>> x) ->
                x.bimap(
                    (A x2) -> x2,
                    (__<__<YieldF.µ,E>,FreeT<__<YieldF.µ,E>,M,A>> x2) -> {
                        YieldF<E,FreeT<__<YieldF.µ,E>,M,A>> x3 = asYieldF(x2);
                        return T2.of(x3.value(), ProducerT.producerT(x3.next()));
                    }
                ),
            toFreeT().resume(mMonadRec, YieldF.functor())
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
        final Maybe<T2<E,ProducerT<E,T1.µ,T0>>> initState = asT1(generator.run(T1.monadRec))._1().maybeRight();
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
                state = asT1(x._2().run(T1.monadRec))._1().maybeRight();
                return element;
            }
        };
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
        return ProducerT.producerT(FreeT.done(r));
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
        return ProducerT.producerT(FreeT.liftF(YieldF.yield(e, T0.of())));
    }

    public static <E,M,A,B> ProducerT<E,M,B> bind(ProducerT<E,M,A> ma, F1<A,ProducerT<E,M,B>> f) {
        return ProducerT.producerT(FreeT.bind(ma.toFreeT(), (A a) -> f.apply(a).toFreeT()));
    }

    public static <E,M,A> ProducerT<E,M,A> suspend(Supplier<ProducerT<E,M,A>> a) {
        return ProducerT.producerT(FreeT.suspend(() -> a.get().toFreeT()));
    }

    public static <E,M,A> ProducerT<E,M,A> lift(__<M,A> ma) {
        return ProducerT.producerT(FreeT.liftM(ma));
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
