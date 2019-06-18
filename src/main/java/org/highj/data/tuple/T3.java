package org.highj.data.tuple;

import org.derive4j.hkt.__3;
import org.highj.data.HList;
import org.highj.data.HList.HCons;
import org.highj.data.HList.HNil;
import org.highj.data.eq.Eq;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.t3.*;
import org.highj.function.F3;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass2.bifunctor.Biapplicative;
import org.highj.typeclass2.bifunctor.Biapply;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An immutable tuple of arity 3, a.k.a. "triple".
 */
public abstract class T3<A, B, C> implements __3<T3.µ, A, B, C> {

    public interface µ {
    }

    private T3() {
    }

    /**
     * Extracts the first element of the tuple.
     *
     * @return the first element
     */
    public abstract A _1();

    /**
     * Extracts the second element of the tuple.
     *
     * @return the second element
     */
    public abstract B _2();

    /**
     * Extracts the third element of the tuple.
     *
     * @return the third element
     */
    public abstract C _3();

    /**
     * Constructs a {@link T3} from three values.
     *
     * @param a   the first value
     * @param b   the second value
     * @param c   the third value
     * @param <A> the type of the first value
     * @param <B> the type of the second value
     * @param <C> the type of the third value
     * @return the tuple
     */
    public static <A, B, C> T3<A, B, C> of(A a, B b, C c) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        Objects.requireNonNull(c);
        return new T3<A, B, C>() {

            @Override
            public A _1() {
                return a;
            }

            @Override
            public B _2() {
                return b;
            }

            @Override
            public C _3() {
                return c;
            }
        };
    }

    /**
     * Constructs a {@link T3} from three values in a lazy fashion.
     *
     * @param supplierA the supplier of the first value
     * @param supplierB the supplier of the second value
     * @param supplierC the supplier of the third value
     * @param <A>       the type of the first value
     * @param <B>       the type of the second value
     * @param <C>       the type of the third value
     * @return the tuple
     */
    public static <A, B, C> T3<A, B, C> of$(Supplier<A> supplierA, Supplier<B> supplierB, Supplier<C> supplierC) {
        return new T3<A, B, C>() {

            @Override
            public A _1() {
                return Objects.requireNonNull(supplierA.get());
            }

            @Override
            public B _2() {
                return Objects.requireNonNull(supplierB.get());
            }

            @Override
            public C _3() {
                return Objects.requireNonNull(supplierC.get());
            }
        };
    }

    /**
     * Maps all values of the tuple at the same time.
     *
     * @param fn1  the first transformation function
     * @param fn2  the second transformation function
     * @param fn3  the third transformation function
     * @param <AA> the new type of the first element
     * @param <BB> the new type of the second element
     * @param <CC> the new type of the third element
     * @return the transformed tuple
     */
    public <AA, BB, CC> T3<AA, BB, CC> trimap(
        Function<? super A, ? extends AA> fn1,
        Function<? super B, ? extends BB> fn2,
        Function<? super C, ? extends CC> fn3) {
        return of(fn1.apply(_1()), fn2.apply(_2()), fn3.apply(_3()));
    }

    /**
     * Maps all values of the tuple at the same time.
     *
     * @param fn1  the first transformation function
     * @param fn2  the second transformation function
     * @param fn3  the third transformation function
     * @param <AA> the new type of the first element
     * @param <BB> the new type of the second element
     * @param <CC> the new type of the third element
     * @return the transformed tuple
     */
    public <AA, BB, CC> T3<AA, BB, CC> trimap$(
        Function<? super A, ? extends AA> fn1,
        Function<? super B, ? extends BB> fn2,
        Function<? super C, ? extends CC> fn3) {
        return of$(() -> fn1.apply(_1()), () -> fn2.apply(_2()), () -> fn3.apply(_3()));
    }

    /**
     * Maps the first value of the tuple.
     *
     * @param fn   the transformation function
     * @param <AA> the new type of the first element
     * @return the transformed tuple
     */
    public <AA> T3<AA, B, C> map_1(Function<? super A, ? extends AA> fn) {
        return of(fn.apply(_1()), _2(), _3());
    }

    /**
     * Maps the first value of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <AA> the new type of the first element
     * @return the transformed tuple
     */
    public <AA> T3<AA, B, C> map_1$(Function<? super A, ? extends AA> fn) {
        return of$(() -> fn.apply(_1()), this::_2, this::_3);
    }

    /**
     * Maps the second value of the tuple.
     *
     * @param fn   the transformation function
     * @param <BB> the new type of the second element
     * @return the transformed tuple
     */
    public <BB> T3<A, BB, C> map_2(Function<? super B, ? extends BB> fn) {
        return of(_1(), fn.apply(_2()), _3());
    }

    /**
     * Maps the second value of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <BB> the new type of the second element
     * @return the transformed tuple
     */
    public <BB> T3<A, BB, C> map_2$(Function<? super B, ? extends BB> fn) {
        return of$(this::_1, () -> fn.apply(_2()), this::_3);
    }

    /**
     * Maps the third value of the tuple.
     *
     * @param fn   the transformation function
     * @param <CC> the new type of the third element
     * @return the transformed tuple
     */
    public <CC> T3<A, B, CC> map_3(Function<? super C, ? extends CC> fn) {
        return of(_1(), _2(), fn.apply(_3()));
    }

    /**
     * Maps the third value of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <CC> the new type of the third element
     * @return the transformed tuple
     */
    public <CC> T3<A, B, CC> map_3$(Function<? super C, ? extends CC> fn) {
        return of$(this::_1, this::_2, () -> fn.apply(_3()));
    }

    /**
     * The catamorphism of {@link T3}.
     *
     * @param fn  the function to be applied
     * @param <D> the result type
     * @return the result
     */
    public <D> D cata(F3<? super A, ? super B, ? super C, ? extends D> fn) {
        return fn.apply(_1(), _2(), _3());
    }

    @Override
    public int hashCode() {
        return 31 * _1().hashCode() + 37 * _2().hashCode() + 41 * _3().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T3) {
            T3<?, ?, ?> that = (T3) o;
            return this._1().equals(that._1())
                       && this._2().equals(that._2())
                       && this._3().equals(that._3());
        }
        return false;
    }

    /**
     * Merges two tuples using the given functions.
     *
     * @param a    the first tuple
     * @param b    the second tuple
     * @param fn1  the merging function for the first values
     * @param fn2  the merging function for the second values
     * @param fn3  the merging function for the third values
     * @param <A1> the type of the first element of the first tuple
     * @param <A2> the type of the second element of the first tuple
     * @param <A3> the type of the third element of the first tuple
     * @param <B1> the type of the first element of the second tuple
     * @param <B2> the type of the second element of the second tuple
     * @param <B3> the type of the third element of the second tuple
     * @param <C1> the type of the first element of the result
     * @param <C2> the type of the second element of the result
     * @param <C3> the type of the third element of the result
     * @return the merged tuple
     */
    public static <A1, A2, A3, B1, B2, B3, C1, C2, C3> T3<C1, C2, C3> merge(
        T3<A1, A2, A3> a, T3<B1, B2, B3> b,
        BiFunction<A1, B1, C1> fn1,
        BiFunction<A2, B2, C2> fn2,
        BiFunction<A3, B3, C3> fn3) {
        return of(fn1.apply(a._1(), b._1()),
            fn2.apply(a._2(), b._2()),
            fn3.apply(a._3(), b._3()));
    }

    /**
     * Merges two tuples using the given functions in a lazy fashion.
     *
     * @param a    the first tuple
     * @param b    the second tuple
     * @param fn1  the merging function for the first values
     * @param fn2  the merging function for the second values
     * @param fn3  the merging function for the third values
     * @param <A1> the type of the first element of the first tuple
     * @param <A2> the type of the second element of the first tuple
     * @param <A3> the type of the third element of the first tuple
     * @param <B1> the type of the first element of the second tuple
     * @param <B2> the type of the second element of the second tuple
     * @param <B3> the type of the third element of the second tuple
     * @param <C1> the type of the first element of the result
     * @param <C2> the type of the second element of the result
     * @param <C3> the type of the third element of the result
     * @return the merged tuple
     */
    public static <A1, A2, A3, B1, B2, B3, C1, C2, C3> T3<C1, C2, C3> merge$(
        T3<A1, A2, A3> a, T3<B1, B2, B3> b,
        BiFunction<A1, B1, C1> fn1,
        BiFunction<A2, B2, C2> fn2,
        BiFunction<A3, B3, C3> fn3) {
        return of$(() -> fn1.apply(a._1(), b._1()),
            () -> fn2.apply(a._2(), b._2()),
            () -> fn3.apply(a._3(), b._3()));
    }

    /**
     * The {@link Eq} instance.
     *
     * @param eqA the eq instance of the type of the first value
     * @param eqB the eq instance of the type of the second value
     * @param eqC the eq instance of the type of the third value
     * @param <A> the type of the first value
     * @param <B> the type of the second value
     * @param <C> the type of the third value
     * @return the instance
     */
    public static <A, B, C> Eq<T3<A, B, C>> eq(Eq<? super A> eqA, Eq<? super B> eqB, Eq<? super C> eqC) {
        return (one, two) -> eqA.eq(one._1(), two._1()) && eqB.eq(one._2(), two._2()) && eqC.eq(one._3(), two._3());
    }

    /**
     * The {@link Ord} instance.
     *
     * @param ordA the ord instance of the type of the first value
     * @param ordB the ord instance of the type of the second value
     * @param ordC the ord instance of the type of the third value
     * @param <A>  the type of the first value
     * @param <B>  the type of the second value
     * @param <C>  the type of the third value
     * @return the instance
     */
    public static <A, B, C> Ord<T3<A, B, C>> ord(Ord<? super A> ordA, Ord<? super B> ordB, Ord<? super C> ordC) {
        return (one, two) -> ordA.cmp(one._1(), two._1())
                                 .andThen(ordB.cmp(one._2(), two._2()))
                                 .andThen(ordC.cmp(one._3(), two._3()));
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", _1(), _2(), _3());
    }

    /**
     * The {@link Functor} instance.
     *
     * @param <S> the type of the first element
     * @param <T> the type of the second element
     * @return the instance
     */
    public static <S, T> T3Functor<S, T> functor() {
        return new T3Functor<S, T>() {
        };
    }

    /**
     * The {@link Apply} instance.
     *
     * @param sSemigroup the semigroup of the type of the first element
     * @param tSemigroup the semigroup of the type of the second element
     * @param <S>        the type of the first element
     * @param <T>        the type of the second element
     * @return the instance
     */
    public static <S, T> T3Apply<S, T> apply(Semigroup<S> sSemigroup, Semigroup<T> tSemigroup) {
        return new T3Apply<S, T>() {
            @Override
            public Semigroup<S> getS() {
                return sSemigroup;
            }

            @Override
            public Semigroup<T> getT() {
                return tSemigroup;
            }
        };
    }

    /**
     * The {@link Applicative} instance.
     *
     * @param sMonoid the monoid of the type of the first element
     * @param tMonoid the monoid of the type of the second element
     * @param <S>     the type of the first element
     * @param <T>     the type of the second element
     * @return the instance
     */
    public static <S, T> T3Applicative<S, T> applicative(Monoid<S> sMonoid, Monoid<T> tMonoid) {
        return new T3Applicative<S, T>() {
            @Override
            public Monoid<S> getS() {
                return sMonoid;
            }

            @Override
            public Monoid<T> getT() {
                return tMonoid;
            }
        };
    }

    /**
     * The {@link Bind} instance.
     *
     * @param sSemigroup the semigroup of the type of the first element
     * @param tSemigroup the semigroup of the type of the second element
     * @param <S>        the type of the first element
     * @param <T>        the type of the second element
     * @return the instance
     */
    public static <S, T> T3Bind<S, T> bind(Semigroup<S> sSemigroup, Semigroup<T> tSemigroup) {
        return new T3Bind<S, T>() {
            @Override
            public Semigroup<S> getS() {
                return sSemigroup;
            }

            @Override
            public Semigroup<T> getT() {
                return tSemigroup;
            }
        };
    }

    /**
     * The {@link Monad} instance.
     *
     * @param sMonoid the monoid of the type of the first element
     * @param tMonoid the monoid of the type of the second element
     * @param <S>     the type of the first element
     * @param <T>     the type of the second element
     * @return the instance
     */
    public static <S, T> T3Monad<S, T> monad(Monoid<S> sMonoid, Monoid<T> tMonoid) {
        return new T3Monad<S, T>() {
            @Override
            public Monoid<S> getS() {
                return sMonoid;
            }

            @Override
            public Monoid<T> getT() {
                return tMonoid;
            }
        };
    }

    /**
     * The {@link Comonad} instance.
     *
     * @param <S> the type of the first element
     * @param <T> the type of the second element
     * @return the instance
     */
    public static <S, T> T3Comonad<S, T> comonad() {
        return new T3Comonad<S, T>() {
        };
    }

    /**
     * The {@link Semigroup} instance.
     *
     * @param semigroupA the semigroup of the type of the first element
     * @param semigroupB the semigroup of the type of the second element
     * @param semigroupC the semigroup of the type of the third element
     * @param <A>        the type of the first element
     * @param <B>        the type of the second element
     * @param <C>        the type of the third element
     * @return the instance
     */
    public static <A, B, C> T3Semigroup<A, B, C> semigroup(
        Semigroup<A> semigroupA,
        Semigroup<B> semigroupB,
        Semigroup<C> semigroupC) {
        return new T3Semigroup<A, B, C>() {
            @Override
            public Semigroup<A> getA() {
                return semigroupA;
            }

            @Override
            public Semigroup<B> getB() {
                return semigroupB;
            }

            @Override
            public Semigroup<C> getC() {
                return semigroupC;
            }
        };
    }

    /**
     * The {@link Monoid} instance.
     *
     * @param monoidA the monoid of the type of the first element
     * @param monoidB the monoid of the type of the second element
     * @param monoidC the monoid of the type of the third element
     * @param <A>     the type of the first element
     * @param <B>     the type of the second element
     * @param <C>     the type of the third element
     * @return the instance
     */
    public static <A, B, C> T3Monoid<A, B, C> monoid(Monoid<A> monoidA, Monoid<B> monoidB, Monoid<C> monoidC) {
        return new T3Monoid<A, B, C>() {
            @Override
            public Monoid<A> getA() {
                return monoidA;
            }

            @Override
            public Monoid<B> getB() {
                return monoidB;
            }

            @Override
            public Monoid<C> getC() {
                return monoidC;
            }
        };
    }

    /**
     * The {@link Group} instance.
     *
     * @param groupA the group of the type of the first element
     * @param groupB the group of the type of the second element
     * @param groupC the group of the type of the third element
     * @param <A>    the type of the first element
     * @param <B>    the type of the second element
     * @param <C>    the type of the third element
     * @return the instance
     */
    public static <A, B, C> T3Group<A, B, C> group(Group<A> groupA, Group<B> groupB, Group<C> groupC) {
        return new T3Group<A, B, C>() {
            @Override
            public Group<A> getA() {
                return groupA;
            }

            @Override
            public Group<B> getB() {
                return groupB;
            }

            @Override
            public Group<C> getC() {
                return groupC;
            }
        };
    }

    /**
     * The {@link Bifunctor} instance.
     *
     * @param <S> the type of the first element
     * @return the instance
     */
    public static <S> T3Bifunctor<S> bifunctor() {
        return new T3Bifunctor<S>() {
        };
    }

    /**
     * The {@link Biapply} instance.
     *
     * @param sSemigroup the semigroup of the type of the first element
     * @param <S>        the type of the first element
     * @return the instance
     */
    public static <S> T3Biapply<S> biapply(Semigroup<S> sSemigroup) {
        return () -> sSemigroup;
    }

    /**
     * The {@link Biapplicative} instance.
     *
     * @param sMonoid the monoid of the type of the first element
     * @param <S>     the type of the first element
     * @return the instance
     */
    public static <S> T3Biapplicative<S> biapplicative(Monoid<S> sMonoid) {
        return () -> sMonoid;
    }

    /**
     * Converts the tuple to a heterogenous list.
     *
     * @return the {@link HList}
     */
    public HCons<A, HCons<B, HCons<C, HNil>>> toHList() {
        return HList.cons(_1(), HList.cons(_2(), HList.cons(_3(), HList.nil)));
    }
}