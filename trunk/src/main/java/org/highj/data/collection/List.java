package org.highj.data.collection;

import org.highj._;
import org.highj.data.collection.list.ListTraversable;
import org.highj.data.collection.list.ListMonadPlus;
import org.highj.data.collection.list.ListMonoid;
import org.highj.data.collection.list.ZipApplicative;
import org.highj.data.functions.Strings;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.util.ArrayUtils;
import org.highj.util.Iterables;
import org.highj.util.Lazy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class List<A> implements _<List.µ, A>, Iterable<A>, Function<Integer, Maybe<A>> {


    public static final class µ {
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

    public Maybe<A> apply(Integer index) {
        List<A> current = this;
        while (!current.isEmpty() && index > 0) {
            current = current.tail();
            index--;
        }
        return (index < 0 || current.isEmpty())
                ? Maybe.<A>Nothing()
                : Maybe.Just(current.head());
    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> narrow(_<µ, A> value) {
        return (List) value;
    }

    @SuppressWarnings("unchecked")
    public static <Super_A, A extends Super_A> List<Super_A> contravariant(List<A> list) {
        return (List) list;
    }

    public static <A> List<A> empty() {
        return nil();
    }

    public static <A> List<A> of() {
        return nil();
    }

    @SafeVarargs
    public static <A> List<A> of(A... as) {
        return List.<A>nil().plus(as);
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

    public static <A> List<A> of(Iterable<A> as) {
        List<A> result = nil();
        for (A a : as) {
            result = result.plus(a);
        }
        return result.reverse();
    }

    public static <A> List<A> of(java.util.List<A> as) {
        List<A> result = nil();
        for (int i = as.size(); i > 0; i--) {
            result = result.plus(as.get(i - 1));
        }
        return result;
    }

    public static <A> List<A> newList(final A head, final List<A> tail) {
        return new List<A>() {

            @Override
            public Maybe<A> maybeHead() {
                return Maybe.Just(head);
            }

            @Override
            public Maybe<List<A>> maybeTail() {
                return Maybe.Just(tail);
            }
        };
    }

    public static <A> List<A> newLazyList(final A head, final Supplier<List<A>> thunkTail) {
        return new List<A>() {

            @Override
            public Maybe<A> maybeHead() {
                return Maybe.Just(head);
            }

            @Override
            public Maybe<List<A>> maybeTail() {
                return Maybe.JustLazy(thunkTail);
            }
        };
    }

    public static <A> List<A> fromStream(final Stream<A> stream) {
        return new List<A>() {

            @Override
            public Maybe<A> maybeHead() {
                return Maybe.Just(stream.head());
            }

            @Override
            public Maybe<List<A>> maybeTail() {
                return Maybe.Just(fromStream(stream.tail()));
            }
        };
    }

    public List<A> plus(A a) {
        return newList(a, this);
    }

    @SafeVarargs
    public final List<A> plus(A... as) {
        List<A> result = this;
        for (A a : Iterables.reverseIterable(as)) {
            result = result.plus(a);
        }
        return result;
    }


    public List<A> minus(A a) {
        List<A> heads = nil();
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
        return newLazyList(head, () -> tail.minusAll(as));
    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> nil() {
        return (List) NIL;
    }

    public abstract Maybe<A> maybeHead();

    public abstract Maybe<List<A>> maybeTail();

    public A head() {
        return maybeHead().get();
    }

    public List<A> tail() {
        return maybeTail().get();
    }

    public boolean isEmpty() {
        return this == NIL;
    }

    public A get(int index) throws IndexOutOfBoundsException {
        return apply(index).getOrError(IndexOutOfBoundsException.class, "Index: " + index);
    }

    public boolean contains(A value) {
        for (A a : this) {
            if (a.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Function<A, Boolean> predicate) {
        for (A a : this) {
            if (predicate.apply(a)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Predicate<? super A> predicate) {
        for (A a : this) {
            if (predicate.test(a)) {
                return true;
            }
        }
        return false;
    }

    public int count(Function<A, Boolean> predicate) {
        int result = 0;
        for (A a : this) {
            if (predicate.apply(a)) {
                result++;
            }
        }
        return result;
    }

    public int count(Predicate<? super A> predicate) {
        int result = 0;
        for (A a : this) {
            if (predicate.test(a)) {
                result++;
            }
        }
        return result;
    }

    public int count(A value) {
        int result = 0;
        for (A a : this) {
            if (a.equals(value)) {
                result++;
            }
        }
        return result;
    }

    public List<A> take(final int n) {
        return n <= 0 || isEmpty() ? nil() : newLazyList(head(), () -> tail().take(n - 1));
    }

    public List<A> takeWhile(final Function<A, Boolean> predicate) {
        return isEmpty() || !predicate.apply(head()) ? nil() : newLazyList(head(), () -> tail().takeWhile(predicate));
    }

    public List<A> takeWhile(final Predicate<? super A> predicate) {
        return isEmpty() || !predicate.test(head()) ? nil() : newLazyList(head(), () -> tail().takeWhile(predicate));
    }

    public List<A> drop(int n) {
        List<A> result = this;
        while (n-- > 0 && !result.isEmpty()) {
            result = result.tail();
        }
        return result;
    }

    public List<A> dropWhile(Function<A, Boolean> predicate) {
        List<A> result = this;
        while (!result.isEmpty() && predicate.apply(result.head())) {
            result = result.tail();
        }
        return result;
    }

    public List<A> dropWhile(Predicate<? super A> predicate) {
        List<A> result = this;
        while (!result.isEmpty() && predicate.test(result.head())) {
            result = result.tail();
        }
        return result;
    }

    public A last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        List<A> current = this;
        List<A> next;
        while(! (next = current.tail()).isEmpty()) {
            current = next;
        }
        return current.head();
    }

    public List<A> init() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail().isEmpty() ? tail() : newList(head(), tail().init());
    }

    public List<A> initLazy() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail().isEmpty() ? tail() : newLazyList(head(), () -> tail().init());
    }


    public List<List<A>> tails() {
        return newList(this, isEmpty() ? empty() : tail().tails());
    }

    public List<List<A>> tailsLazy() {
        return newLazyList(this, (Supplier<List<List<A>>>) () -> isEmpty() ? empty() : tail().tails());
    }

    //won't terminate for infinite Lists
    public int size() {
        int result = 0;
        for (A a : this) {
            result++;
        }
        return result;
    }

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

    public java.util.List<A> toJList() {
        java.util.List<A> result = new ArrayList<>();
        for (A a : this) {
            result.add(a);
        }
        return result;
    }

    public static List<Integer> range(final int from, final int step, final int to) {
        return (step > 0 && from <= to || step < 0 && from >= to)
                ? newLazyList(from, () -> range(from + step, step, to))
                : List.<Integer>nil();
    }


    public static List<Integer> range(final int from, final int step) {
        return newLazyList(from, () -> range(from + step, step));
    }

    public static List<Integer> range(final int from) {
        return range(from, 1);
    }

    @SafeVarargs
    public static <A> List<A> cycle(final A... as) {
        List<A> result = newLazyList(as[as.length - 1], () -> cycle(as));
        for (int i = as.length - 1; i > 0; i--) {
            result = newList(as[i - 1], result);
        }
        return result;
    }

    //for performance reasons
    public static <A> List<A> repeat(final A a) {
        final Lazy<List<A>> ls = Lazy.newLazy();
        ls.set(newLazyList(a, ls::get));
        return ls.get();
    }

    public static <A> List<A> replicate(int n, A a) {
        return repeat(a).take(n);
    }

    public static <A> List<A> append(_<µ, A> one, _<µ, A> two) {
        List<A> listOne = narrow(one);
        List<A> listTwo = narrow(two);
        if (listTwo.isEmpty()) {
            return listOne;
        } else {
            listOne = listOne.reverse();
            while (!listOne.isEmpty()) {
                listTwo = listTwo.plus(listOne.head());
                listOne = listOne.tail();
            }
        }
        return listTwo;
    }

    //won't terminate for infinite Lists
    @Override
    public String toString() {
        return Strings.mkString("List(", ",", ")", this);
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

    public <B> List<B> map(Function<? super A, ? extends B> fn) {
        return isEmpty()
                ? nil()
                : newLazyList(fn.apply(head()), () -> tail().map(fn));
    }

    public <B> List<B> concatMap(Function<? super A, List<? extends B>> fn) {
        if(isEmpty()) {
            return nil();
        } else {
            List<B> headList = List.contravariant(fn.apply(head()));
            return List.append(headList, tail().concatMap(fn));
        }
    }

    public List<A> filter(final Function<A, Boolean> predicate) {
        if (isEmpty()) {
            return this;
        } else if (predicate.apply(head())) {
            return newLazyList(head(), () -> tail().filter(predicate));
        } else {
            return tail().filter(predicate);
        }
    }

    public List<A> filter(final Predicate<? super A> predicate) {
        if (isEmpty()) {
            return this;
        } else if (predicate.test(head())) {
            return newLazyList(head(), () -> tail().filter(predicate));
        } else {
            return tail().filter(predicate);
        }
    }

    public static <A> List<A> buildFromStack(Stack<A> stack) {
        List<A> result = nil();
        while (!stack.isEmpty()) {
            result = result.plus(stack.pop());
        }
        return result;
    }

    public List<A> reverse() {
        List<A> result = nil();
        for (A a : this) {
            result = result.plus(a);
        }
        return result;
    }

    public <B> B foldr(final Function<A, Function<B, B>> fn, final B b) {
        return isEmpty() ? b : fn.apply(head()).apply(tail().foldr(fn, b));
    }

    public <B> B foldl(final B b, final Function<B, Function<A, B>> fn) {
        B result = b;
        for (A a : this) {
            result = fn.apply(result).apply(a);
        }
        return result;
    }

    public static <A, B> List<T2<A, B>> zip(List<A> listA, List<B> listB) {
        return zipWith(listA, listB, (Function<A, Function<B, T2<A, B>>>) a -> b -> T2.of(a, b));
    }

    public static <A, B, C> List<T3<A, B, C>> zip(List<A> listA, List<B> listB, List<C> listC) {
        return zipWith(listA, listB, listC, (Function<A, Function<B, Function<C, T3<A, B, C>>>>) a -> b -> c -> T3.of(a, b, c));
    }

    public static <A, B, C, D> List<T4<A, B, C, D>> zip(List<A> listA, List<B> listB, List<C> listC, List<D> listD) {
        return zipWith(listA, listB, listC, listD, (Function<A, Function<B, Function<C, Function<D, T4<A, B, C, D>>>>>) a -> b -> c -> d -> T4.of(a, b, c, d));
    }

    public static <A, B, C> List<C> zipWith(final List<A> listA, final List<B> listB, final Function<A, Function<B, C>> fn) {
        return listA.isEmpty() || listB.isEmpty() ? nil() :
                newLazyList(fn.apply(listA.head()).apply(listB.head()), () -> zipWith(listA.tail(), listB.tail(), fn));
    }

    public static <A, B, C, D> List<D> zipWith(final List<A> listA, final List<B> listB, final List<C> listC, final Function<A, Function<B, Function<C, D>>> fn) {
        return listA.isEmpty() || listB.isEmpty() || listC.isEmpty() ? nil() :
                newLazyList(fn.apply(listA.head()).apply(listB.head()).apply(listC.head()), () -> zipWith(listA.tail(), listB.tail(), listC.tail(), fn));
    }

    public static <A, B, C, D, E> List<E> zipWith(final List<A> listA, final List<B> listB, final List<C> listC, final List<D> listD, final Function<A, Function<B, Function<C, Function<D, E>>>> fn) {
        return listA.isEmpty() || listB.isEmpty() || listC.isEmpty() || listD.isEmpty() ? nil() :
                newLazyList(fn.apply(listA.head()).apply(listB.head()).apply(listC.head()).apply(listD.head()), () -> zipWith(listA.tail(), listB.tail(), listC.tail(), listD.tail(), fn));
    }

    public static <A, B> T2<List<A>, List<B>> unzip(List<T2<A, B>> listAB) {
        return T2.of(listAB.map(t -> t._1()), listAB.map(t -> t._2()));
    }

    public static <A, B, C> T3<List<A>, List<B>, List<C>> unzip3(List<T3<A, B, C>> listABC) {
        return T3.of(listABC.map(t -> t._1()), listABC.map(t -> t._2()), listABC.map(t -> t._3()));
    }

    public static <A, B, C, D> T4<List<A>, List<B>, List<C>, List<D>> unzip4(List<T4<A, B, C, D>> listABCD) {
        return T4.of(listABCD.map(t -> t._1()), listABCD.map(t -> t._2()), listABCD.map(t -> t._3()), listABCD.map(t -> t._4()));
    }

    public static <A> List<A> join(List<List<A>> list) {
        Stack<A> stack = new Stack<>();
        for (List<A> innerList : list) {
            for (A a : innerList) {
                stack.push(a);
            }
        }
        return buildFromStack(stack);
    }

    public List<A> intersperse(final A a) {
        return isEmpty() || tail().isEmpty() ? this : newLazyList(this.head(), () -> newList(a, tail().intersperse(a)));
    }

    public static final Foldable<µ> foldable = new ListTraversable();

    public static final ZipApplicative zipApplicative = new ZipApplicative();

    public static final ListMonadPlus monadPlus = new ListMonadPlus();

    public static <A> Monoid<List<A>> monoid() {
        return new ListMonoid<>();
    }

}
