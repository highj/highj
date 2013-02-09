package org.highj.data.collection;

import org.highj._;
import org.highj.data.compare.Eq;
import org.highj.function.Functions;
import org.highj.typeclass1.alternative.Alt;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A data type which may or may not hold a value. A calculation that may fail. A.k.a. "Option" or "Box".
 */
public abstract class Maybe<A> implements _<Maybe.µ, A>, Iterable<A> {
    private static final String SHOW_NOTHING = "Nothing";
    private static final String SHOW_JUST = "Just(%s)";

    public static final class µ {
    }

    private Maybe() {
    }

    public static Foldable<µ> foldable = new Foldable<µ>() {
        @Override
        public <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, _<µ, A> as) {
            return narrow(as).<B>cata(mb.identity(), fn::apply);
        }

        @Override
        public <A, B> B foldr(Function<A, Function<B, B>> fn, B b, _<µ, A> as) {
            return narrow(as).cata(b, a -> fn.apply(a).apply(b));
        }

        @Override
        public <A, B> A foldl(Function<A, Function<B, A>> fn, A a, _<µ, B> bs) {
            return narrow(bs).cata(a, b -> fn.apply(a).apply(b));
        }
    };

    public static <A> Monoid<Maybe<A>> firstMonoid() {
        return new Monoid<Maybe<A>>() {
            @Override
            public Maybe<A> identity() {
                return Maybe.Nothing();
            }

            @Override
            public Maybe<A> dot(Maybe<A> x, Maybe<A> y) {
                return x.isJust() ? x : y;
            }
        };
    }

    public static <A> Monoid<Maybe<A>> lastMonoid() {
        return new Monoid<Maybe<A>>() {
            @Override
            public Maybe<A> identity() {
                return Maybe.Nothing();
            }

            @Override
            public Maybe<A> dot(Maybe<A> x, Maybe<A> y) {
                return y.isJust() ? y : x;
            }
        };
    }

    public static <A> Monoid<Maybe<A>> monoid(final Semigroup<A> semigroupA) {
        return new Monoid<Maybe<A>>() {
            @Override
            public Maybe<A> identity() {
                return Maybe.Nothing();
            }

            @Override
            public Maybe<A> dot(Maybe<A> one, Maybe<A> two) {
                return one.cata(Maybe.<A>Nothing(), x -> two.<A>map(y -> semigroupA.dot(x, y)));
            }
        };
    }

    @SuppressWarnings("rawtypes")
    private final static Maybe<Object> NOTHING = new Maybe<Object>() {

        @Override
        public <B> B lazyCata(Supplier<B> defaultThunk, Function<Object,B> fn) {
            return defaultThunk.get();
        }

        @Override
        public <B> B  cata(B defaultValue, Function<Object,B> fn) {
            return defaultValue;
        }
    };

    public abstract <B> B lazyCata(Supplier<B> defaultThunk, Function<A, B> fn);

    // the catamorphism of Maybe
    public abstract <B> B cata(B defaultValue, Function<A, B> fn);

    public <B> Maybe<B> map(Function<? super A, ? extends B> fn) {
        return cata(Maybe.<B>Nothing(), a -> Maybe.<B>Just(fn.apply(a)));
    }

    @SuppressWarnings("unchecked")
    public static <A> Maybe<A> narrow(_<µ, A> value) {
        return (Maybe) value;
    }

    @SuppressWarnings("unchecked")
    public static <A> Maybe<A> Nothing() {
        return (Maybe) NOTHING;
    }

    public static <A> Maybe<A> Just(final A value) {
        assert (value != null);
        return new Maybe<A>() {
            public <B> B lazyCata(Supplier<B> defaultThunk, Function<A, B> fn) {
                return fn.apply(value);
            }

            @Override
            public <B> B cata(B defaultValue, Function<A, B> fn) {
                return fn.apply(value);
            }
        };
    }

    public static <A> Maybe<A> Just(final Supplier<A> thunk) {
        return new Maybe<A>() {

            public <B> B lazyCata(Supplier<B> defaultThunk, Function<A, B> fn) {
                return fn.apply(thunk.get());
            }

            @Override
            public <B> B cata(B defaultValue, Function<A, B> fn) {
                return fn.apply(thunk.get());
            }
        };
    }

    public boolean isNothing() {
        return this == NOTHING;
    }

    public boolean isJust() {
        return this != NOTHING;
    }

    public A getOrElse(A defaultValue) {
        return cata(defaultValue, x -> x);
    }

    public A getOrElse(Supplier<A> defaultThunk) {
        return this.<A>lazyCata(defaultThunk, x -> x);
    }

    public A getOrError(Class<? extends RuntimeException> exClass) {
        return getOrElse(Functions.<A>error(exClass));
    }

    public A getOrError(Class<? extends RuntimeException> exClass, String message) {
        return getOrElse(Functions.<A>error(exClass, message));
    }

    public A getOrError(String message) {
        return getOrElse(Functions.<A>error(message));
    }

    public A get() throws NoSuchElementException {
        return getOrError(NoSuchElementException.class);
    }

    public Maybe<A> orElse(Maybe<A> that) {
        return this.isJust() ? this : that;
    }

    @Override
    public String toString() {
        return cata(SHOW_NOTHING, a -> String.format(SHOW_JUST, a));
    }

    public List<A> asList() {
        return cata(List.<A>nil(), x-> List.<A>empty().plus(x));
    }

    public Iterator<A> iterator() {
        return asList().iterator();
    }

    public static <A> Eq<Maybe<A>> eq(final Eq<? super A> eqA) {
        return (one, two) -> one.cata(two.isNothing(),
                x -> two.<Boolean>cata(false, y -> eqA.eq(x, y)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Maybe) {
            Maybe<?> that = (Maybe) obj;
            return cata(that.isNothing(), x -> that.<Boolean>cata(false, x::equals));
        } else return false;
    }

    @Override
    public int hashCode() {
        return cata(0, Object::hashCode);
    }

    public static <A> List<A> justs(List<Maybe<A>> list) {
        Stack<A> result = new Stack<>();
        for (Maybe<A> maybe : list) {
           for(A a : maybe) {
               result.push(a);
           }
        }
        return List.buildFromStack(result);
    }

    public static final MonadPlus<µ> monad = new MaybeMonadPlus();

    private static class MaybeMonadPlus implements MonadPlus<µ> {
        @Override
        public <A> _<µ, A> pure(A a) {
            return Just(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, Function<A, B>> fn, _<µ, A> nestedA) {
            return narrow(fn).cata(Maybe.<B>Nothing(), f1 -> narrow(nestedA).<_<µ, B>>cata(
                    Maybe.<B>Nothing(), a -> Just(f1.apply(a)))
            );
        }

        public <A, B> _<µ, B> map(final Function<A, B> fn, _<µ, A> nestedA) {
            return Maybe.narrow(nestedA).map(fn);
        }

        @Override
        public <A, B> _<µ, B> bind(_<µ, A> nestedA, Function<A, _<µ, B>> fn) {
            return narrow(nestedA).<_<µ, B>>cata(Maybe.<B>Nothing(), fn::apply);
        }

        @Override
        public <A> _<µ, A> mzero() {
            return Nothing();
        }

        @Override
        public <A> _<µ, A> mplus(_<µ, A> first, _<µ, A> second) {
            return narrow(first).isNothing() ? second : first;
        }
    }

    public final static Alt<µ> alt = new Alt<µ>() {
        @Override
        public <A> _<µ, A> mplus(_<µ, A> first, _<µ, A> second) {
            return narrow(first).isNothing() ? second : first;
        }

        @Override
        public <A, B> _<µ, B> map(Function<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }
    };

}
