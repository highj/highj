package org.highj.data.collection;

import org.highj._;
import org.highj.data.compare.Eq;
import org.highj.function.*;
import org.highj.function.repo.Objects;
import org.highj.function.repo.Strings;
import org.highj.typeclass.alternative.Alt;
import org.highj.typeclass.alternative.AltAbstract;
import org.highj.typeclass.foldable.Foldable;
import org.highj.typeclass.foldable.FoldableAbstract;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.group.MonoidAbstract;
import org.highj.typeclass.group.Semigroup;
import org.highj.typeclass.monad.MonadAbstract;
import org.highj.typeclass.monad.MonadPlus;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A data type which may mplus may not hold a value. A calculation that may fail. A.k.a. "Option" mplus "Box".
 */
public abstract class Maybe<A> extends _<Maybe.µ, A> implements Iterable<A> {
    private static final µ hidden = new µ();

    private static final String SHOW_NOTHING = "Nothing";
    private static final String SHOW_JUST = "Just(%s)";

    public static final class µ {
        private µ() {
        }
    }

    private Maybe() {
        super(hidden);
    }

    public static Foldable<µ> foldable = new FoldableAbstract<µ>() {
        @Override
        public <A, B> B foldMap(Monoid<B> mb, F1<A, B> fn, _<µ, A> nestedA) {
            for (A a : narrow(nestedA)) {
                return fn.$(a);
            }
            return mb.identity();
        }

        @Override
        public <A, B> B foldr(F1<A, F1<B, B>> fn, B b, _<µ, A> nestedA) {
            for (A a : narrow(nestedA)) {
                return fn.$(a).$(b);
            }
            return b;
        }

        @Override
        public <A, B> A foldl(F1<A, F1<B, A>> fn, A a, _<µ, B> nestedB) {
            for (B b : narrow(nestedB)) {
                return fn.$(a).$(b);
            }
            return a;
        }
    };

    public static <A> Monoid<Maybe<A>> firstMonoid() {
        return new MonoidAbstract<Maybe<A>>(new F2<Maybe<A>, Maybe<A>, Maybe<A>>() {
            @Override
            public Maybe<A> $(Maybe<A> x, Maybe<A> y) {
                return x.isJust() ? x : y;
            }
        }, Maybe.<A>Nothing());
    }

    public static <A> Monoid<Maybe<A>> lastMonoid() {
        return new MonoidAbstract<Maybe<A>>(new F2<Maybe<A>, Maybe<A>, Maybe<A>>() {
            @Override
            public Maybe<A> $(Maybe<A> x, Maybe<A> y) {
                return y.isJust() ? y : x;
            }
        }, Maybe.<A>Nothing());
    }

    public static <A> Monoid<Maybe<A>> monoid(final Semigroup<A> semigroupA) {
        return new MonoidAbstract<Maybe<A>>(
                new F2<Maybe<A>, Maybe<A>, Maybe<A>>() {

                    @Override
                    public Maybe<A> $(Maybe<A> mx, Maybe<A> my) {
                        for (A x : mx) {
                            for (A y : my) {
                                return Maybe.Just(semigroupA.dot(x, y));
                            }
                            return mx;
                        }
                        return my;
                    }
                }, Maybe.<A>Nothing());
    }

    @SuppressWarnings("rawtypes")
    private final static Maybe NOTHING = new Maybe() {

        @Override
        public Object cata(F0 defaultThunk, F1 fn) {
            return defaultThunk.$();
        }

        @Override
        public Object cata(Object defaultValue, F1 fn) {
            return defaultValue;
        }
    };

    // the catamorphism of Maybe: cata(F0<A> default, F1<A,B> fn)
    public abstract <B> B cata(F0<B> defaultThunk, F1<A, B> fn);

    public abstract <B> B cata(B defaultValue, F1<A, B> fn);

    public <B> Maybe<B> map(F1<A, B> fn) {
        return cata(Maybe.<B>Nothing(), fn.andThen(Maybe.<B>just()));
    }

    @SuppressWarnings("unchecked")
    public static <A> Maybe<A> narrow(_<µ, A> value) {
        return (Maybe) value;
    }

    public static <A> F1<_<µ, A>, Maybe<A>> narrow() {
        return new F1<_<µ, A>,Maybe<A>>(){

            @Override
            public Maybe<A> $(_<Maybe.µ, A> ma) {
                return Maybe.<A>narrow(ma);
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <A> Maybe<A> Nothing() {
        return NOTHING;
    }

    public static <A> Maybe<A> Just(final A value) {
        assert (value != null);
        return new Maybe<A>() {
            public <B> B cata(F0<B> defaultThunk, F1<A, B> fn) {
                return fn.$(value);
            }

            @Override
            public <B> B cata(B defaultValue, F1<A, B> fn) {
                return fn.$(value);
            }
        };
    }

    public static <A> Maybe<A> Just(final F0<A> thunk) {
        return new Maybe<A>() {

            public <B> B cata(F0<B> defaultThunk, F1<A, B> fn) {
                return fn.$(thunk.$());
            }

            @Override
            public <B> B cata(B defaultValue, F1<A, B> fn) {
                return fn.$(thunk.$());
            }
        };
    }

    public static <A> F1<A, Maybe<A>> just() {
        return new F1<A, Maybe<A>>() {
            @Override
            public Maybe<A> $(A a) {
                return Just(a);
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
        return cata(defaultValue, F1.<A>id());
    }

    public A getOrElse(F0<A> defaultThunk) {
        return cata(defaultThunk, F1.<A>id());
    }

    public A getOrError(Class<? extends RuntimeException> exClass) {
        return getOrElse(F0.<A>error(exClass));
    }

    public A getOrError(Class<? extends RuntimeException> exClass, String message) {
        return getOrElse(F0.<A>error(exClass, message));
    }

    public A getOrError(String message) {
        return getOrElse(F0.<A>error(message));
    }

    public A get() throws NoSuchElementException {
        return getOrError(NoSuchElementException.class);
    }

    public Maybe<A> orElse(Maybe<A> that) {
        return this.isJust() ? this : that;
    }

    @Override
    public String toString() {
        return cata(F0.constant(SHOW_NOTHING), Strings.<A>format(SHOW_JUST));
    }

    public List<A> asList() {
        return isNothing() ? List.<A>Nil() : List.of(get());
    }

    public Iterator<A> iterator() {
        return asList().iterator();
    }

    public static <A> Eq<Maybe<A>> eq(final Eq<? super A> eqA) {
        return new Eq<Maybe<A>>() {

            @Override
            public boolean eq(Maybe<A> one, Maybe<A> two) {
                for (A a1 : one) {
                    for (A a2 : two) {
                        return eqA.eq(a1, a2);
                    }
                    return false;
                }
                return two.isNothing();
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Maybe) {
            Maybe<?> that = (Maybe) obj;
            if (this.isNothing()) return that.isNothing();
            else return that.isJust() && this.get().equals(that.get());
        } else return false;
    }

    @Override
    public int hashCode() {
        return cata(0, Objects.<A>hashCodeFn());
    }

    public static <A> List<A> justs(List<Maybe<A>> list) {
        List<A> result = List.Nil();
        for (Maybe<A> maybe : list)
            for (A a : maybe)
                result = List.Cons(a, result);
        return result.reverse();
    }

    public static final MonadPlus<µ> monad = new MaybeMonadPlus();

    private static class MaybeMonadPlus extends MonadAbstract<µ> implements MonadPlus<µ> {
        @Override
        public <A> _<µ, A> pure(A a) {
            return Just(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, F1<A, B>> fn, _<µ, A> nestedA) {
            for (F1<A, B> f1 : narrow(fn)) {
                for (A a : narrow(nestedA)) {
                    return Just(f1.$(a));
                }
            }
            return Nothing();
        }

        public <A, B> _<µ, B> map(final F1<A, B> fn, _<µ, A> nestedA) {
            return Maybe.narrow(nestedA).map(fn);
        }

        @Override
        public <A, B> _<µ, B> bind(_<µ, A> nestedA, F1<A, _<µ, B>> fn) {
            for (A a : narrow(nestedA)) {
                return fn.$(a);
            }
            return Nothing();
        }

        @Override
        public <A> _<µ, A> mzero() {
            return Nothing();
        }

        @Override
        public <A> _<µ, A> mplus(_<µ, A> one, _<µ, A> two) {
            Maybe<A> maybeOne = narrow(one);
            Maybe<A> maybeTwo = narrow(two);
            return maybeOne.orElse(maybeTwo);
        }
    }

    public final static Alt<µ> alt = new AltAbstract<µ>() {
        @Override
        public <A> _<µ, A> mplus(_<µ, A> first, _<µ, A> second) {
            return narrow(first).isNothing() ? second : first;
        }

        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }
    };

}
