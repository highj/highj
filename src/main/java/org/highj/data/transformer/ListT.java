package org.highj.data.transformer;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.data.transformer.list.ListTAlt;
import org.highj.data.transformer.list.ListTAlternative;
import org.highj.data.transformer.list.ListTApplicative;
import org.highj.data.transformer.list.ListTApply;
import org.highj.data.transformer.list.ListTBind;
import org.highj.data.transformer.list.ListTFunctor;
import org.highj.data.transformer.list.ListTMonad;
import org.highj.data.transformer.list.ListTMonadPlus;
import org.highj.data.transformer.list.ListTMonadTrans;
import org.highj.data.transformer.list.ListTMonadZero;
import org.highj.data.transformer.list.ListTPlus;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;

public class ListT<M, A> implements __2<ListT.µ, M, A> {

    public interface µ {
    }

    private final __<M, Step<M, A>> step;

    ListT(__<M, Step<M, A>> step) {
        this.step = step;
    }

    public static <M, A> ListT<M, A> narrow(__<__<ListT.µ, M>, A> nested) {
        return (ListT<M, A>) nested;
    }

    public interface Step<M, A> {
        <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier);
    }

    public static class Yield<M, A> implements Step<M, A> {
        private final A head;
        private final Supplier<ListT<M, A>> tail;

        private Yield(A head, Supplier<ListT<M, A>> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public Supplier<ListT<M, A>> tail() {
            return tail;
        }

        public <T> Supplier<T> mapTail(Function<ListT<M, A>, T> fn) {
            return () -> fn.apply(tail.get());
        }

        @Override
        public <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier) {
            return yieldFn.apply(this);
        }
    }

    public static class Skip<M, A> implements Step<M, A> {

        private final Supplier<ListT<M, A>> tail;

        private Skip(Supplier<ListT<M, A>> tail) {
            this.tail = tail;
        }

        public Supplier<ListT<M, A>> tail() {
            return tail;
        }

        public <T> Supplier<T> mapTail(Function<ListT<M, A>, T> fn) {
            return () -> fn.apply(tail.get());
        }

        @Override
        public <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier) {
            return skipFn.apply(this);
        }
    }

    public static class Done<M, A> implements Step<M, A> {

        private Done() {
        }

        @Override
        public <B> B map(Function<Yield<M, A>, B> yieldFn, Function<Skip<M, A>, B> skipFn, Supplier<B> doneSupplier) {
            return doneSupplier.get();
        }
    }

    public static <A, M> Yield<M, A> yield(A a, Supplier<ListT<M, A>> supplier) {
        return new Yield<>(a, supplier);
    }

    public static <A, M> Skip<M, A> skip(Supplier<ListT<M, A>> supplier) {
        return new Skip<>(supplier);
    }

    public static <A, M> Done<A, M> done() {
        return new Done<>();
    }

    public __<M, Step<M, A>> run() {
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

    public ListT<M, A> prepend(Applicative<M> applicative, A head) {
        return new ListT<>(applicative.pure(yield(head, () -> this)));
    }

    public <B> ListT<M, B> stepMap(Functor<M> functor,
            Function<Step<M, A>, Step<M, B>> fn) {
        return new ListT<>(functor.map(fn, step));
    }

    public <B> ListT<M, B> stepMap(
            Functor<M> functor,
            Function<Yield<M, A>, Step<M, B>> yieldFn,
            Function<Skip<M, A>, Step<M, B>> skipFn,
            Supplier<Step<M, B>> doneSupplier) {
        return stepMap(functor, step -> step.map(yieldFn, skipFn, doneSupplier));
    }

    public static <M, A> ListT<M, A> concat(Applicative<M> applicative,
            ListT<M, A> first, ListT<M, A> second) {
        return first.stepMap(applicative,
                stepYield -> yield(stepYield.head, stepYield.mapTail(s -> concat(applicative, s, second))),
                stepSkip -> skip(stepSkip.mapTail(s -> concat(applicative, s, second))),
                () -> skip(() -> second));
    }

    public ListT<M, A> append(Applicative<M> applicative, ListT<M, A> that) {
        return concat(applicative, this, that);
    }

    public static <M, A> ListT<M, A> singleton(Applicative<M> applicative, A a) {
        return ListT.<M, A> nil(applicative).prepend(applicative, a);
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

    public static <M, A, Z> ListT<M, A> unfold(Functor<M> functor,
            Function<Z, __<M, Maybe<T2<Z, A>>>> fn, Z z) {
        return new ListT<>(functor.map(maybe ->
                maybe.<Step<M, A>> map(
                        (T2<Z, A> t2) -> yield(t2._2(), () -> unfold(functor, fn, t2._1())))
                        .getOrElse(() -> ListT.done()),
                fn.apply(z)));
    }

    public static <M, A> ListT<M, A> iterate(Applicative<M> applicative,
            Function<A, A> fn, A a) {
        return unfold(applicative, v -> applicative.pure(Maybe.Just(T2.of(fn.apply(v), v))), a);
    }

    public static <M, A> ListT<M, A> repeat(Monad<M> monad, A a) {
        return iterate(monad, Function.identity(), a);
    }

    public ListT<M, A> take(Applicative<M> applicative, int n) {
        return (n <= 0)
                ? nil(applicative)
                : stepMap(applicative,
                        stepYield -> yield(stepYield.head, stepYield.mapTail(s -> s.take(applicative, n - 1))),
                        stepSkip -> skip(stepSkip.mapTail(s -> s.take(applicative, n))),
                        () -> ListT.done());
    }

    public ListT<M, A> takeWhile(Applicative<M> applicative, Predicate<A> predicate) {
        return stepMap(applicative,
                stepYield -> predicate.test(stepYield.head)
                        ? yield(stepYield.head, stepYield.mapTail(s -> s.takeWhile(applicative, predicate)))
                        : done(),
                stepSkip -> skip(stepSkip.mapTail(s -> s.takeWhile(applicative, predicate))),
                () -> ListT.done());
    }

    public ListT<M, A> drop(Applicative<M> applicative, int n) {
        return (n <= 0)
                ? this
                : stepMap(applicative,
                        stepYield -> skip(stepYield.mapTail(s -> s.drop(applicative, n - 1))),
                        stepSkip -> skip(stepSkip.mapTail(s -> s.drop(applicative, n))),
                () -> ListT.done());
    }

    public ListT<M, A> dropWhile(Applicative<M> applicative, Predicate<A> predicate) {
        return stepMap(applicative,
                stepYield -> predicate.test(stepYield.head)
                        ? skip(stepYield.mapTail(s -> s.dropWhile(applicative, predicate)))
                        : stepYield,
                stepSkip -> skip(stepSkip.mapTail(s -> s.dropWhile(applicative, predicate))),
                () -> ListT.done());
    }

    public ListT<M, A> filter(Applicative<M> applicative, Predicate<A> predicate) {
        return stepMap(applicative,
                stepYield -> predicate.test(stepYield.head)
                        ? yield(stepYield.head, stepYield.mapTail(s -> s.filter(applicative, predicate)))
                        : skip(stepYield.mapTail(s -> s.filter(applicative, predicate))),
                stepSkip -> skip(stepSkip.mapTail(s -> s.filter(applicative, predicate))),
                () -> ListT.done());
    }

    public <B> ListT<M, B> mapMaybe(Functor<M> functor, Function<A, Maybe<B>> fn) {
        return stepMap(functor,
                stepYield -> {
                    Supplier<ListT<M, B>> mbSup = stepYield.mapTail(s -> s.mapMaybe(functor, fn));
                    return fn.apply(stepYield.head).cata$(() -> skip(mbSup), b -> yield(b, mbSup));
                },
                stepSkip -> skip(stepSkip.mapTail(s -> s.mapMaybe(functor, fn))),
                () -> ListT.done());
    }

    public static <M, A> ListT<M, A> catMaybes(Functor<M> functor, ListT<M, Maybe<A>> maybes) {
        return maybes.mapMaybe(functor, Function.identity());
    }

    public __<M, Maybe<T2<A, ListT<M, A>>>> uncons(Monad<M> monad) {
        return monad.bind(step, s -> s.map(
                stepYield -> monad.pure(Maybe.Just(T2.of(stepYield.head, stepYield.tail.get()))),
                stepSkip -> stepSkip.tail.get().uncons(monad),
                () -> monad.pure(Maybe.Nothing())));
    }

    public __<M, Maybe<A>> head(Monad<M> monad) {
        return monad.map(maybe -> maybe.map(T2::_1), uncons(monad));
    }

    public __<M, Maybe<ListT<M, A>>> tail(Monad<M> monad) {
        return monad.map(maybe -> maybe.map(T2::_2), uncons(monad));
    }

    public <B> __<M, B> foldl_(Monad<M> monad, BiFunction<B, A, __<M, B>> fn, B b) {
        return monad.bind(uncons(monad),
                g -> g.map(t2 -> monad.bind(fn.apply(b, t2._1()), b_ -> t2._2().foldl_(monad, fn, b_)))
                        .getOrElse(() -> monad.pure(b)));
    }

    public <B> __<M, B> foldl(Monad<M> monad, BiFunction<B, A, B> fn, B b) {
        return monad.bind(uncons(monad),
                g -> g.map(t2 -> t2._2().foldl(monad, fn, fn.apply(b, t2._1()))).getOrElse(() -> monad.pure(b)));
    }

    public <B> ListT<M, B> scanl(Monad<M> monad, BiFunction<B, A, B> fn, B b) {
        Function<T2<B, ListT<M, A>>, __<M, Maybe<T2<T2<B, ListT<M, A>>, B>>>> g = t2 ->
                monad.map(h -> h.map(
                        stepYield -> {
                            B b__ = fn.apply(b, stepYield.head);
                            return Maybe.Just(T2.of(T2.of(b__, stepYield.tail.get()), b__));
                        },
                        stepSkip -> Maybe.Just(T2.of(T2.of(t2._1(), stepSkip.tail.get()), t2._1())),
                        Maybe::Nothing
                        ), t2._2().step);
        return unfold(monad, g, T2.of(b, this));
    }

    public static <M, A, B, C> ListT<M, C> zipWith_(Monad<M> monad, BiFunction<A, B, __<M, C>> fn, ListT<M, A> ma, ListT<M, B> mb) {
        __<M, Maybe<T2<A, ListT<M, A>>>> ua = ma.uncons(monad);
        __<M, Maybe<T2<B, ListT<M, B>>>> ub = mb.uncons(monad);
        __<M, ListT<M, C>> uc = monad.bind(ua, va -> monad.bind(ub, vb -> va.isNothing() || vb.isNothing()
                ? monad.pure(nil(monad))
                : monad.map(a -> prepend_(monad, a, () -> zipWith_(monad, fn, va.get()._2(), vb.get()._2())),
                        fn.apply(va.get()._1(), vb.get()._1()))));
        return wrapEffect(monad, uc);
    }

    public static <M, A, B, C> ListT<M, C> zipWith(Monad<M> monad, BiFunction<A, B, C> fn, ListT<M, A> ma, ListT<M, B> mb) {
        return zipWith_(monad, fn.andThen(monad::pure), ma, mb);
    }

    public static <M, A> Semigroup<ListT<M, A>> semigroup(Applicative<M> applicative) {
        return (x, y) -> concat(applicative, x, y);
    }

    public static <M, A> Monoid<ListT<M, A>> monoid(Applicative<M> applicative) {
        return Monoid.create(nil(applicative), (x, y) -> concat(applicative, x, y));
    }

    public static <M> ListTFunctor<M> functor(Functor<M> mFunctor) {
        return () -> mFunctor;
    }

    public static <M> ListTApply<M> apply(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> ListTApplicative<M> applicative(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> ListTBind<M> bind(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> ListTMonad<M> monad(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> ListTMonadTrans<M> monadTrans(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> ListTAlt<M> alt(Applicative<M> mApplicative) {
        return () -> mApplicative;
    }

    public static <M> ListTPlus<M> plus(Applicative<M> mApplicative) {
        return () -> mApplicative;
    }

    public static <M> ListTAlternative<M> alternative(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> ListTMonadZero<M> monadZero(Monad<M> mMonad) {
        return () -> mMonad;
    }

    public static <M> ListTMonadPlus<M> monadPlus(Monad<M> mMonad) {
        return () -> mMonad;
    }
}
