package org.highj.data.tuple;

import org.derive4j.hkt.__;
import org.highj.data.HList;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ord1;
import org.highj.data.predicates.Pred1;
import org.highj.data.tuple.t1.*;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An immutable tuple of arity 1, a.k.a. "cell" or "Id".
 */
public abstract class T1<A> implements __<T1.µ, A>, Supplier<A> {

    public interface µ {
    }

    private T1() {
    }

    @Override
    public A get() {
        return _1();
    }

    /**
     * Returns the only element of the tuple.
     *
     * @return the element
     */
    public abstract A _1();

    /**
     * Constructs a {@link T1} from a value.
     *
     * @param a   the value
     * @param <A> the value type
     * @return the unary tuple
     */
    public static <A> T1<A> of(A a) {
        Objects.requireNonNull(a);
        return new T1<A>() {
            @Override
            public A _1() {
                return a;
            }
        };
    }

    /**
     * Constructs a {@link T1} from a value in a lazy fashion.
     *
     * @param supplier the supplier for the value
     * @param <A>      the value type
     * @return the unary tuple
     */
    public static <A> T1<A> of$(Supplier<A> supplier) {
        return new T1<A>() {
            @Override
            public A _1() {
                return Objects.requireNonNull(supplier.get());
            }
        };
    }

    @Override
    public String toString() {
        return String.format("(%s)", _1());
    }

    /**
     * Converts the wrapped value using the given function.
     *
     * @param fn  the transformation function
     * @param <B> the new element type
     * @return the transformed tuple
     */
    public <B> T1<B> map(Function<? super A, ? extends B> fn) {
        return of(fn.apply(_1()));
    }

    /**
     * Converts the wrapped value using the given function in a lazy fashion.
     *
     * @param fn  the transformation function
     * @param <B> the new element type
     * @return the transformed tuple
     */
    public <B> T1<B> map$(Function<? super A, ? extends B> fn) {
        return of$(() -> fn.apply(_1()));
    }

    /**
     * Applies the wrapped function to the wrapped value.
     *
     * @param nestedFn the wrapped transformation function
     * @param <B>      the new element type
     * @return the transformed tuple
     */
    public <B> T1<B> ap(T1<Function<A, B>> nestedFn) {
        return map(nestedFn._1());
    }

    /**
     * Applies the wrapped function to the wrapped value in a lazy fashion.
     *
     * @param nestedFn the wrapped transformation function
     * @param <B>      the new element type
     * @return the transformed tuple
     */
    public <B> T1<B> ap$(T1<Function<A, B>> nestedFn) {
        return T1.of$(() -> nestedFn._1().apply(_1()));
    }

    /**
     * Applies the function with {@link T1} return type to the wrapped value.
     *
     * @param fn  the transformation function
     * @param <B> the new element type
     * @return the transformed tuple
     */
    public <B> T1<B> bind(Function<A, T1<B>> fn) {
        return fn.apply(_1());
    }

    /**
     * Applies the function with {@link T1} return type to the wrapped value in a lazy fashion.
     *
     * @param fn  the transformation function
     * @param <B> the new element type
     * @return the transformed tuple
     */
    public <B> T1<B> bind$(Function<A, T1<B>> fn) {
        return of$(() -> fn.apply(_1())._1());
    }

    /**
     * The catamorphism of {@link T1}.
     *
     * @param fn  the function to be applied to the value
     * @param <B> the result type
     * @return the result of the function application
     */
    public <B> B cata(Function<? super A, ? extends B> fn) {
        return fn.apply(_1());
    }

    @Override
    public int hashCode() {
        return _1().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T1) {
            T1<?> that = (T1) o;
            return this._1().equals(that._1());
        }
        return false;
    }

    /**
     * Merges two {@link T1} into a single one using the given function.
     *
     * @param a   the first tuple
     * @param b   the second tuple
     * @param fn  the merge function
     * @param <A> the element type of the first tuple
     * @param <B> the element type of the second tuple
     * @param <C> the element type of the result
     * @return the merged tuple
     */
    public static <A, B, C> T1<C> merge(T1<A> a, T1<B> b, BiFunction<A, B, C> fn) {
        return of(fn.apply(a._1(), b._1()));
    }

    /**
     * Merges two {@link T1} into a single one using the given function in a lazy fashion.
     *
     * @param a   the first tuple
     * @param b   the second tuple
     * @param fn  the merge function
     * @param <A> the element type of the first tuple
     * @param <B> the element type of the second tuple
     * @param <C> the element type of the result
     * @return the merged tuple
     */
    public static <A, B, C> T1<C> merge$(T1<A> a, T1<B> b, BiFunction<A, B, C> fn) {
        return of$(() -> fn.apply(a._1(), b._1()));
    }

    /**
     * The {@link Eq} instance.
     *
     * @param eqA the {@link Eq} instance of the element type
     * @param <A> the element type
     * @return the instance
     */
    public static <A> Eq<T1<A>> eq(Eq<? super A> eqA) {
        return (one, two) -> eqA.eq(one._1(), two._1());
    }

    /**
     * The {@link Eq1} instance.
     */
    public static final T1Eq1 eq1 = new T1Eq1() {
    };

    /**
     * The {@link Ord} instance.
     *
     * @param ordA the {@link Ord} instance of the element type
     * @param <A>  the element type
     * @return the instance
     */
    public static <A> Ord<T1<A>> ord(Ord<? super A> ordA) {
        return (one, two) -> ordA.cmp(one._1(), two._1());
    }

    /**
     * The {@link Ord1} instance.
     */
    public static final T1Ord1 ord1 = new T1Ord1() {
    };

    /**
     * The {@link Functor} instance.
     */
    public static final T1Functor functor = new T1Functor() {
    };

    /**
     * The {@link Applicative} instance.
     */
    public static final T1Applicative applicative = new T1Applicative() {
    };

    /**
     * The {@link Monad} instance.
     */
    public static final T1Monad monad = new T1Monad() {
    };

    /**
     * The {@link MonadRec} instance.
     */
    public static final T1MonadRec monadRec = new T1MonadRec() {
    };

    /**
     * The {@link Comonad} instance.
     */
    public static final T1Comonad comonad = new T1Comonad() {
    };

    /**
     * The {@link Semigroup} instance.
     *
     * @param semigroupA the semigroup of the element type
     * @param <A>        the element type
     * @return the instance
     */
    public static <A> T1Semigroup<A> semigroup(Semigroup<A> semigroupA) {
        return () -> semigroupA;
    }

    /**
     * The {@link Monoid} instance.
     *
     * @param monoidA the monoid of the element type
     * @param <A>     the element type
     * @return the instance
     */
    public static <A> T1Monoid<A> monoid(Monoid<A> monoidA) {
        return () -> monoidA;
    }

    /**
     * The {@link Group} instance.
     *
     * @param groupA the group of the element type
     * @param <A>    the element type
     * @return the instance
     */
    public static <A> T1Group<A> group(Group<A> groupA) {
        return () -> groupA;
    }

    /**
     * The {@link Pred1} instance.
     */
    public static final T1Pred1 pred1 = new T1Pred1() {
    };

    /**
     * Represents the tuple as heterogenous list.
     *
     * @return the {@link HList}
     */
    public HList.HCons<A, HList.HNil> toHList() {
        return HList.single(_1());
    }

}
