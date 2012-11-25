package org.highj.data.collection;

import org.highj._;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.function.F0;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.function.Strings;
import org.highj.typeclass.alternative.Alt;
import org.highj.typeclass.alternative.AltAbstract;
import org.highj.typeclass.foldable.Foldable;
import org.highj.typeclass.foldable.FoldableAbstract;
import org.highj.typeclass.monad.*;
import org.highj.util.Lazy;
import org.highj.util.ReadOnlyIterator;

import java.util.*;

public abstract class List<A> extends _<List.µ, A> implements Iterable<A> {

    private static final µ hidden = new µ();

    public static final class µ {
        private µ() {
        }
    }

    private final static List NIL = new List() {

        @Override
        public Maybe maybeHead() {
            return Maybe.Nothing();
        }

        @Override
        public Maybe maybeTail() {
            return Maybe.Nothing();
        }
    };

    private List() {
        super(hidden);
    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> narrow(_<µ, A> value) {
        return (List) value;
    }

    @SuppressWarnings("unchecked")
    public static <Super_A, A extends Super_A> List<Super_A> contravariant(List<A> list) {
        return (List) list;
    }

    public static <A> List<A> of() {
        return Nil();
    }

    @SafeVarargs
    public static <A> List<A> of(final A... as) {
        return of(Arrays.asList(as));
    }

    public static <A> List<A> of(java.util.List<A> as) {
        List<A> result = Nil();
        for (int i = as.size(); i > 0; i--) {
            result = result.cons(as.get(i - 1));
        }
        return result;
    }

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
        };
    }

    public static <A> List<A> Cons(final A head, final F0<List<A>> thunkTail) {
        return new List<A>() {

            @Override
            public Maybe<A> maybeHead() {
                return Maybe.Just(head);
            }

            @Override
            public Maybe<List<A>> maybeTail() {
                return Maybe.Just(thunkTail);
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

    public static <A> F2<A, List<A>, List<A>> cons() {
        return new F2<A, List<A>, List<A>>() {
            @Override
            public List<A> $(A a, List<A> as) {
                return Cons(a, as);
            }
        };
    }

    public List<A> cons(A a) {
        return Cons(a, this);
    }

    public List<A> minus(A a) {
        if (isEmpty()) {
            return this;
        }
        A head = head();
        List<A> tail = tail();
        if (head.equals(a)) {
            return tail;
        } else {
            List<A> newTail = tail.minus(a);
            return tail == newTail ? this : Cons(head, newTail);
        }
    }

    @SafeVarargs
    public final List<A> minusAll(A... as) {
        if (isEmpty()) {
            return this;
        }
        A head = head();
        List<A> tail = tail();
        for (A a : as) {
            if (head.equals(a)) {
                return tail.minusAll(as);
            }
        }
        List<A> newTail = tail.minusAll(as);
        return tail == newTail ? this : Cons(head, newTail);
    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> Nil() {
        return NIL;
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

    public boolean $(A value) {
        for (A a : this) {
            if (a == value) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(F1<A,Boolean> predicate) {
        for (A a : this) {
            if (predicate.$(a)) {
                return true;
            }
        }
        return false;
    }

    public int count(F1<A,Boolean> predicate) {
        int result = 0;
        for (A a : this) {
            if (predicate.$(a)) {
                result++;
            }
        }
        return result;
    }

    public int count(A value) {
        int result = 0;
        for (A a : this) {
            if (a == value) {
                result++;
            }
        }
        return result;
    }

    public List<A> take(final int n) {
        return n <= 0 || isEmpty() ? List.<A>Nil() : Cons(head(), new F0<List<A>>() {
            @Override
            public List<A> $() {
                return tail().take(n - 1);
            }
        });
    }

    public List<A> takeWhile(final F1<A, Boolean> predicate) {
        return isEmpty() || !predicate.$(head()) ? List.<A>Nil() : Cons(head(), new F0<List<A>>() {
            @Override
            public List<A> $() {
                return tail().takeWhile(predicate);
            }
        });
    }

    public List<A> drop(int n) {
        List<A> result = this;
        while (n-- > 0 && !result.isEmpty()) {
            result = result.tail();
        }
        return result;
    }

    public List<A> dropWhile(F1<A, Boolean> predicate) {
        List<A> result = this;
        while (!result.isEmpty() && predicate.$(result.head())) {
            result = result.tail();
        }
        return result;
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
        return new ReadOnlyIterator<A>() {

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
        java.util.List<A> result = new ArrayList<A>();
        for (A a : this) {
            result.add(a);
        }
        Collections.reverse(result);
        return result;
    }

    public static List<Integer> range(final int from, final int step, final int to) {
        return (step > 0 && from <= to || step < 0 && from >= to) ? Cons(from, new F0<List<Integer>>() {

            @Override
            public List<Integer> $() {
                return range(from + step, step, to);
            }
        }) : List.<Integer>Nil();
    }


    public static List<Integer> range(final int from, final int step) {
        return Cons(from, new F0<List<Integer>>() {

            @Override
            public List<Integer> $() {
                return range(from + step, step);
            }
        });
    }

    public static List<Integer> range(final int from) {
        return range(from, 1);
    }

    @SafeVarargs
    public static <A> List<A> cycle(final A... as) {
        List<A> result = Cons(as[as.length - 1], new F0<List<A>>() {

            @Override
            public List<A> $() {
                return cycle(as);
            }
        });
        for (int i = as.length - 1; i > 0; i--) {
            result = Cons(as[i - 1], result);
        }
        return result;
    }

    //for performance reasons
    public static <A> List<A> repeat(final A a) {
        final Lazy<List<A>> ls = Lazy.Lazy();
        ls.set(Cons(a, new F0<List<A>>() {
            @Override
            public List<A> $() {
                return ls.get();
            }
        }));
        return ls.get();
    }

    public static <A> List<A> replicate(int n, A a) {
        return cycle(a).take(n);
    }

    public static <A> List<A> append(_<µ, A> one, _<µ, A> two) {
        final List<A> listOne = narrow(one);
        final List<A> listTwo = narrow(two);
        return listOne.isEmpty() ? listTwo : Cons(listOne.head(), new F0<List<A>>() {
            @Override
            public List<A> $() {
                return append(listOne.tail(), listTwo);
            }
        });
    }

    //won't terminate for infinite Lists
    public String toString() {
        return Strings.mkString("List(", ",", ")", this);
    }

    public <B> List<B> map(final F1<? super A, ? extends B> fn) {
        if (isEmpty())
            return Nil();
        else
            return Cons(fn.$(head()),
                    new F0<List<B>>() {
                        @Override
                        public List<B> $() {
                            return tail().map(fn);
                        }
                    }
            );
    }

    public List<A> filter(final F1<A, Boolean> predicate) {
        if (isEmpty()) {
            return this;
        } else if (predicate.$(head())) {
            return Cons(head(), new F0<List<A>>() {
                @Override
                public List<A> $() {
                    return tail().filter(predicate);
                }
            });
        } else {
            return tail().filter(predicate);
        }
    }

    private static <A> List<A> buildFromStack(Stack<A> stack) {
        List<A> result = Nil();
        while (!stack.isEmpty()) {
            result = result.cons(stack.pop());
        }
        return result;
    }

    public List<A> reverse() {
        List<A> result = Nil();
        for (A a : this) {
            result = Cons(a, result);
        }
        return result;
    }

    public <B> B foldr(final F1<A, F1<B, B>> fn, final B b) {
        return isEmpty() ? b : fn.$(head()).$(tail().foldr(fn, b));
    }

    public <B> B foldl(final B b, final F1<B, F1<A, B>> fn) {
        B result = b;
        for (A a : this) {
            result = fn.$(result).$(a);
        }
        return result;
    }

    public static final Foldable<µ> foldable = new FoldableAbstract<µ>() {
        @Override
        public <A, B> B foldr(F1<A, F1<B, B>> fn, B b, _<µ, A> nestedA) {
            return narrow(nestedA).foldr(fn, b);
        }

        @Override
        public <A, B> A foldl(F1<A, F1<B, A>> fn, A a, _<µ, B> nestedB) {
            return narrow(nestedB).foldl(a, fn);
        }
    };

    public static <A, B> List<T2<A, B>> zip(List<A> listA, List<B> listB) {
        return zipWith(listA, listB, Tuple.<A, B>pair());
    }

    public static <A, B, C> List<C> zipWith(final List<A> listA, final List<B> listB, final F1<A, F1<B, C>> fn) {
        return listA.isEmpty() || listB.isEmpty() ? List.<C>Nil() :
                Cons(fn.$(listA.head()).$(listB.head()), new F0<List<C>>() {
                    @Override
                    public List<C> $() {
                        return zipWith(listA.tail(), listB.tail(), fn);
                    }
                });
    }

    public static <A, B> T2<List<A>, List<B>> unzip(List<T2<A, B>> listAB) {
        return Tuple.of(listAB.map(Tuple.<A>fst()), listAB.map(Tuple.<B>snd()));
    }


    public static final Applicative<µ> zipApplicative = new ApplicativeAbstract<µ>() {
        @Override
        public <A> _<µ, A> pure(A a) {
            return repeat(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, F1<A, B>> fn, _<µ, A> nestedA) {
            return zipWith(narrow(fn), narrow(nestedA), F1.<A, B>apply());
        }

        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }
    };

    public static MonadPlus<µ> monad = new ListMonad();

    private static class ListMonad extends MonadAbstract<µ> implements MonadPlus<µ> {

        @Override
        public <A, B> _<µ, B> map(final F1<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }

        @Override
        public <A> _<µ, A> pure(A a) {
            return of(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, F1<A, B>> fn, _<µ, A> nestedA) {
            List<F1<A, B>> listFn = narrow(fn);
            List<A> listA = narrow(nestedA);
            Stack<B> stack = new Stack<B>();
            for (F1<A, B> f : listFn) {
                for (A a : listA) {
                    stack.push(f.$(a));
                }
            }
            return buildFromStack(stack);
        }

        @Override
        public <A> _<µ, A> join(_<µ, _<µ, A>> nestedNestedA) {
            List<_<µ, A>> nestedList = narrow(nestedNestedA);
            Stack<A> stack = new Stack<A>();
            for (_<µ, A> list : nestedList) {
                for (A a : narrow(list)) {
                    stack.push(a);
                }
            }
            return buildFromStack(stack);
        }

        @Override
        public <A> _<µ, A> mzero() {
            return Nil();
        }

        @Override
        public <A> _<µ, A> mplus(_<µ, A> one, _<µ, A> two) {
            List<A> listOne = narrow(one);
            List<A> listTwo = narrow(two);
            return append(listOne, listTwo);
        }
    };

    public final static Alt<µ> alt = new AltAbstract<µ>() {
        @Override
        public <A> _<µ, A> mplus(_<µ, A> first, _<µ, A> second) {
            return append(first, second);
        }

        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }
    };

}
