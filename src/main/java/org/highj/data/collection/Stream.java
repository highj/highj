package org.highj.data.collection;

import org.highj._;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.function.*;
import org.highj.typeclass.monad.Monad;
import org.highj.typeclass.monad.MonadAbstract;
import org.highj.util.ReadOnlyIterator;

import java.util.Iterator;

/*
 * An infinite list.
 */
public abstract class Stream<A> extends _<Stream.µ, A> implements Iterable<A> {

    private static final µ hidden = new µ();

    public static final class µ {
        private µ() {
        }
    }

    @SuppressWarnings("unchecked")
    public static <A> Stream<A> narrow(_<µ, A> value) {
        return (Stream) value;
    }

    private Stream() {
        super(hidden);
    }

    public abstract A head();

    public abstract Stream<A> tail();

    public static <A> Stream<A> of(final A a) {
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

    public static <A> Stream<A> unfold(final A a, final F1<A, A> fn) {
        return new Stream<A>() {

            @Override
            public A head() {
                return a;
            }

            @Override
            public Stream<A> tail() {
                return unfold(fn.$(a), fn);
            }
        };
    }

    public static <A> Stream<A> Cons(final A a, final Stream<A> stream) {
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

    public static <A> Stream<A> Cons(final A a, final F0<Stream<A>> thunk) {
        return new Stream<A>() {

            @Override
            public A head() {
                return a;
            }

            @Override
            public Stream<A> tail() {
                return thunk.$();
            }
        };
    }

    //assuming that the iterator doesn't stop
    public static <A> Stream<A> fromIterator(final Iterator<A> iterator) {
        return Cons(iterator.next(), new F0<Stream<A>>() {

            @Override
            public Stream<A> $() {
                return fromIterator(iterator);
            }
        });
    }

    //returns iterator values wrapped in Just, and Nothing when the iterator gets empty
    public static <A> Stream<Maybe<A>> maybeFromIterator(final Iterator<A> iterator) {
        return Cons(iterator.hasNext()
                ? Maybe.Just(iterator.next())
                : Maybe.<A>Nothing(),
                new F0<Stream<Maybe<A>>>() {

                    @Override
                    public Stream<Maybe<A>> $() {
                        return maybeFromIterator(iterator);
                    }
                });
    }

    public Stream<A> filter(final F1<A, Boolean> predicate) {
        final Stream<A> result = dropWhile(Predicates.not(predicate));
        return Cons(result.head(), new F0<Stream<A>>() {
            @Override
            public Stream<A> $() {
                return result.tail().filter(predicate);
            }
        });
    }

    public String toString() {
        return Strings.mkString("Stream(", ",", "...)", this.take(10));
    }

    public List<A> take(final int n) {
        return n <= 0 ? List.<A>Nil() : List.Cons(head(), new F0<List<A>>() {
            @Override
            public List<A> $() {
                return tail().take(n - 1);
            }
        });
    }

    public List<A> takeWhile(final F1<A, Boolean> predicate) {
        return !predicate.$(head()) ? List.<A>Nil() : List.Cons(head(), new F0<List<A>>() {
            @Override
            public List<A> $() {
                return tail().takeWhile(predicate);
            }
        });
    }

    public Stream<A> drop(int n) {
        Stream<A> result = this;
        while (n-- > 0) {
            result = result.tail();
        }
        return result;
    }

    public Stream<A> dropWhile(F1<A, Boolean> predicate) {
        Stream<A> result = this;
        while (predicate.$(result.head())) {
            result = result.tail();
        }
        return result;
    }

    public static Stream<Integer> range(final int from, final int step) {
        return unfold(from, Integers.add.$(step));
    }

    public static Stream<Integer> range(final int from) {
        return range(from, 1);
    }

    //@SafeVarargs
    public static <A> Stream<A> of(final A... as) {
        Stream<A> result = Cons(as[as.length - 1], new F0<Stream<A>>() {

            @Override
            public Stream<A> $() {
                return of(as);
            }
        });
        for (int i = as.length - 1; i > 0; i--) {
            result = Cons(as[i - 1], result);
        }
        return result;
    }

    public <B> Stream<B> map(final F1<? super A, ? extends B> fn) {
        return Cons(fn.$(head()),
                new F0<Stream<B>>() {
                    @Override
                    public Stream<B> $() {
                        return tail().map(fn);
                    }
                }
        );
    }

    public static <A, B> Stream<T2<A, B>> zip(Stream<A> streamA, Stream<B> streamB) {
        return zipWith(streamA, streamB, Tuple.<A, B>pair());
    }

    public static <A, B, C> Stream<C> zipWith(final Stream<A> streamA, final Stream<B> streamB, final F1<A, F1<B, C>> fn) {
        return Cons(fn.$(streamA.head()).$(streamB.head()), new F0<Stream<C>>() {
            @Override
            public Stream<C> $() {
                return zipWith(streamA.tail(), streamB.tail(), fn);
            }
        });
    }

    public static <A, B> T2<Stream<A>, Stream<B>> unzip(Stream<T2<A, B>> streamAB) {
        return Tuple.of(streamAB.map(Tuple.<A>fst()), streamAB.map(Tuple.<B>snd()));
    }

    public static final Monad<µ> monad = new MonadAbstract<µ>() {
        @Override
        public <A> _<µ, A> pure(A a) {
            return of(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, F1<A, B>> fn, _<µ, A> nestedA) {
            return zipWith(narrow(fn), narrow(nestedA), F1.<A, B>apply());
        }

        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }

        @Override
        public <A> _<µ, A> join(_<µ, _<µ, A>> nestedNestedA) {
            Stream<_<µ, A>> nestedStream = narrow(nestedNestedA);
            Stream<A> xs = narrow(nestedStream.head());
            final Stream<_<µ, A>> xss = nestedStream.tail();
            return Cons(xs.head(), new F0<Stream<A>>() {
                @Override
                public Stream<A> $() {
                    Stream<_<Stream.µ, A>> tails = xss.map(new F1<_<Stream.µ, A>, _<Stream.µ, A>>() {
                        @Override
                        public _<Stream.µ, A> $(_<Stream.µ, A> as) {
                            return Stream.narrow(as).tail();
                        }
                    });
                    return Stream.narrow(join(tails));
                }
            });
        }
    };

    @Override
    public Iterator<A> iterator() {
        return new ReadOnlyIterator<A>() {

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
}