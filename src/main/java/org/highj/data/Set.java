package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.instance.set.SetMonadPlus;
import org.highj.data.ord.Ordering;
import org.highj.function.Strings;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.util.ArrayUtils;
import org.highj.util.Iterators;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.*;

/**
 * A crude, hash-based, immutable set implementation.
 * <p>
 * Note that the provided monad instance could be considered a hack, based on the fact that every
 * Java object has a {@link Object#hashCode} and an {@link Object#equals} implementation,
 * which might be rather useless in some cases.
 * </p>
 *
 * @param <A> The element type.
 */
public class Set<A> implements __<Set.µ, A>, Iterable<A>, Predicate<A> {

    public interface µ {
    }

    @SuppressWarnings("unchecked")
    private final static Set<?> EMPTY = new Set<>(Integer.MIN_VALUE, List.Nil(), null, null);

    private final Set<A> left;
    private final Set<A> right;
    private final List<A> bucket;
    private final int hc;

    private Set(int hc, List<A> bucket, Set<A> left, Set<A> right) {
        this.hc = hc;
        this.bucket = bucket;
        this.right = right;
        this.left = left;
    }

    /**
     * Checks whether the set contains a given value.
     *
     * @param value value to check
     * @return true if the set contains the value
     */
    @Override
    public boolean test(A value) {
        if (isEmpty()) {
            return false;
        }

        int vhc = value.hashCode();
        switch (Ordering.compare(vhc, hc)) {
            case LT:
                return left.test(value);
            case GT:
                return right.test(value);
            case EQ:
                return bucket.contains(value);
            default:
                throw new AssertionError();
        }
    }

    private Set<A> withBucket(List<A> newBucket) {
        return bucket == newBucket ? this : new Set<>(hc, newBucket, left, right);
    }
    private Set<A> withLeft(Set<A> newLeft) {
        return left == newLeft ? this : new Set<>(hc, bucket, newLeft, right);
    }
    private Set<A> withRight(Set<A> newRight) {
        return right == newRight ? this : new Set<>(hc, bucket, left, newRight);
    }

    /**
     * Contructs a new set which contains all elements of the current set, as well as the given value.
     *
     * If the value is already there, a reference of the current set will be returned.
     *
     * @param a the value
     * @return the set
     */
    public Set<A> plus(A a) {
        if (isEmpty()) {
            return new Set<>(a.hashCode(), List.of(a), Set.empty(), Set.empty());
        }
        int ahc = a.hashCode();
        switch (Ordering.compare(ahc, hc)) {
            case EQ:
                return bucket.contains(a) ? this : withBucket(bucket.plus(a));
            case LT:
                return withLeft(left.plus(a));
            case GT:
                return withRight(right.plus(a));
            default:
                throw new AssertionError();
        }
    }

    /**
     * Contructs a new set which contains all elements of the current set, except the given value.
     *
     * If the value isn't there, a reference of the current set will be returned.
     *
     * @param a the value
     * @return the set
     */
    public Set<A> minus(A a) {
        if (isEmpty()) {
            return this;
        }
        int ahc = a.hashCode();
        switch (Ordering.compare(ahc, hc)) {
            case EQ:
                List<A> newBucket = bucket.minus(a);
                if (!newBucket.isEmpty()) {
                    return withBucket(newBucket);
                } else if (left.isEmpty()) {
                    return right;
                } else if (right.isEmpty()) {
                    return left;
                } else {
                    T2<Set<A>, Set<A>> pair = right.removeMin();
                    return new Set<>(pair._1().hc, pair._1().bucket, left, pair._2());
                }
            case LT:
                return withLeft(left.minus(a));
            case GT:
                return withRight(right.minus(a));
            default:
                throw new AssertionError();
        }
    }

    private T2<Set<A>, Set<A>> removeMin() {
        if (left.isEmpty()) {
            return T2.of(this, right);
        } else {
            T2<Set<A>, Set<A>> pair = left.removeMin();
            return T2.of(pair._1(), withLeft(pair._2()));
        }
    }

    /**
     * Checks whether the set is empty.
     *
     * @return true if the set is empty, false otherwise
     */
    public boolean isEmpty() {
        return this == EMPTY;
    }

    /**
     * Constructs an empty set.
     *
     * @param <A> the element type.
     * @return the set
     */
    @SuppressWarnings("unchecked")
    public static <A> Set<A> empty() {
        return (Set) EMPTY;
    }

    /**
     * Constructs a set holding a single element.
     *
     * @param a the element
     * @param <A> the element type
     * @return the set
     */
    public static <A> Set<A> of(A a) {
        return Set.<A>empty().plus(a);
    }

    /**
     * Constructs a set containing the given values.
     *
     * @param as the elements
     * @param <A> the element type
     * @return the set
     */
    @SafeVarargs
    public static <A> Set<A> of(A... as) {
        return Set.<A>empty().plus(as);
    }

    public static Set<Boolean> of(boolean[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    public static Set<Byte> of(byte[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    public static Set<Character> of(char[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    public static Set<Short> of(short[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    public static Set<Integer> of(int[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    public static Set<Long> of(long[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    public static Set<Float> of(float[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    public static Set<Double> of(double[] as) {
        return Set.of(ArrayUtils.box(as));
    }

    /**
     * Constructs a set from the given values.
     *
     * @param as an {@link Iterable} containing the elements
     * @param <A> the element type
     * @return the set
     */
    public static <A> Set<A> of(Iterable<A> as) {
        return Set.<A>empty().plus(as);
    }

    /**
     * Constructs a set, which contains the current elements and the given values.
     *
     * @param as the values to be included
     * @return the set
     */
    @SafeVarargs
    public final Set<A> plus(A... as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.plus(a);
        }
        return result;
    }

    /**
     * Constructs a set containing the current elements, except the given values.
     *
     * @param as the values to be excluded
     * @return the set
     */
    @SafeVarargs
    public final Set<A> minus(A... as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.minus(a);
        }
        return result;
    }

    /**
     * Constructs a set, which contains the current elements and the given values.
     *
     * @param as the {@link Iterable} of the values to be included
     * @return the set
     */
    public Set<A> plus(Iterable<A> as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.plus(a);
        }
        return result;
    }

    /**
     * Constructs a set containing the current elements, except the given values.
     *
     * @param as the {@link Iterable} of the values to be excluded
     * @return the set
     */
    public Set<A> minus(Iterable<A> as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.minus(a);
        }
        return result;
    }

    /**
     * Calculates the number of elements.
     *
     * Note that the size is calculated and not cached, so for large sets this may be an expensive operation.
     *
     * @return the number of elements.
     */
    public int size() {
        return isEmpty() ? 0 : left.size() + bucket.size() + right.size();
    }

    //note that the iteration order depends on the insertion order

    /**
     * An iterator over the set.
     *
     * Note that equal sets may have different iteration orders, depending on their respective insertion order.
     *
     * @return the iterator
     */
    public Iterator<A> iterator() {
        return isEmpty() ? Iterators.emptyIterator() : new Iterator<A>() {

            private List<A> bucket = Set.this.bucket;
            private List<Set<A>> todo = addSubSets(List.Nil(), Set.this);

            @Override
            public boolean hasNext() {
                return !bucket.isEmpty() || !todo.isEmpty();
            }

            @Override
            public A next() {
                if (bucket.isEmpty()) {
                    if (todo.isEmpty()) {
                        throw new NoSuchElementException();
                    }
                    Set<A> set = todo.head();
                    todo = addSubSets(todo.tail(), set);
                    bucket = set.bucket;
                }
                A result = bucket.head();
                bucket = bucket.tail();
                return result;
            }

            private List<Set<A>> addSubSets(List<Set<A>> list, Set<A> set) {
                List<Set<A>> result = list;
                result = set.left.isEmpty() ? result : result.plus(set.left);
                result = set.right.isEmpty() ? result : result.plus(set.right);
                return result;
            }
        };
    }

    public java.util.stream.Stream<A> toJavaStream() {
        return StreamSupport.stream(
            Spliterators.spliterator(iterator(), 0L, NONNULL + DISTINCT + IMMUTABLE),
            false);
    }


    /**
     * Generates a {@link String} representation of the set.
     *
     * Note that equal sets may have different representations, depending on their respective insertion order.
     *
     * @return the {@link String} representation
     */
    @Override
    public String toString() {
        return Strings.mkEnclosed("Set(", ",", ")", this);
    }

    /**
     * Transforms the set.
     *
     * Note that in contrast to lists the result set may be smaller than the original one.
     *
     * @param fn the transformation function
     * @param <B> the result type
     * @return the transformed set
     */
    public <B> Set<B> map(Function<? super A, ? extends B> fn) {
        Set<B> result = empty();
        for (A a : this) {
            result = result.plus(fn.apply(a));
        }
        return result;
    }

    /**
     * Calculates a set of all elements satisfying a given condition.
     *
     * @param predicate the {@link Predicate} used for testing
     * @return the filtered set
     */
    public Set<A> filter(Predicate<? super A> predicate) {
        Set<A> result = empty();
        for (A a : this) {
            result = predicate.test(a) ? result.plus(a) : result;
        }
        return result;
    }

    /**
     * Counts the number of elements satisfying a predicate.
     * @param predicate the Predicate to test
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
     * Calculates the union of all sets contained in a given set.
     *
     * Note that in contrast to lists the result set may be smaller than the combined size of the original ones.
     *
     * @param set the nested set
     * @param <A> the element type
     * @return the "flattened" set
     */
    public static <A> Set<A> join(Set<Set<A>> set) {
        Set<A> result = empty();
        for (Set<A> innerSet : set) {
            result = result.plus(innerSet);
        }
        return result;
    }

    /**
     * Calculates a set by applying all the given functions to all elements.
     *
     * @param functions a set of functions
     * @param <B> the result type
     * @return the set
     */
    public <B> Set<B> ap(Set<Function<A, B>> functions) {
        Set<B> result = empty();
        for (Function<A, B> fn : functions) {
            result = result.plus(map(fn));
        }
        return result;
    }

    /**
     * Calculates a new set as union of the results after applying the given function to all elements.
     *
     * This operation is also known as "flatMap".
     *
     * @param fn the function
     * @param <B> the result type
     * @return the set
     */
    public <B> Set<B> bind(Function<? super A, Set<B>> fn) {
        Set<B> result = empty();
        for (A a : this) {
            result = result.plus(fn.apply(a));
        }
        return result;
    }

    /**
     * Converts the set to a mutable {@link java.util.Set}.
     *
     * @return the {@link java.util.Set}
     */
    public java.util.Set<A> toJSet() {
        java.util.Set<A> result = new HashSet<>();
        for (A a : this) {
            result.add(a);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Set) {
            Set<?> that = (Set) o;
            return this.toJSet().equals(that.toJSet());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 19;
        for (A a : this) {
            //we can't multiply by a prime here, as this would result in different hash codes
            //depending on the insertion (and hence iteration) order
            result += a.hashCode();
        }
        return result;
    }

    /**
     * The monad instance of sets, also implementing MonadPlus and MonadRec.
     *
     * Note that this is only possible because in Java every Object has a
     * a {@link Object#hashCode} and an {@link Object#equals} implementation,
     * which might be rather useless in some cases. Haskell has no direct equivalent.
     */
    public static SetMonadPlus monadPlus = new SetMonadPlus() {
    };


    /**
     * Constructs the set monoid.
     *
     * @param <A> the element type
     * @return the monoid
     */
    public static <A> Monoid<Set<A>> monoid() {
        return Monoid.create(Set.empty(), (x, y) -> x.plus(y));
    }
}
