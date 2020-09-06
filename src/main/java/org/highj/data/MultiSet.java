package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.ord.Ordering;
import org.highj.function.Strings;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.util.ArrayUtils;
import org.highj.util.Contracts;
import org.highj.util.Iterators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.*;

/**
 * A crude, hash-based Multiset implementation.
 * <p>
 *
 * @param <A> The element type.
 */
public class MultiSet<A> implements __<MultiSet.µ, A>, Iterable<T2<A, Integer>>, Function<A, Integer> {

    public static class µ {
    }

    private final static MultiSet<?> EMPTY = new MultiSet<>(Integer.MIN_VALUE, List.Nil(), null, null);

    private final MultiSet<A> left;
    private final MultiSet<A> right;
    private final List<T2<A, Integer>> bucket;
    private final int hc;

    private MultiSet(int hc, List<T2<A, Integer>> bucket, MultiSet<A> left, MultiSet<A> right) {
        this.hc = hc;
        this.bucket = bucket;
        this.right = right;
        this.left = left;
    }

    @Override
    public Integer apply(A value) {
        if (isEmpty()) {
            return 0;
        }

        int vhc = value.hashCode();
        switch (Ordering.compare(vhc, hc)) {
            case LT:
                return left.apply(value);
            case GT:
                return right.apply(value);
            case EQ:
                return bucketValue(value);
            default:
                throw new AssertionError();
        }
    }

    private int bucketValue(A a) {
        for (T2<A, Integer> pair : bucket) {
            if (pair._1().equals(a)) return pair._2();
        }
        return 0;
    }

    private List<T2<A, Integer>> bucketWithout(A a) {
        return bucket.filter((T2<A, Integer> pair) -> !pair._1().equals(a));
    }

    public MultiSet<A> set(A a, int value) {
        Contracts.require(value >= 0, "Value must be non-negative.");
        if (value == 0) {
            return remove(a);
        }
        if (isEmpty()) {
            return new MultiSet<>(a.hashCode(), List.of(T2.of(a, value)), MultiSet.<A>empty(), MultiSet.<A>empty());
        }
        int ahc = a.hashCode();
        switch (Ordering.compare(ahc, hc)) {
            case EQ:
                return new MultiSet<>(hc, bucketWithout(a).plus(T2.of(a, value)), left, right);
            case LT:
                MultiSet<A> newLeft = left.plus(a, value);
                return left == newLeft ? this : new MultiSet<>(hc, bucket, newLeft, right);
            case GT:
                MultiSet<A> newRight = right.plus(a, value);
                return right == newRight ? this : new MultiSet<>(hc, bucket, left, newRight);
            default:
                throw new AssertionError();
        }
    }


    public MultiSet<A> plus1(A a) {
        return plus(a, 1);
    }

    public MultiSet<A> plus(T2<A, Integer> pair) {
        return plus(pair._1(), pair._2());
    }

    public MultiSet<A> plus(A a, int value) {
        Contracts.require(value >= 0, "Value can't be negative");

        if (value == 0) {
            return this;
        } else if (isEmpty()) {
            return new MultiSet<>(a.hashCode(), List.of(T2.of(a, value)), MultiSet.<A>empty(), MultiSet.<A>empty());
        }
        int ahc = a.hashCode();
        switch (Ordering.compare(ahc, hc)) {
            case EQ:
                int v = bucketValue(a);
                return new MultiSet<>(hc, bucketWithout(a).plus(T2.of(a, v + value)), left, right);
            case LT:
                MultiSet<A> newLeft = left.plus(a, value);
                return left == newLeft ? this : new MultiSet<>(hc, bucket, newLeft, right);
            case GT:
                MultiSet<A> newRight = right.plus(a, value);
                return right == newRight ? this : new MultiSet<>(hc, bucket, left, newRight);
            default:
                throw new AssertionError();
        }
    }

    public MultiSet<A> minus1(A a) {
        return minus(a, 1);
    }

    public MultiSet<A> minus(T2<A, Integer> pair) {
        return minus(pair._1(), pair._2());
    }

    public MultiSet<A> minus(A a, int value) {
        Contracts.require(value >= 0, "Value can't be negative");
        if (value == 0) {
            return this;
        }
        if (isEmpty()) {
            return this;
        }
        int ahc = a.hashCode();
        switch (Ordering.compare(ahc, hc)) {
            case EQ:
                List<T2<A, Integer>> newBucket = bucketMinus(a, value);
                if (!newBucket.isEmpty()) {
                    return new MultiSet<>(hc, newBucket, left, right);
                } else if (left.isEmpty()) {
                    return right;
                } else if (right.isEmpty()) {
                    return left;
                } else {
                    T2<MultiSet<A>, MultiSet<A>> pair = right.removeMin();
                    return new MultiSet<>(pair._1().hc, pair._1().bucket, left, pair._2());
                }
            case LT:
                MultiSet<A> newLeft = left.minus(a, value);
                return left == newLeft ? this : new MultiSet<>(hc, bucket, newLeft, right);
            case GT:
                MultiSet<A> newRight = right.minus(a, value);
                return right == newRight ? this : new MultiSet<>(hc, bucket, left, newRight);
            default:
                throw new AssertionError();
        }
    }

    public MultiSet<A> remove(A a) {
        int ahc = a.hashCode();
        switch (Ordering.compare(ahc, hc)) {
            case EQ:
                List<T2<A, Integer>> newBucket = bucketWithout(a);
                if (!newBucket.isEmpty()) {
                    return new MultiSet<>(hc, newBucket, left, right);
                } else if (left.isEmpty()) {
                    return right;
                } else if (right.isEmpty()) {
                    return left;
                } else {
                    T2<MultiSet<A>, MultiSet<A>> pair = right.removeMin();
                    return new MultiSet<>(pair._1().hc, pair._1().bucket, left, pair._2());
                }
            case LT:
                MultiSet<A> newLeft = left == null ? null : left.remove(a);
                return left == newLeft ? this : new MultiSet<>(hc, bucket, newLeft, right);
            case GT:
                MultiSet<A> newRight = right == null ? null :  right.remove(a);
                return right == newRight ? this : new MultiSet<>(hc, bucket, left, newRight);
            default:
                throw new AssertionError();
        }
    }

    private List<T2<A, Integer>> bucketMinus(A a, int value) {
        int v = bucketValue(a);
        switch (Ordering.compare(v, value)) {
            case EQ:
            case LT:
                return bucketWithout(a);
            case GT:
                return bucketWithout(a).plus(T2.of(a, v - value));
            default:
                throw new AssertionError();
        }
    }

    private T2<MultiSet<A>, MultiSet<A>> removeMin() {
        if (left.isEmpty()) {
            return T2.of(this, right);
        } else {
            T2<MultiSet<A>, MultiSet<A>> pair = left.removeMin();
            return T2.of(pair._1(), new MultiSet<>(hc, bucket, pair._2(), right));
        }
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @SuppressWarnings("unchecked")
    public static <A> MultiSet<A> empty() {
        return (MultiSet) EMPTY;
    }

    public static <A> MultiSet<A> of1(A a) {
        return MultiSet.<A>empty().plus1(a);
    }

    @SafeVarargs
    public static <A> MultiSet<A> of1(A... as) {
        return MultiSet.<A>empty().plus1(as);
    }

    public static MultiSet<Boolean> of1(boolean[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static MultiSet<Byte> of1(byte[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static MultiSet<Character> of1(char[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static MultiSet<Short> of1(short[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static MultiSet<Integer> of1(int[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static MultiSet<Long> of1(long[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static MultiSet<Float> of1(float[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static MultiSet<Double> of1(double[] as) {
        return MultiSet.of1(ArrayUtils.box(as));
    }

    public static <A> MultiSet<A> of1(Iterable<A> as) {
        return MultiSet.<A>empty().plus1(as);
    }

    @SafeVarargs
    public final MultiSet<A> plus1(A... as) {
        MultiSet<A> result = this;
        for (A a : as) {
            result = result.plus1(a);
        }
        return result;
    }

    @SafeVarargs
    public final MultiSet<A> minus1(A... as) {
        MultiSet<A> result = this;
        for (A a : as) {
            result = result.minus1(a);
        }
        return result;
    }

    public MultiSet<A> plus1(Iterable<A> as) {
        MultiSet<A> result = this;
        for (A a : as) {
            result = result.plus1(a);
        }
        return result;
    }

    public MultiSet<A> plus(Iterable<T2<A, Integer>> as) {
        MultiSet<A> result = this;
        for (T2<A, Integer> pair : as) {
            result = result.plus(pair);
        }
        return result;
    }

    public MultiSet<A> minus1(Iterable<A> as) {
        MultiSet<A> result = this;
        for (A a : as) {
            result = result.minus1(a);
        }
        return result;
    }

    public MultiSet<A> minus(Iterable<T2<A, Integer>> as) {
        MultiSet<A> result = this;
        for (T2<A, Integer> pair : as) {
            result = result.minus(pair);
        }
        return result;
    }

    public int size() {
        return isEmpty() ? 0 : left.size() + bucket.size() + right.size();
    }

    public int totalCount() {
        return isEmpty() ? 0 : left.totalCount() + bucket.foldl(0, (s, t2) -> s + t2._2()) + right.totalCount();
    }

    //we need to output the hashCodes in order, else different insertion orders would lead to different iteration orders
    public Iterator<T2<A, Integer>> iterator() {
        return (isEmpty()) ? Iterators.emptyIterator() :
                new Iterator<T2<A, Integer>>() {

                    private List<T2<A, Integer>> list = List.empty();
                    private List<Either<MultiSet<A>, List<T2<A, Integer>>>> todo = List.of(Either.<MultiSet<A>, List<T2<A, Integer>>>Left(MultiSet.this));

                    @Override
                    public boolean hasNext() {
                        return !list.isEmpty() || !todo.isEmpty();
                    }

                    @Override
                    public T2<A, Integer> next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        while (list.isEmpty()) {
                            Either<MultiSet<A>, List<T2<A, Integer>>> current = todo.head();
                            todo = todo.tail();
                            if (current.isRight()) {
                                list = current.getRight();
                            } else {
                                MultiSet<A> set = current.getLeft();
                                addIfNotEmpty(set.right);
                                todo = todo.plus(Either.Right(set.bucket));
                                addIfNotEmpty(set.left);
                            }
                        }
                        T2<A, Integer> result = list.head();
                        list = list.tail();
                        return result;
                    }

                    private void addIfNotEmpty(MultiSet<A> set) {
                        if (!set.isEmpty()) {
                            todo = todo.plus(Either.Left(set));
                        }
                    }
                };
    }

    public java.util.stream.Stream<T2<A, Integer>> toJavaStream() {
        return StreamSupport.stream(
            Spliterators.spliterator(iterator(), 0L, NONNULL + DISTINCT + IMMUTABLE),
            false);
    }

    @Override
    public String toString() {
        return Strings.mkEnclosed("MultiSet(", ",", ")", this);
    }

    public Set<A> toSet() {
        Set<A> result = Set.empty();
        for (T2<A, Integer> pair : this) {
            result = result.plus(pair._1());
        }
        return result;
    }

    public <B> MultiSet<B> map(Function<? super A, ? extends B> fn) {
        MultiSet<B> result = empty();
        for (T2<A, Integer> pair : this) {
            result = result.plus(fn.apply(pair._1()), pair._2());
        }
        return result;
    }

    //avoids using the expensive iterator
    public java.util.Map<A, Integer> toJMap() {
        java.util.Map<A, Integer> result = new HashMap<>();
        toJMapHelper(result);
        return result;
    }

    private void toJMapHelper(java.util.Map<A, Integer> jMap) {
        if (!isEmpty()) {
            left.toJMapHelper(jMap);
            for (T2<A, Integer> pair : bucket) {
                jMap.put(pair._1(), pair._2());
            }
            right.toJMapHelper(jMap);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof MultiSet) {
            MultiSet<?> that = (MultiSet) o;
            return this.toJMap().equals(that.toJMap());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 19;
        for (T2<A, Integer> pair : this) {
            result += pair.hashCode();
        }
        return result;
    }

    public static <A> Monoid<MultiSet<A>> monoid() {
        return Monoid.create(MultiSet.empty(), MultiSet::plus);
    }
}
