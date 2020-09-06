package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.instance.stream.StreamMonad;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.function.F3;
import org.highj.function.F4;
import org.highj.function.Strings;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import static org.highj.Hkt.asList;
import static org.highj.Hkt.asStream;

/**
 * An immutable infinite lazy list.
 *
 * @param <A> the element type
 */
public abstract class Stream<A> implements __<Stream.µ, A>, Iterable<A>, Function<Integer, A> {

    public interface µ {
    }

    private Stream() {
    }

    public abstract A head();

    public abstract Stream<A> tail();

    @Override
    public A apply(Integer index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index " + index);
        }
        Stream<A> current = this;
        while (index-- > 0) {
            current = current.tail();
        }
        return current.head();
    }

    public static <A> Stream<A> repeat(A a) {
        return new Stream<A>() {

            @Override
            public A head() {
                return a;
            }

            @Override
            public Stream<A> tail() {
                return this;
            }
        };
    }

    public static <A> Stream<A> unfold(Function<A, A> fn, A a) {
        return new Stream<A>() {

            @Override
            public A head() {
                return a;
            }

            @Override
            public Stream<A> tail() {
                return unfold(fn, fn.apply(a));
            }
        };
    }

    public static <A> Stream<A> newStream(A a, Stream<A> stream) {
        return new Stream<A>() {

            @Override
            public A head() {
                return a;
            }

            @Override
            public Stream<A> tail() {
                return stream;
            }
        };
    }

    public static <A> Stream<A> newLazyStream(A a, Supplier<Stream<A>> thunk) {
        return new Stream<A>() {

            @Override
            public A head() {
                return a;
            }

            @Override
            public Stream<A> tail() {
                return thunk.get();
            }
        };
    }

    //assuming that the iterator doesn't stop
    public static <A> Stream<A> newLazyStream(Iterator<A> iterator) throws NoSuchElementException {
        return newLazyStream(iterator.next(), () -> newLazyStream(iterator));
    }

    //returns iterator values wrapped in Just, and Nothing when the iterator gets empty
    public static <A> Stream<Maybe<A>> maybeFromIterator(Iterator<A> iterator) {
        return newLazyStream(iterator.hasNext()
                                 ? Maybe.Just(iterator.next())
                                 : Maybe.Nothing(),
            () -> maybeFromIterator(iterator));
    }

    public Stream<A> filter(Predicate<? super A> predicate) {
        final Stream<A> result = dropWhile((A a) -> !predicate.test(a));
        return newLazyStream(result.head(), () -> result.tail().filter(predicate));
    }

    public String toString(int n) {
        return Strings.mkEnclosed("Stream(", ",", "...)", this.take(n));
    }

    @Override
    public String toString() {
        return toString(10);
    }

    public List<A> take(int n) {
        return n <= 0
                   ? List.Nil()
                   : List.Cons$(head(), () -> tail().take(n - 1));
    }

    public List<A> takeWhile(Predicate<A> predicate) {
        return !predicate.test(head())
                   ? List.Nil()
                   : List.Cons$(head(), () -> tail().takeWhile(predicate));
    }

    public Stream<A> drop(int n) {
        Stream<A> result = this;
        while (n-- > 0) {
            result = result.tail();
        }
        return result;
    }

    public Stream<A> dropWhile(Predicate<A> predicate) {
        Stream<A> result = this;
        while (predicate.test(result.head())) {
            result = result.tail();
        }
        return result;
    }

    public Stream<List<A>> inits() {
        return newLazyStream(List.empty(), () -> tail().inits().map(xs -> List.Cons(head(), xs)));
    }

    public Stream<Stream<A>> tails() {
        return newLazyStream(this, () -> tail().tails());
    }

    public Stream<A> intersperse(A a) {
        return newStream(head(), newLazyStream(a, () -> tail().intersperse(a)));
    }

    public <B> Stream<B> map(Function<? super A, ? extends B> fn) {
        return newLazyStream(fn.apply(head()), () -> tail().map(fn));
    }

    public static <A> Stream<A> join(Stream<Stream<A>> nestedStream) {
        return newLazyStream(
            nestedStream.head().head(),
            () -> join(nestedStream.tail().map(Stream::tail)));
    }

    public <B> Stream<B> flatMap(Function<A, Stream<B>> fn) {
        return join(map(fn));
    }

    public static Stream<Integer> range(int from, int step) {
        return unfold(x -> x + step, from);
    }

    public static Stream<Integer> range(int from) {
        return range(from, 1);
    }

    @SafeVarargs
    public static <A> Stream<A> cycle(A... as) {
        if (as.length == 1) {
            return repeat(as[0]);
        } else {
            Stream<A> result = newLazyStream(as[as.length - 1], () -> cycle(as));
            for (int i = as.length - 1; i > 0; i--) {
                result = newStream(as[i - 1], result);
            }
            return result;
        }
    }

    public static <A> Stream<A> append(__<List.µ, A> list, __<µ, A> stream) {
        final List<A> listOne = asList(list);
        final Stream<A> streamTwo = asStream(stream);
        return listOne.isEmpty() ? streamTwo : newLazyStream(listOne.head(), () -> append(listOne.tail(), streamTwo));
    }

    public static <A> Stream<A> interleave(__<µ, A> one, __<µ, A> two) {
        return newLazyStream(asStream(one).head(), () -> interleave(two, asStream(one).tail()));
    }

    public static <A, B> Stream<T2<A, B>> zip(__<µ, A> streamA, __<µ, B> streamB) {
        return zipWith(T2::of, streamA, streamB);
    }

    public static <A, B, C> Stream<T3<A, B, C>> zip(__<µ, A> streamA, __<µ, B> streamB, __<µ, C> streamC) {
        return zipWith(T3::of, streamA, streamB, streamC);
    }

    public static <A, B, C, D> Stream<T4<A, B, C, D>> zip(__<µ, A> streamA, __<µ, B> streamB, __<µ, C> streamC, __<µ, D> streamD) {
        return zipWith(T4::of, streamA, streamB, streamC, streamD);
    }

    public static <A, B, C> Stream<C> zipWith(BiFunction<A, B, C> fn, __<µ, A> streamA, __<µ, B> streamB) {
        final Stream<A> sA = asStream(streamA);
        final Stream<B> sB = asStream(streamB);
        return newLazyStream(fn.apply(sA.head(), sB.head()), () -> zipWith(fn, sA.tail(), sB.tail()));
    }

    public static <A, B, C, D> Stream<D> zipWith(F3<A, B, C, D> fn, __<µ, A> streamA, __<µ, B> streamB, __<µ, C> streamC) {
        final Stream<A> sA = asStream(streamA);
        final Stream<B> sB = asStream(streamB);
        final Stream<C> sC = asStream(streamC);
        return newLazyStream(fn.apply(sA.head(), sB.head(), sC.head()), () -> zipWith(fn, sA.tail(), sB.tail(), sC.tail()));
    }

    public static <A, B, C, D, E> Stream<E> zipWith(F4<A, B, C, D, E> fn, __<µ, A> streamA, __<µ, B> streamB, __<µ, C> streamC, __<µ, D> streamD) {
        final Stream<A> sA = asStream(streamA);
        final Stream<B> sB = asStream(streamB);
        final Stream<C> sC = asStream(streamC);
        final Stream<D> sD = asStream(streamD);

        return newLazyStream(fn.apply(sA.head(), sB.head(), sC.head()).apply(sD.head()),
            () -> zipWith(fn, sA.tail(), sB.tail(), sC.tail(), sD.tail()));
    }

    public static <A, B> T2<Stream<A>, Stream<B>> unzip(Stream<T2<A, B>> streamAB) {
        return T2.of(streamAB.map(T2::_1), streamAB.map(T2::_2));
    }

    public static <A, B, C> T3<Stream<A>, Stream<B>, Stream<C>> unzip3(Stream<T3<A, B, C>> streamABC) {
        return T3.of(streamABC.map(T3::_1), streamABC.map(T3::_2), streamABC.map(T3::_3));
    }

    public static <A, B, C, D> T4<Stream<A>, Stream<B>, Stream<C>, Stream<D>> unzip4(Stream<T4<A, B, C, D>> streamABCD) {
        return T4.of(streamABCD.map(T4::_1), streamABCD.map(T4::_2), streamABCD.map(T4::_3), streamABCD.map(t -> t._4()));
    }

    public static final StreamMonad monad = new StreamMonad() {
    };

    @Override
    public Iterator<A> iterator() {
        return new Iterator<A>() {

            private Stream<A> stream = Stream.this;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                A a = stream.head();
                stream = stream.tail();
                return a;
            }
        };
    }

    public java.util.stream.Stream<A> javaStream() {
        return StreamSupport.stream(spliterator(), false);
    }
}