package org.highj.data.transformer;

import java.util.function.Function;
import java.util.function.Supplier;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;

public class ListT<M, A> implements __2<ListT.µ, M, A> {

    public interface µ {
    }

    private final __<M, Step<A, ListT<M, A>>> step;

    ListT(__<M, Step<A, ListT<M, A>>> step) {
        this.step = step;
    }

    interface Step<A, S> {
    }

    interface Yield<A, S> extends Step<A, S> {
        A yield();

        Supplier<S> get();
    }

    interface Skip<A, S> extends Step<A, S> {
        Supplier<S> get();
    }

    interface Done<A, S> extends Step<A, S> {
    }

    private static <A, S> Yield<A, S> yield(A a, Supplier<S> supplier) {
        return new Yield<A, S>() {
            @Override
            public A yield() {
                return a;
            }

            @Override
            public Supplier<S> get() {
                return supplier;
            }
        };
    }

    private static <A, S> Skip<A, S> skip(Supplier<S> supplier) {
        return () -> supplier;
    }

    private static <A, S> Done<A, S> done() {
        return new Done<A, S>() {};
    }

    public __<M, Step<A, ListT<M, A>>> run() {
        return step;
    }

    public static <M, A> ListT<M, A> nil(Applicative<M> applicative) {
        return new ListT<>(applicative.pure(done()));
    }

    public static <M, A> ListT<M, A> cons(Applicative<M> applicative, Supplier<A> supHead, Supplier<ListT<M, A>> supTail) {
        return new ListT<>(applicative.pure(yield(supHead.get(), supTail)));
    }

    public static <M, A> ListT<M, A> prepend_(Applicative<M> applicative, A head, Supplier<ListT<M, A>> lh) {
        return new ListT<>(applicative.pure(yield(head, lh)));
    }

    public static <M, A> ListT<M, A> prepend(Applicative<M> applicative, A head, ListT<M, A> tail) {
        return new ListT<>(applicative.pure(yield(head, () -> tail)));
    }

    public static <M, A, B> ListT<M, B> stepMap(Functor<M> functor,
            Function<Step<A, ListT<M, A>>, Step<B, ListT<M, B>>> fn, ListT<M, A> listA) {
        return new ListT<>(functor.map(fn, listA.step));
    }

    public static <M, A> ListT<M, A> concat(Applicative<M> applicative,
            ListT<M, A> first, ListT<M, A> second) {
        Function<Step<A, ListT<M, A>>, Step<A, ListT<M, A>>> fn = step -> {
            if (step instanceof Yield) {
                Yield<A, ListT<M, A>> stepYield = (Yield<A, ListT<M, A>>) step;
                return yield(stepYield.yield(), () -> concat(applicative, stepYield.get().get(), second));
            } else if (step instanceof Skip) {
                Skip<A, ListT<M, A>> stepSkip = (Skip<A, ListT<M, A>>) step;
                return skip(() -> concat(applicative, stepSkip.get().get(), second));
            } else {
                return skip(() -> second);
            }
        };
        return stepMap(applicative, fn, first);
    }

    public static <M, A> ListT<M, A> singleton(Applicative<M> applicative, A a) {
        return prepend(applicative, a, nil(applicative));
    }

    public static <M, A> ListT<M, A> fromEffect(Applicative<M> applicative, __<M, A> ma) {
        return new ListT<>(applicative.map(a -> yield(a, () -> nil(applicative)), ma));
    }

    public static <M, A> ListT<M, A> wrapEffect(Functor<M> functor, __<M, ListT<M, A>> v) {
        return new ListT<>(functor.map(listT -> skip(() -> listT), v));
    }

    public static <M, A> ListT<M, A> wrapLazy(Applicative<M> applicative, Supplier<ListT<M, A>> supplier) {
        return new ListT<>(applicative.pure(skip(supplier)));
    }

    public static <M, A, Z> ListT<M, A> unfold(Monad<M> monad, //why not Functor?
            Function<Z, __<M, Maybe<T2<Z, A>>>> fn, Z z) {
        Function<Maybe<T2<Z, A>>, Step<A, ListT<M, A>>> g = maybe ->
            maybe.<Step<A, ListT<M, A>>>map(
                    (T2<Z,A> t2) -> yield(t2._2(), () -> unfold(monad, fn, t2._1())))
                    .getOrElse(done());
        return new ListT<>(monad.map(g, fn.apply(z)));
    }

    public static <M,A> ListT<M,A> iterate(Monad<M> monad, // why not Applicative?
                                           Function<A,A> fn, A a) {
        return unfold(monad, v -> monad.pure(Maybe.Just(T2.of(fn.apply(v),v))), a);
    }

    public static <M,A> ListT<M,A> repeat(Monad<M> monad, A a) {
        return iterate(monad, Function.identity(), a);
    }


}
