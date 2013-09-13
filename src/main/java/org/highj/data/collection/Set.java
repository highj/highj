package org.highj.data.collection;

import org.highj._;
import org.highj.data.collection.set.SetMonadPlus;
import org.highj.data.collection.set.SetMonoid;
import org.highj.data.compare.Ordering;
import org.highj.data.tuple.T2;
import org.highj.data.functions.Strings;
import org.highj.typeclass0.group.Monoid;
import org.highj.util.ArrayUtils;
import org.highj.util.Iterators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * A crude, hash-based Set implementation.
 * <p/>
 * Note that the provided monadTrans instance could be considered a hack, based on the fact that every
 * Java Object has a hashCode and an equals implementation, which might be rather useless in some cases.
 *
 * @param <A> The element type.
 */
public class Set<A> implements _<Set.µ, A>, Iterable<A>, Function<A, Boolean> {

    public static class µ {
    }

    @SuppressWarnings("unchecked")
    private final static Set<Object> EMPTY = new Set<Object>(Integer.MIN_VALUE, List.nil(), null, null);

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
    public Boolean apply(A value) {
        if (isEmpty()) {
            return false;
        }

        int vhc = value.hashCode();
        switch (Ordering.compare(vhc, hc)) {
            case LT:
                return left.apply(value);
            case GT:
                return right.apply(value);
            case EQ:
                return bucket.contains(value);
            default:
                throw new AssertionError();
        }
    }

    public Set<A> plus(A a) {
        if (isEmpty()) {
            return new Set<>(a.hashCode(), List.of(a), Set.<A>empty(), Set.<A>empty());
        }
        int ahc = a.hashCode();
        switch (Ordering.compare(ahc, hc)) {
            case EQ:
                return bucket.contains(a) ? this : new Set<>(hc, bucket.plus(a), left, right);
            case LT:
                Set<A> newLeft = left.plus(a);
                return left == newLeft ? this : new Set<>(hc, bucket, newLeft, right);
            case GT:
                Set<A> newRight = right.plus(a);
                return right == newRight ? this : new Set<>(hc, bucket, left, newRight);
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
                if (bucket == newBucket) {
                    return this;
                } else if (!newBucket.isEmpty()) {
                    return new Set<>(hc, newBucket, left, right);
                } else if (left.isEmpty()) {
                    return right;
                } else if (right.isEmpty()) {
                    return left;
                } else {
                    T2<Set<A>, Set<A>> pair = right.removeMin();
                    return new Set<>(pair._1().hc, pair._1().bucket, left, pair._2());
                }
            case LT:
                Set<A> newLeft = left.minus(a);
                return left == newLeft ? this : new Set<>(hc, bucket, newLeft, right);
            case GT:
                Set<A> newRight = right.minus(a);
                return right == newRight ? this : new Set<>(hc, bucket, left, newRight);
            default:
                throw new AssertionError();
        }
    }

    private T2<Set<A>, Set<A>> removeMin() {
        if (left.isEmpty()) {
            return T2.of(this, right);
        } else {
            T2<Set<A>, Set<A>> pair = left.removeMin();
            return T2.of(pair._1(), new Set<>(hc, bucket, pair._2(), right));
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

    //we need to output the hashCodes in order, else different insertion orders would lead to different iteration orders
    public Iterator<A> iterator() {
        return (isEmpty()) ? Iterators.emptyIterator() :
        new Iterator<A>() {

            private List<A> list = List.empty();
            private List<Either<Set<A>,List<A>>> todo = List.of(Either.<Set<A>, List<A>>newLeft(Set.this));

            @Override
            public boolean hasNext() {
                return ! list.isEmpty() || ! todo.isEmpty();
            }

            @Override
            public A next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                while(list.isEmpty()) {
                   Either<Set<A>,List<A>> current = todo.head();
                   todo = todo.tail();
                    if (current.isRight()) {
                        list = current.getRight();
                    } else {
                        Set<A> set = current.getLeft();
                        addIfNotEmpty(set.right);
                        todo = todo.plus(Either.newRight(set.bucket));
                        addIfNotEmpty(set.left);
                    }
                }
                A result = list.head();
                list = list.tail();
                return result;
            }

            private void addIfNotEmpty(Set<A> set) {
                if (!set.isEmpty()) {
                    todo = todo.plus(Either.newLeft(set));
                }
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

    public static <A> Set<A> join(Set<Set<A>> set) {
        Set<A> result = empty();
        for (Set<A> innerSet : set) {
            result = result.plus(innerSet);
        }
        return result;
    }

    //avoids using the expensive iterator
    public java.util.Set<A> toJSet() {
        java.util.Set<A> result = new HashSet<>();
        toJSetHelper(result);
        return result;
    }

    private void toJSetHelper(java.util.Set<A> jSet) {
        if (! isEmpty()) {
            left.toJSetHelper(jSet);
            for(A a : bucket) {
               jSet.add(a);
            }
            right.toJSetHelper(jSet);
        }
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
        for(A a : this) {
            result += a.hashCode();
        }
        return result;
    }

    public static SetMonadPlus monadPlus = new SetMonadPlus();

    public static <A> Monoid<Set<A>> monoid() {
        return new SetMonoid<>();
    }
}
