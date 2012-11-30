package org.highj.data.collection;

import org.highj._;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.function.F1;
import org.highj.function.Strings;
import org.highj.typeclass.monad.MonadAbstract;
import org.highj.typeclass.monad.MonadPlus;
import org.highj.util.ArrayUtils;
import org.highj.util.Iterators;

import java.util.Iterator;

/**
 * A crude, hash-based Set implementation.
 * <p/>
 * Note that the provided monad instance could be considered a hack, based on the fact that every
 * Java Object has a hashCode and an equals implementation, which might be rather useless in some cases.
 *
 * @param <A> The element type.
 */
public class Set<A> extends _<Set.µ, A> implements Iterable<A> {

    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    @SuppressWarnings("unchecked")
    private final static Set EMPTY = new Set(Integer.MIN_VALUE, List.Nil(), null, null) {
    };

    private final Set<A> left;
    private final Set<A> right;
    private final List<A> bucket;
    private final int hc;

    private Set(int hc, List<A> bucket, Set<A> left, Set<A> right) {
        super(hidden);
        this.hc = hc;
        this.bucket = bucket;
        this.right = right;
        this.left = left;
    }

    @SuppressWarnings("unchecked")
    public static <A> Set<A> narrow(_<µ, A> value) {
        return (Set) value;
    }

    public boolean $(A value) {
        if (isEmpty()) {
            return false;
        }

        int vhc = value.hashCode();
        if (vhc < hc) {
            return left.$(value);
        } else if (vhc > hc) {
            return right.$(value);
        } else {
            for (A a : bucket) {
                if (a == value) {
                    return true;
                }
            }
            return false;
        }
    }

    public Set<A> plus(A a) {
        if (isEmpty()) {
            return new Set<A>(a.hashCode(), List.<A>of(a), Set.<A>empty(), Set.<A>empty());
        }
        int ahc = a.hashCode();
        if (hc == ahc) {
            return bucket.$(a) ? this : new Set<A>(hc, bucket.cons(a), left, right);
        } else if (ahc < hc) {
            Set<A> newLeft = left.plus(a);
            return left == newLeft ? this : new Set<A>(hc, bucket, newLeft, right);
        } else {
            Set<A> newRight = right.plus(a);
            return right == newRight ? this : new Set<A>(hc, bucket, left, newRight);
        }
    }

    public Set<A> minus(A a) {
        if (isEmpty()) {
            return this;
        }
        int ahc = a.hashCode();
        if (hc == ahc) {
            List<A> newBucket = bucket.minus(a);
            if (bucket == newBucket) {
                return this;
            } else if (!newBucket.isEmpty()) {
                return new Set<A>(hc, newBucket, left, right);
            } else if (left.isEmpty()) {
                return right;
            } else if (right.isEmpty()) {
                return left;
            } else {
                T2<Set<A>, Set<A>> pair = right.removeMin();
                return new Set<A>(pair._1().hc, pair._1().bucket, left, pair._2());
            }
        } else if (ahc < hc) {
            Set<A> newLeft = left.minus(a);
            return left == newLeft ? this : new Set<A>(hc, bucket, newLeft, right);
        } else {
            Set<A> newRight = right.minus(a);
            return right == newRight ? this : new Set<A>(hc, bucket, left, newRight);
        }
    }

    private T2<Set<A>, Set<A>> removeMin() {
        if (left.isEmpty()) {
            return Tuple.of(this, right);
        } else {
            T2<Set<A>, Set<A>> pair = left.removeMin();
            return Tuple.of(pair._1(), new Set<A>(hc, bucket, pair._2(), right));
        }
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @SuppressWarnings("unchecked")
    public static <A> Set<A> empty() {
        return EMPTY;
    }

    public static <A> Set<A> of() {
        return empty();
    }

    //@SafeVarargs
    public static <A> Set<A> of(A... as) {
        return Set.<A>empty().plus(as);
    }

    public static Set<Boolean> of(boolean[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static Set<Byte> of(byte[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static Set<Character> of(char[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static Set<Short> of(short[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static Set<Integer> of(int[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static Set<Long> of(long[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static Set<Float> of(float[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static Set<Double> of(double[] as) {
        return Set.of(ArrayUtils.asSet(as));
    }

    public static <A> Set<A> of(Iterable<A> as) {
        return Set.<A>empty().plus(as);
    }

    //@SafeVarargs
    public final Set<A> plus(A... as) {
        Set<A> result = this;
        for (A a : as) {
            result = result.plus(a);
        }
        return result;
    }

    //@SafeVarargs
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
        return isEmpty() ? 0 : 1 + left.size() + right.size();
    }

    public F1<A, Boolean> F1() {
        return new F1<A, Boolean>() {

            @Override
            public Boolean $(A a) {
                return Set.this.$(a);
            }
        };
    }

    public Iterator<A> iterator() {
        return isEmpty() ? Iterators.<A>emptyIterator() : Iterators.concat(left.iterator(), bucket.iterator(), right.iterator());
    }

    public String toString() {
        return Strings.mkString("Set(", ",", ")", this);
    }

    public <B> Set<B> map(F1<A, B> fn) {
        Set<B> result = empty();
        for (A a : this) {
            result.plus(fn.$(a));
        }
        return result;
    }

    public static MonadPlus<µ> monadPlus = new SetMonadPlus();

    private static class SetMonadPlus extends MonadAbstract<µ> implements MonadPlus<µ> {
        @Override
        public <A> _<µ, A> pure(A a) {
            return of(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, F1<A, B>> fn, _<µ, A> nestedA) {
            Set<B> result = empty();
            for (F1<A, B> f : narrow(fn)) {
                for (A a : narrow(nestedA)) {
                    result.plus(f.$(a));
                }
            }
            return result;
        }

        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }

        @Override
        public <A> _<µ, A> mzero() {
            return empty();
        }

        @Override
        public <A> _<µ, A> mplus(_<µ, A> one, _<µ, A> two) {
            return narrow(one).plus(narrow(two));
        }

        @Override
        public <A> _<µ, A> join(_<µ, _<µ, A>> nestedNestedA) {
            Set<A> result = empty();
            for (_<µ, A> innerSet : narrow(nestedNestedA)) {
                result.plus(narrow(innerSet));
            }
            return result;
        }
    }
}
