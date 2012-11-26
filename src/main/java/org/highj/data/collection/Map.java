package org.highj.data.collection;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.function.F1;
import org.highj.typeclass.monad.MonadAbstract;
import org.highj.typeclass.monad.MonadPlus;
import org.highj.util.Iterators;

import java.util.Iterator;

/**
 * A crude, hash-based Map implementation.
 *
 * Note that the provided monad instance could be considered a hack, based on the fact that every
 * Java Object has a hashCode and an equals implementation, which might be rather useless in some cases.
 *
 * @param <A> The element type.
 */
public class Map<A,B> extends __<Map.µ, A, B> implements Iterable<T2<A,B>> {

    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    @SuppressWarnings("unchecked")
    private final static Map EMPTY = new Map(Integer.MIN_VALUE, List.Nil(), null, null) {
    };

    private final Map<A,B> left;
    private final Map<A,B> right;
    private final List<T2<A,B>> bucket;
    private final int hc;

    private Map(int hc, List<T2<A,B>> bucket, Map<A,B> left, Map<A,B> right) {
        super(hidden);
        this.hc = hc;
        this.bucket = bucket;
        this.right = right;
        this.left = left;
    }

    @SuppressWarnings("unchecked")
    public static <A,B> Map<A,B> narrow(_<__.µ<Map.µ,A>, B> value) {
        return (Map) value;
    }

    public Maybe<B> $(A value) {
        for (T2<A,B> ab : this) {
            if (ab._1() == value) {
                return Maybe.Just(ab._2());
            }
        }
        return Maybe.Nothing();
    }

    public Map<A,B> plus(final A a, final B b) {
        if (isEmpty()) {
            return new Map<A,B>(a.hashCode(), List.<T2<A,B>>of(Tuple.of(a, b)), Map.<A,B>empty(), Map.<A,B>empty());
        }
        int ahc = a.hashCode();
        if (hc == ahc) {
            return new Map<A,B>(hc, bucket.filter(new F1<T2<A,B>,Boolean>(){
                @Override
                public Boolean $(T2<A,B> ab) {
                    return ! ab._1().equals(a);
                }
            }).cons(Tuple.of(a,b)), left, right);
        } else if (ahc < hc) {
            Map<A,B> newLeft = left.plus(a,b);
            return left == newLeft ? this : new Map<A,B>(hc, bucket, newLeft, right);
        } else {
            Map<A,B> newRight = right.plus(a,b);
            return right == newRight ? this : new Map<A,B>(hc, bucket, left, newRight);
        }
    }

    public Map<A,B> plus(T2<A,B> ab) {
       return plus(ab._1(), ab._2());
    }


    public Map<A,B> minus(final A a) {
        if (isEmpty()) {
            return this;
        }
        int ahc = a.hashCode();
        if (hc == ahc) {
            List<T2<A,B>> newBucket = bucket.filter(new F1<T2<A, B>, Boolean>() {
                @Override
                public Boolean $(T2<A, B> ab) {
                    return ab._1() != a;
                }
            });
            if (bucket == newBucket) {
                return this;
            } else if (! newBucket.isEmpty()) {
                return new Map<A,B>(hc, newBucket, left, right);
            } else if (left.isEmpty()) {
                return right;
            } else if (right.isEmpty()) {
                return left;
            } else {
                T2<Map<A,B>, Map<A,B>> pair = right.removeMin();
                return new Map<A,B>(pair._1().hc, pair._1().bucket, left, pair._2());
            }
        } else if (ahc < hc) {
            Map<A,B> newLeft = left.minus(a);
            return left == newLeft ? this : new Map<A,B>(hc, bucket, newLeft, right);
        } else {
            Map<A,B> newRight = right.minus(a);
            return right == newRight ? this : new Map<A,B>(hc, bucket, left, newRight);
        }
    }

    private T2<Map<A,B>, Map<A,B>> removeMin() {
        if (left.isEmpty()) {
            return Tuple.of(this, right);
        } else {
            T2<Map<A,B>, Map<A,B>> pair = left.removeMin();
            return Tuple.of(pair._1(), new Map<A,B>(hc, bucket, pair._2(), right));
        }
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @SuppressWarnings("unchecked")
    public static <A,B> Map<A,B> empty() {
        return EMPTY;
    }

    public static <A,B> Map<A,B> of() {
        return empty();
    }

    //@SafeVarargs
    public static <A,B> Map<A,B> of(T2<A,B> ... abs) {
        return Map.<A,B>empty().plus(abs);
    }

    public static <A,B> Map<A,B> of(Iterable<T2<A,B>> abs) {
        return Map.<A,B>empty().plus(abs);
    }

    //@SafeVarargs
    public final Map<A,B> plus(T2<A,B> ... abs) {
        Map<A,B> result = this;
        for(T2<A,B> ab : abs) {
            result = result.plus(ab);
        }
        return result;
    }

    //@SafeVarargs
    public final Map<A,B> minus(A ... as) {
        Map<A,B> result = this;
        for(A a : as) {
            result = result.minus(a);
        }
        return result;
    }

    public Map<A,B> plus(Iterable<T2<A,B>> abs) {
        Map<A,B> result = this;
        for(T2<A,B> ab : abs) {
            result = result.plus(ab);
        }
        return result;
    }

    public Map<A,B> minus(Iterable<A> as) {
        Map<A,B> result = this;
        for(A a : as) {
            result = result.minus(a);
        }
        return result;
    }

    public int size() {
        return isEmpty() ? 0 : 1 + left.size() + right.size();
    }

    public F1<A, Maybe<B>> F1(){
        return new F1<A, Maybe<B>>() {

            @Override
            public Maybe<B> $(A a) {
                return Map.this.$(a);
            }
        };
    }

    public Iterator<T2<A,B>> iterator() {
        return isEmpty() ? Iterators.<T2<A,B>>emptyIterator() : Iterators.concat(left.iterator(), bucket.iterator(), right.iterator());
    }

    public String toString() {
        if (isEmpty()) return "Map()";
        StringBuilder sb = new StringBuilder();
        for(T2<A,B> ab : this) {
           sb.append(sb.length() == 0 ? "Map(" : ",").append(ab._1()).append("->").append(ab._2());
        }
        return sb.append(')').toString();
    }

    /*public static MonadPlus<µ> monadPlus = new MapMonadPlus();

    private static class MapMonadPlus extends MonadAbstract<µ> implements MonadPlus<µ> {
        @Override
        public <A> _<µ, A> pure(A a) {
            return of(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, F1<A, B>> fn, _<µ, A> nestedA) {
            Map<B> result = empty();
            for(F1<A,B> f : narrow(fn)) {
                for(A a : narrow(nestedA)) {
                    result.plus(f.$(a));
                }
            }
            return result;
        }

        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            Map<B> result = empty();
            for(A a : narrow(nestedA)) {
                result.plus(fn.$(a));
            }
            return result;
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
            Map<A> result = empty();
            for(_<µ, A> innerMap : narrow(nestedNestedA)) {
                result.plus(narrow(innerMap));
            }
            return result;
        }
    } */
}
