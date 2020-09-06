package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq1;
import org.highj.data.instance.list.*;
import org.highj.data.instance.list.ListEq1;
import org.highj.data.ord.Ord1;
import org.highj.function.F3;
import org.highj.function.F4;
import org.highj.function.Strings;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.comonad.Extend;
import org.highj.typeclass1.monad.MonadPlus;
import org.highj.typeclass1.unfoldable.Unfoldable;
import org.highj.util.ArrayUtils;
import org.highj.util.Iterables;
import org.highj.util.Lazy;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.*;
import static java.util.Spliterator.IMMUTABLE;

/**
 * Immutable list implementation (a.k.a. immutable Stack).
 * <p>
 * This implementation is usually strict, but permits lazy behavior as well.
 *
 * @param <A> the element type
 */
public abstract class List<A> implements __<List.µ, A>, Iterable<A>, Function<Integer, Maybe<A>> {

    public interface µ {
    }

    private final static List<?> NIL = new List<Object>() {

        @Override
        public Maybe<Object> maybeHead() {
            return Maybe.Nothing();
        }

        @Override
        public Maybe<List<Object>> maybeTail() {
            return Maybe.Nothing();
        }
    };

    private List() {
    }

    /**
     * Retrieves an element at the given index, if possible.
     *
     * @param index the index
     * @return the element wrapped in a Just, or Nothing if the index is out of bounds
     */
    public Maybe<A> apply(Integer index) {
        List<A> current = this;
        while (!current.isEmpty() && index > 0) {
            current = current.tail();
            index--;
        }
        return (index < 0 || current.isEmpty())
                ? Maybe.Nothing()
                : Maybe.Just(current.head());
    }

    /**
     * Widens the type of the list.
     * <p>
     * ... which is safe, as read-only data structures are contravariant.
     *
     * @param list      the original list
     * @param <Super_A> the required super type
     * @param <A>       the original type
     * @return the same list with a widened type
     */
    @SuppressWarnings("unchecked")
    public static <Super_A, A extends Super_A> List<Super_A> contravariant(List<A> list) {
        return (List) list;
    }

    /**
     * Constructs an empty list, synonymous to {@link List#Nil}.
     *
     * @param <A> the element type
     * @return the list
     */
    public static <A> List<A> empty() {
        return Nil();
    }

    /**
     * Constructs an empty list, synonymous to {@link List#Nil}.
     *
     * @param <A> the element type
     * @return the list
     */
    public static <A> List<A> of() {
        return Nil();
    }

    /**
     * Constructs a list containing the given values.
     *
     * @param as  the elements
     * @param <A> the element type
     * @return the list
     */
    @SafeVarargs
    public static <A> List<A> of(A... as) {
        return List.<A>Nil().plus(as);
    }

    public static List<Boolean> of(boolean[] as) {
        return of(ArrayUtils.box(as));
    }

    public static List<Byte> of(byte[] as) {
        return of(ArrayUtils.box(as));
    }

    public static List<Character> of(char[] as) {
        return of(ArrayUtils.box(as));
    }

    public static List<Short> of(short[] as) {
        return of(ArrayUtils.box(as));
    }

    public static List<Integer> of(int[] as) {
        return of(ArrayUtils.box(as));
    }

    public static List<Long> of(long[] as) {
        return of(ArrayUtils.box(as));
    }

    public static List<Float> of(float[] as) {
        return of(ArrayUtils.box(as));
    }

    public static List<Double> of(double[] as) {
        return of(ArrayUtils.box(as));
    }

    /**
     * Constructs a list containing the given values.
     *
     * @param as  the {@link Iterable} containing the elements
     * @param <A> the element type
     * @return the list
     */
    public static <A> List<A> fromIterable(Iterable<A> as) {
        List<A> result = Nil();
        for (A a : as) {
            result = result.plus(a);
        }
        return result.reverse();
    }

    /**
     * Constructs a list from a given Java collection list.
     * <p>
     * This is slightly more efficient than using the {@link Iterable} version.
     *
     * @param as  the {@link List} containing the elements
     * @param <A> the element type
     * @return the list
     */
    public static <A> List<A> fromJavaList(java.util.List<A> as) {
        List<A> result = Nil();
        for (int i = as.size(); i > 0; i--) {
            result = result.plus(as.get(i - 1));
        }
        return result;
    }

    /**
     * Constructs a list from the first element and an existing list in a strict fashion.
     *
     * @param head the first element
     * @param tail the remaining elements
     * @param <A>  the element type
     * @return the list
     */
    public static <A> List<A> Cons(final A head, final List<A> tail) {
        return new List<A>() {

            @Override
            public Maybe<A> maybeHead() {
                return Maybe.Just(head);
            }

            @Override
            public Maybe<List<A>> maybeTail() {
                return Maybe.Just(tail);
            }

            @Override
            public A head() throws NoSuchElementException {
                return head;
            }

            @Override
            public List<A> tail() throws NoSuchElementException {
                return tail;
            }
        };
    }

    /**
     * Constructs a list from the first element and an existing list in a lazy fashion.
     *
     * @param head         the first element
     * @param tailSupplier a {@link Supplier} producing the remaining elements
     * @param <A>          the element type
     * @return the list
     */
    public static <A> List<A> Cons$(final A head, final Supplier<List<A>> tailSupplier) {
        return new List<A>() {

            @Override
            public A head() throws NoSuchElementException {
                return head;
            }

            @Override
            public List<A> tail() throws NoSuchElementException {
                return tailSupplier.get();
            }

            @Override
            public Maybe<A> maybeHead() {
                return Maybe.Just(head);
            }

            @Override
            public Maybe<List<A>> maybeTail() {
                return Maybe.Just$(tailSupplier);
            }
        };
    }

    /**
     * Constructs an infinite list from a {@link Stream} in a lazy fashion.
     *
     * @param stream the stream
     * @param <A>    the element type
     * @return the list
     */
    public static <A> List<A> fromStream(final Stream<A> stream) {
        return new List<A>() {

            @Override
            public A head() throws NoSuchElementException {
                return stream.head();
            }

            @Override
            public List<A> tail() throws NoSuchElementException {
                return List.fromStream(stream.tail());
            }

            @Override
            public Maybe<A> maybeHead() {
                return Maybe.Just(head());
            }

            @Override
            public Maybe<List<A>> maybeTail() {
                return Maybe.Just(tail());
            }
        };
    }

    /**
     * Constructs a list containing the given element as head and the current list as tail.
     *
     * @param a the new head
     * @return the list
     */
    public List<A> plus(A a) {
        assert (a != null);
        return Cons(a, this);
    }

    /**
     * Constructs a list containing the given elements as successive heads and the current list as tail.
     *
     * @param as the new elements to be put in front
     * @return the new list
     */
    @SafeVarargs
    public final List<A> plus(A... as) {
        List<A> result = this;
        for (A a : Iterables.reverseIterable(as)) {
            result = result.plus(a);
        }
        return result;
    }

    /**
     * Constructs a list where the first occurrence of the given element (if there is any) is removed.
     *
     * @param a the element to be removed
     * @return the list
     */
    public List<A> minus(A a) {
        List<A> heads = Nil();
        List<A> current = this;
        while (!current.isEmpty() && !current.head().equals(a)) {
            heads = heads.plus(current.head());
            current = current.tail();
        }
        if (current.isEmpty()) {
            return this;
        } else {
            current = current.tail();
            while (!heads.isEmpty()) {
                current = current.plus(heads.head());
                heads = heads.tail();
            }
            return current;
        }
    }

    /**
     * Constructs a list where all occurrences of the given elements (if there are any) are removed in a lazy fashion.
     *
     * @param as the elements to be removed
     * @return the list
     */
    @SafeVarargs
    public final List<A> minusAll(A... as) {
        if (isEmpty()) {
            return this;
        }
        A head = head();
        final List<A> tail = tail();
        for (A a : as) {
            if (head.equals(a)) {
                return tail.minusAll(as);
            }
        }
        return Cons$(head, () -> tail.minusAll(as));
    }

    /**
     * Constructs an empty list, synonymous to {@link List#empty}.
     *
     * @param <A> the element type
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public static <A> List<A> Nil() {
        return (List) NIL;
    }

    /**
     * Retrieves the head of the list, if there is any.
     *
     * @return the head wrapped in Just, or Nothing if the list ist empty
     */
    public abstract Maybe<A> maybeHead();

    /**
     * Retrieves the tail of the list, if there is any.
     *
     * @return the tail wrapped in Just, or Nothing if the list ist empty
     */
    public abstract Maybe<List<A>> maybeTail();

    /**
     * Retrieves the head of the list.
     *
     * @return the head
     * @throws NoSuchElementException for empty list
     */
    public A head() throws NoSuchElementException {
        return maybeHead().get();
    }

    /**
     * Retrieves the tail of the list.
     *
     * @return the tail
     * @throws NoSuchElementException for empty list
     */
    public List<A> tail() throws NoSuchElementException {
        return maybeTail().get();
    }

    /**
     * Checks if a list is empty (a.k.a Nil).
     *
     * @return true when empty
     */
    public boolean isEmpty() {
        return this == NIL;
    }

    /**
     * Retrieves an element at the given index.
     *
     * @param index the index of the element
     * @return the element
     * @throws IndexOutOfBoundsException when index is negative or bigger than (size - 1)
     */
    public A get(int index) throws IndexOutOfBoundsException {
        return apply(index).getOrException(IndexOutOfBoundsException.class, "Index: " + index);
    }

    /**
     * Checks whether the list contains the given value, using {@link Object#equals}.
     *
     * @param value the value to check against
     * @return the result of the check
     */
    public boolean contains(A value) {
        return contains(value::equals);
    }

    /**
     * Checks whether the list contains an element satisfying the predicate.
     *
     * @param predicate the predicate to check against
     * @return the result of the check
     */
    public boolean contains(Predicate<? super A> predicate) {
        for (A a : this) {
            if (predicate.test(a)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of elements satisfying a predicate.
     *
     * @param predicate the predicate to test for
     * @return the number of occurrences
     */
    public int count(Predicate<? super A> predicate) {
        int result = 0;
        for (A a : this) {
            if (predicate.test(a)) {
                result++;
            }
        }
        return result;
    }

    /**
     * Counts the number of occurrences of a given elements.
     *
     * @param value the element
     * @return the number of occurrences
     */
    public int count(A value) {
        int result = 0;
        for (A a : this) {
            if (a.equals(value)) {
                result++;
            }
        }
        return result;
    }

    /**
     * Constructs a new list containing n elements of the current list (or less if shorter) in a lazy fashion.
     *
     * @param n the number of elements to take
     * @return the list
     */
    public List<A> take(final int n) {
        return n <= 0 || isEmpty() ? Nil() : Cons$(head(), () -> tail().take(n - 1));
    }

    /**
     * Constructs a new list containing the leading elements of the list as long as they satisfy the given predicate
     * in a lazy fashion.
     *
     * @param predicate the predicate to test against
     * @return the list
     */
    public List<A> takeWhile(final Predicate<? super A> predicate) {
        return isEmpty() || !predicate.test(head()) ? Nil() : Cons$(head(), () -> tail().takeWhile(predicate));
    }


    /**
     * Constructs a new list without the leading n elements of the current list (or less if shorter).
     *
     * @param n the number of elements to take
     * @return the list
     */
    public List<A> drop(int n) {
        List<A> result = this;
        while (n-- > 0 && !result.isEmpty()) {
            result = result.tail();
        }
        return result;
    }

    /**
     * Constructs a new list without the leading elements of the current list which satisfy the given predicate.
     *
     * @param predicate the predicate to test against
     * @return the list
     */
    public List<A> dropWhile(Predicate<? super A> predicate) {
        List<A> result = this;
        while (!result.isEmpty() && predicate.test(result.head())) {
            result = result.tail();
        }
        return result;
    }

    /**
     * Retrieves the last element of a list, if there is any.
     * <p>
     * Doesn't terminate for infinite lists.
     *
     * @return the last element
     * @throws NoSuchElementException for empty list
     */
    public A last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        List<A> current = this;
        List<A> next;
        while (!(next = current.tail()).isEmpty()) {
            current = next;
        }
        return current.head();
    }

    /**
     * Retrieves the last element of a list, if there is any.
     * <p>
     * Doesn't terminate for infinite lists.
     *
     * @return the last element wrapped in a Just, or Nothing
     */
    public Maybe<A> maybeLast() {
        return Maybe.JustWhenTrue(!isEmpty(), this::last);
    }

    /**
     * Constructs a list containing all elements except the last one, if possible.
     * <p>
     * Doesn't terminate for infinite lists.
     *
     * @return the list
     * @throws NoSuchElementException for empty list
     */
    public List<A> init() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail().isEmpty() ? tail() : Cons(head(), tail().init());
    }

    /**
     * Constructs a list containing all elements except the last one, if possible.
     * <p>
     * Doesn't terminate for infinite lists.
     *
     * @return the list wrapped in Just, or Nothing
     */
    public Maybe<List<A>> maybeInit() {
        return Maybe.JustWhenTrue(!isEmpty(), this::init);
    }


    /**
     * Constructs a list containing all elements except the last one, if possible, in a lazy fashion.
     * <p>
     * Doesn't terminate for infinite lists.
     *
     * @return the list
     */
    public List<A> initLazy() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail().isEmpty() ? tail() : Cons$(head(), () -> tail().init());
    }

    /**
     * Constructs a list of all suffixes of the current list, starting with the longest.
     *
     * @return the suffixes
     */
    public List<List<A>> tails() {
        return Cons(this, isEmpty() ? empty() : tail().tails());
    }

    /**
     * Constructs a list of all suffixes of the current list, starting with the longest, in a lazy fashion.
     *
     * @return the suffixes
     */
    public List<List<A>> tailsLazy() {
        return Cons$(this, () -> isEmpty() ? empty() : tail().tails());
    }

    /**
     * Calculates the length of the list.
     * <p>
     * Doesn't terminate for infinite lists.
     *
     * @return the size
     */
    public int size() {
        int result = 0;
        for (A a : this) {
            result++;
        }
        return result;
    }

    @Override
    public Iterator<A> iterator() {
        return new Iterator<A>() {

            private List<A> list = List.this;

            @Override
            public boolean hasNext() {
                return !list.isEmpty();
            }

            @Override
            public A next() {
                A a = list.head();
                list = list.tail();
                return a;
            }
        };
    }

    /**
     * Converts the list to a {@link java.util.List}.
     *
     * @return the {@link java.util.List}
     */
    public java.util.List<A> toJList() {
        java.util.List<A> result = new ArrayList<>();
        for (A a : this) {
            result.add(a);
        }
        return result;
    }

    /**
     * Converts the list to a {@link java.util.stream.Stream}
     *
     * @return the stream
     */
    public java.util.stream.Stream<A> toJavaStream() {
        return StreamSupport.stream(
            Spliterators.spliterator(iterator(), 0L, NONNULL + IMMUTABLE),
            false);

    }

    /**
     * Constructs a bounded list of integers in arithmetic progression in a lazy fashion.
     *
     * @param from the start value
     * @param step the difference between values
     * @param to   the inclusive upper bound
     * @return the list
     */
    public static List<Integer> range(final int from, final int step, final int to) {
        return (step > 0 && from <= to || step < 0 && from >= to)
                ? Cons$(from, () -> range(from + step, step, to))
                : List.<Integer>Nil();
    }

    /**
     * Constructs an unbounded list of integers in arithmetic progression in a lazy fashion.
     *
     * @param from the start value
     * @param step the difference between values
     * @return the list
     */
    public static List<Integer> range(final int from, final int step) {
        return Cons$(from, () -> range(from + step, step));
    }

    /**
     * Constructs an unbounded list of integers counting up by one.
     *
     * @param from the start value
     * @return the list
     */
    public static List<Integer> range(final int from) {
        return range(from, 1);
    }

    /**
     * Constructs an infinite list repeating the given elements.
     *
     * @param as  the elements
     * @param <A> the element type
     * @return the list
     */
    @SafeVarargs
    public static <A> List<A> cycle(final A... as) {
        if (as.length == 0) {
            throw new NoSuchElementException();
        }
        final Lazy<List<A>> ls = Lazy.newLazy();
        List<A> result = Cons$(as[as.length - 1], ls);
        for (int i = as.length - 1; i > 0; i--) {
            result = Cons(as[i - 1], result);
        }
        ls.set(result);
        return result;
    }

    /**
     * Constructs a list repeating the given element n times.
     * @param n the length of the list
     * @param a the repeating element
     * @param <A> the element type
     * @return the list
     */
    public static <A> List<A> replicate(int n, A a) {
        return cycle(a).take(n);
    }

    /**
     * Appends two lists in a lazy fashion.
     *
     * @param one the first list
     * @param two the second list
     * @param <A> the element type
     * @return the resulting list
     */
    public static <A> List<A> append(List<A> one, List<A> two) {
        if (two.isEmpty()) {
            return one;
        } else if (one.isEmpty()) {
            return two;
        } else {
            return Cons$(one.head(), () -> append(one.tail(), two));
        }
    }

    /**
     * The String representation of a list.
     *
     * Doesn't terminate for infinite lists.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return Strings.mkEnclosed("List(", ",", ")", this);
    }

    //won't terminate for equal infinite Lists
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof List) {
            List<?> that = (List) o;
            return this.maybeHead().equals(that.maybeHead()) && this.maybeTail().equals(that.maybeTail());
        }
        return false;
    }

    //won't terminate for infinite Lists
    @Override
    public int hashCode() {
        int hc = 17;
        for (A a : this) {
            hc = 5 * hc + 37 * a.hashCode();
        }
        return hc;
    }

    /**
     * Transforms the list using a given function in a lazy fashion.
     *
     * @param fn the transformation function
     * @param <B> the result type
     * @return the list
     */
    public <B> List<B> map(Function<? super A, ? extends B> fn) {
        return isEmpty()
                ? Nil()
                : List.Cons$(fn.apply(head()), () -> tail().map(fn));
    }

    /**
     * Calculates a new list as concatenation of the results after applying the given function to all elements.
     *
     * This operation is also known as "bind" or "flatMap".
     *
     * @param fn the function
     * @param <B> the result type
     * @return the list
     */
    public <B> List<B> concatMap(Function<? super A, List<? extends B>> fn) {
        if (isEmpty()) {
            return Nil();
        } else {
            List<B> headList = List.contravariant(fn.apply(head()));
            return List.append(headList, tail().concatMap(fn));
        }
    }

    /**
     * Calculates a set of all elements satisfying a given condition.
     *
     * @param predicate the {@link Predicate} used for testing
     * @return the filtered list
     */
    public List<A> filter(final Predicate<? super A> predicate) {
        if (isEmpty()) {
            return this;
        } else if (predicate.test(head())) {
            return Cons$(head(), () -> tail().filter(predicate));
        } else {
            return tail().filter(predicate);
        }
    }

    /**
     * Constructs a list from a stack, without reversing the elements.
     *
     * This is thought as quick helper function.
     *
     * @param stack the stack
     * @param <A> the element type
     * @return the list
     */
    public static <A> List<A> buildFromStack(Stack<A> stack) {
        List<A> result = Nil();
        while (!stack.isEmpty()) {
            result = result.plus(stack.pop());
        }
        return result;
    }

    /**
     * Constructs a list containing the elements in reverse order.
     *
     * @return the list
     */
    public List<A> reverse() {
        List<A> result = Nil();
        for (A a : this) {
            result = result.plus(a);
        }
        return result;
    }

    /**
     * Performs a right fold over the list.
     *
     * That means the last value of the list is combined with the starting value using the given function,
     * then the result is combined with the second to last value etc, until the whole list is consumed that way.
     *
     * @param fn combination function
     * @param startValue starting value
     * @param <B> result type
     * @return result of the folding operation
     */
    public <B> B foldr(BiFunction<A, B, B> fn, B startValue) {
        return reverse().foldl(startValue, (b,a) -> fn.apply(a,b));
    }

    /**
     * Performs a left fold over the list.
     *
     * That means the starting value is combined with the first value of the list using the given function,
     * then the result is combined with the second list value etc, until the whole list is consumed that way.
     *
     * @param startValue starting value
     * @param fn combination function
     * @param <B> result type
     * @return result of the folding operation
     */
    public <B> B foldl(B startValue, BiFunction<B, A, B> fn) {
        B result = startValue;
        for (A a : this) {
            result = fn.apply(result, a);
        }
        return result;
    }

    /**
     * Combines two lists to a list of pairs.
     *
     * Trailing elements of the longer list are discarded.
     *
     * @param listA the first list
     * @param listB the second list
     * @param <A> the element type of the first list
     * @param <B>  the element type of the second list
     * @return the combined list
     */
    public static <A, B> List<T2<A, B>> zip(List<A> listA, List<B> listB) {
        return zipWith(listA, listB, T2::of);
    }

    /**
     * Combines three lists to a list of triples.
     *
     * Trailing elements of the longer lists are discarded.
     *
     * @param listA the first list
     * @param listB the second list
     * @param listC the third list
     * @param <A> the element type of the first list
     * @param <B>  the element type of the second list
     * @param <C>  the element type of the third list
     * @return the combined list
     */
    public static <A, B, C> List<T3<A, B, C>> zip(List<A> listA, List<B> listB, List<C> listC) {
        return zipWith(listA, listB, listC, T3::of);
    }

    /**
     * Combines four lists to a list of quadruples.
     *
     * Trailing elements of the longer lists are discarded.
     *
     * @param listA the first list
     * @param listB the second list
     * @param listC the third list
     * @param listD the fourth list
     * @param <A> the element type of the first list
     * @param <B>  the element type of the second list
     * @param <C>  the element type of the third list
     * @param <D>  the element type of the fourth list
     * @return the combined list
     */
    public static <A, B, C, D> List<T4<A, B, C, D>> zip(List<A> listA, List<B> listB, List<C> listC, List<D> listD) {
        return zipWith(listA, listB, listC, listD, T4::of);
    }

    /**
     * Combines two lists using a combination function.
     *
     * Trailing elements of the longer list are discarded.
     *
     * @param listA the first list
     * @param listB the second list
     * @param fn the combination function
     * @param <A> the element type of the first list
     * @param <B>  the element type of the second list
     * @param <C>  the result type
     * @return the combined list
     */
    public static <A, B, C> List<C> zipWith(final List<A> listA, final List<B> listB, final BiFunction<A, B, C> fn) {
        return listA.isEmpty() || listB.isEmpty() ? Nil() :
                List.Cons$(fn.apply(listA.head(), listB.head()), () -> zipWith(listA.tail(), listB.tail(), fn));
    }

    /**
     * Combines three lists using a combination function.
     *
     * Trailing elements of the longer list are discarded.
     *
     * @param listA the first list
     * @param listB the second list
     * @param listC the third list
     * @param fn the combination function
     * @param <A> the element type of the first list
     * @param <B>  the element type of the second list
     * @param <C>  the element type of the third list
     * @param <D>  the result type
     * @return the combined list
     */
    public static <A, B, C, D> List<D> zipWith(final List<A> listA, final List<B> listB, final List<C> listC, final F3<A, B, C, D> fn) {
        return listA.isEmpty() || listB.isEmpty() || listC.isEmpty() ? Nil() :
                List.Cons$(fn.apply(listA.head(), listB.head(), listC.head()), () -> zipWith(listA.tail(), listB.tail(), listC.tail(), fn));
    }

    /**
     * Combines four lists using a combination function.
     *
     * Trailing elements of the longer list are discarded.
     *
     * @param listA the first list
     * @param listB the second list
     * @param listC the third list
     * @param listD the fourth list
     * @param fn the combination function
     * @param <A> the element type of the first list
     * @param <B>  the element type of the second list
     * @param <C>  the element type of the third list
     * @param <D>  the element type of the fourth list
     * @param <E>  the result type
     * @return the combined list
     */
    public static <A, B, C, D, E> List<E> zipWith(final List<A> listA, final List<B> listB, final List<C> listC, final List<D> listD, final F4<A, B, C, D, E> fn) {
        return listA.isEmpty() || listB.isEmpty() || listC.isEmpty() || listD.isEmpty() ? Nil() :
                List.Cons$(fn.apply(listA.head(), listB.head(), listC.head(), listD.head()), () -> zipWith(listA.tail(), listB.tail(), listC.tail(), listD.tail(), fn));
    }

    /**
     * Splits a list of pairs in two lists.
     *
     * @param listAB the list of pairs
     * @param <A> element type of the first value of the pair
     * @param <B> element type of the second value of the pair
     * @return a pair containing the resulting lists
     */
    public static <A, B> T2<List<A>, List<B>> unzip(List<T2<A, B>> listAB) {
        return T2.of(listAB.map(T2::_1), listAB.map(T2::_2));
    }

    /**
     * Splits a list of triples in three lists.
     *
     * @param listABC the list of triples
     * @param <A> element type of the first value of the triple
     * @param <B> element type of the second value of the triple
     * @param <C> element type of the third value of the triple
     * @return a triple containing the resulting lists
     */
    public static <A, B, C> T3<List<A>, List<B>, List<C>> unzip3(List<T3<A, B, C>> listABC) {
        return T3.of(listABC.map(T3::_1), listABC.map(T3::_2), listABC.map(T3::_3));
    }

    /**
     * Splits a list of quadruples in four lists.
     *
     * @param listABCD the list of quadruples
     * @param <A> element type of the first value of the quadruple
     * @param <B> element type of the second value of the quadruple
     * @param <C> element type of the third value of the quadruple
     * @param <D> element type of the fourth value of the quadruple
     * @return a quadruple containing the resulting lists
     */
    public static <A, B, C, D> T4<List<A>, List<B>, List<C>, List<D>> unzip4(List<T4<A, B, C, D>> listABCD) {
        return T4.of(listABCD.map(T4::_1), listABCD.map(T4::_2), listABCD.map(T4::_3), listABCD.map(T4::_4));
    }

    /**
     * Concatenates all elements contained in a list of lists.
     *
     * A.k.a. "flatten".
     *
     * @param list the list of lists
     * @param <A> the element type
     * @return the list of elements
     */
    public static <A> List<A> join(List<List<A>> list) {
        Stack<A> stack = new Stack<>();
        for (List<A> innerList : list) {
            for (A a : innerList) {
                stack.push(a);
            }
        }
        return buildFromStack(stack);
    }

    /**
     * Sorts the list.
     *
     * @param comparator the {@link Comparator} used for sorting
     * @return the sorted list
     */
    public List<A> sort(Comparator<A> comparator) {
        java.util.List<A> jList = toJList();
        jList.sort(comparator);
        return List.fromJavaList(jList);
    }

    /**
     * Puts a given element between every two elements of the original list.
     *
     * E.g. <code>List.of("a","b","c").intersperse("x").equals(List.of("a","x","b","x","c"))</code>
     *
     * @param a the interspersing value
     * @return the list
     */
    public List<A> intersperse(final A a) {
        return isEmpty() || tail().isEmpty() ? this : Cons$(this.head(), () -> Cons(a, tail().intersperse(a)));
    }

    /**
     * Constructs a {@link Zipper} focused at the start position.
     *
     * @return the zipper
     */
    public Zipper<A> toZipper() {
        return Zipper.fromList(this);
    }


    /**
     * The {@link org.highj.typeclass1.foldable.Traversable} instance of lists.
     */
    public static final ListTraversable traversable = new ListTraversable() {
    };

    /**
     * The {@link org.highj.typeclass1.monad.Applicative} instance of lists using {@link List#zip}
     * as combining operation.
     */
    public static final ZipApplicative zipApplicative = new ZipApplicative() {
    };

    /**
     * The {@link MonadPlus} instance of lists.
     */
    public static final ListMonadPlus monadPlus = new ListMonadPlus() {
    };

    /**
     * The {@link Unfoldable} instance of lists.
     */
    public static final ListUnfoldable unfoldable = new ListUnfoldable() {
    };

    /**
     * The {@link Extend} instance of lists.
     */
    public static final ListExtend extend = new ListExtend() {
    };

    /**
     * The {@link Eq1} instance of lists.
     */
    public static final ListEq1 eq1 = new ListEq1() {};

   /**
     * The {@link Ord1} instance of lists.
     */
    public static final ListOrd1 ord1 = new ListOrd1() {};

    /**
     * The list {@link org.highj.typeclass0.group.Monoid} using  {@link List#append} as combining operation.
     * @param <A> the element type of the monoid
     * @return the monoid
     */
    public static <A> Monoid<List<A>> monoid() {
        return Monoid.create(List.empty(), List::append);
    }

}
