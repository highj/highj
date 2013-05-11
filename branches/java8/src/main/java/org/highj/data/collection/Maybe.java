package org.highj.data.collection;

import org.highj._;
import org.highj.data.collection.maybe.*;
import org.highj.data.compare.Eq;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

    @SuppressWarnings("rawtypes")
    private final static Maybe<Object> NOTHING = new Maybe<Object>() {

        @Override
        public <B> B cata(B defaultValue, Function<Object, B> fn) {
            return defaultValue;
        }

        @Override
        public <B> B cataLazy(Supplier<B> defaultThunk, Function<Object, B> fn) {
            return defaultThunk.get();
        }
    };

    @SuppressWarnings("unchecked")
    public static <A> Maybe<A> Nothing() {
        return (Maybe) NOTHING;
    }

    public static <A> Maybe<A> Just(final A value) {
        if (value == null) {
            throw new IllegalArgumentException("Just() can't take null argument");
        }
        return new Maybe<A>() {
            public <B> B cataLazy(Supplier<B> defaultThunk, Function<A, B> fn) {
                return fn.apply(value);
            }

            @Override
            public <B> B cata(B defaultValue, Function<A, B> fn) {
                return fn.apply(value);
            }
        };
    }

    public static <A> Maybe<A> JustLazy(final Supplier<A> thunk) {
        return new Maybe<A>() {

            public <B> B cataLazy(Supplier<B> defaultThunk, Function<A, B> fn) {
                return fn.apply(thunk.get());
            }

            @Override
            public <B> B cata(B defaultValue, Function<A, B> fn) {
                return fn.apply(thunk.get());
            }
        };
    }

    public abstract <B> B cata(B defaultValue, Function<A, B> fn);

    public abstract <B> B cataLazy(Supplier<B> defaultThunk, Function<A, B> fn);

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
        return cataLazy(defaultThunk, x -> x);
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

    public void forEach(Consumer<? super A> consumer) {
        if (isJust()) consumer.accept(get());
    }

    public Maybe<A> filter(Predicate<? super A> predicate) {
        return isJust() && predicate.test(get()) ? this : Nothing();
    }

    public List<A> asList() {
        return cata(List.<A>nil(), x -> List.<A>empty().plus(x));
    }

    public <B> Maybe<B> bind(Function<A, Maybe<B>> fn) {
        return cata(Maybe.<B>Nothing(), fn);
    }

    public <B> Maybe<B> map(Function<? super A, ? extends B> fn) {
        return bind(a -> Maybe.<B>Just(fn.apply(a)));
    }

    @SuppressWarnings("unchecked")
    public static <A> Maybe<A> narrow(_<µ, A> value) {
        return (Maybe) value;
    }

    public Iterator<A> iterator() {
        return asList().iterator();
    }

    @Override
    public String toString() {
        return cata(SHOW_NOTHING, a -> String.format(SHOW_JUST, a));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Maybe) {
            Maybe<?> that = (Maybe) obj;
            return cata(that.isNothing(), x -> that.<Boolean>cata(false, x::equals));
        } else return false;
    }

    public static <A> Eq<Maybe<A>> eq(final Eq<? super A> eqA) {
        return (one, two) -> one.cata(two.isNothing(),
                x -> two.<Boolean>cata(false, y -> eqA.eq(x, y)));
    }

    @Override
    public int hashCode() {
        return cata(0, Object::hashCode);
    }

    public static <A> List<A> justs(List<Maybe<A>> list) {
        Stack<A> result = new Stack<>();
        for (Maybe<A> maybe : list) {
            maybe.forEach(result::push);
        }
        return List.buildFromStack(result);
    }

    public static final MonadPlus<µ> monadPlus = new MaybeMonadPlus();

    public static Traversable<µ> traversable = new MaybeTraversable();

    public static <A> Monoid<Maybe<A>> firstMonoid() {
        return new MaybeFirstMonoid<>();
    }

    public static <A> Monoid<Maybe<A>> lastMonoid() {
        return new MaybeLastMonoid<>();
    }

    public static <A> Monoid<Maybe<A>> monoid(final Semigroup<A> semigroup) {
        return new MaybeMonoidFromSemigroup<A>(semigroup);
    }


}
