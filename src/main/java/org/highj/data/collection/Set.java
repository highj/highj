package org.highj.data.collection;

import org.highj._;
import org.highj.data.collection.set.SetMonadPlus;
import org.highj.data.compare.Ordering;
import org.highj.data.functions.Strings;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.util.ArrayUtils;
import org.highj.util.Iterators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A crude, hash-based set implementation.
 * <p>
 * Note that the provided monad instance could be considered a hack, based on the fact that every
 * Java object has a {@link Object#hashCode} and an {@link Object#equals} implementation,
 * which might be rather useless in some cases.
 *
 * @param <A> The element type.
 */
public class Set<A> implements _<Set.µ, A>, Iterable<A>, Predicate<A> {

    public interface µ {
    }

    @SuppressWarnings("unchecked")
    private final static Set<?> EMPTY = new Set<>(Integer.MIN_VALUE, List.nil(), null, null);

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

    @SuppressWarnings("unchecked")
    public static <A> Set<A> narrow(_<µ, A> value) {
        return (Set) value;
    }

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

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @SuppressWarnings("unchecked")
    public static <A> Set<A> empty() {
        return (Set) EMPTY;
    }

    public static <A> Set<A> of(A a) {
        return Set.<A>empty().plus(a);
    }

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

    public static <A> Set<A> of(Iterable<A> as) {
        return Set.<A>empty().plus(as);
    }

    @SafeVarargs
    public final Set<A> plus(A... as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.plus(a);
        }
        return result;
    }

    @SafeVarargs
    public final Set<A> minus(A... as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.minus(a);
        }
        return result;
    }

    public Set<A> plus(Iterable<A> as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.plus(a);
        }
        return result;
    }

    public Set<A> minus(Iterable<A> as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.minus(a);
        }
        return result;
    }

    public int size() {
        return isEmpty() ? 0 : left.size() + bucket.size() + right.size();
    }

    //note that the iteration order depends on the insertion order
    public Iterator<A> iterator() {
        return isEmpty() ? Iterators.emptyIterator() : new Iterator<A>() {

            private List<A> bucket = Set.this.bucket;
            private List<Set<A>> todo = addSubSets(List.nil(), Set.this);

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


    @Override
    public String toString() {
        return Strings.mkString("Set(", ",", ")", this);
    }

    public <B> Set<B> map(Function<? super A, ? extends B> fn) {
        Set<B> result = empty();
        for (A a : this) {
            result = result.plus(fn.apply(a));
        }
        return result;
    }

    public Set<A> filter(Predicate<? super A> fn) {
        Set<A> result = empty();
        for (A a : this) {
            result = fn.test(a) ? result.plus(a) : result;
        }
        return result;
    }

    public static <A> Set<A> join(Set<Set<A>> set) {
        Set<A> result = empty();
        for (Set<A> innerSet : set) {
            result = result.plus(innerSet);
        }
        return result;
    }

    public <B> Set<B> ap(Set<Function<A, B>> functions) {
        Set<B> result = empty();
        for (Function<A, B> fn : functions) {
            result = result.plus(map(fn));
        }
        return result;
    }

    public <B> Set<B> bind(Function<? super A, Set<B>> fn) {
        Set<B> result = empty();
        for (A a : this) {
            result = result.plus(fn.apply(a));
        }
        return result;
    }

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

    public static SetMonadPlus monadPlus = new SetMonadPlus() {
    };

    public static <A> Monoid<Set<A>> monoid() {
        return Monoid.create(Set.empty(), (x, y) -> x.plus(y));
    }
}
