package org.highj.data.tuple;

import org.derive4j.hkt.__2;
import org.highj.data.HList;
import org.highj.data.HList.HCons;
import org.highj.data.HList.HNil;
import org.highj.data.eq.Eq;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.t2.*;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.*;
import org.highj.typeclass2.bifoldable.Bifoldable;
import org.highj.typeclass2.bifunctor.Biapplicative;
import org.highj.typeclass2.bifunctor.Biapply;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An immutable tuple of arity 2, a.k.a. "pair".
 *
 * @param <A> the type of the first element
 * @param <B> the type of the second element
 */
public abstract class T2<A, B> implements __2<T2.µ, A, B> {

    public interface µ {
    }

    private T2() {
    }

    /**
     * Extracts the first element of the tuple.
     *
     * @return the first value
     */
    public abstract A _1();

    /**
     * Extrcts the second element of the tuple.
     *
     * @return the second value
     */
    public abstract B _2();

    /**
     * Constructs a {@link T2} tuple from two values.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <A> the type of the first element
     * @param <B> the type of the second element
     * @return the binary tuple
     */
    public static <A, B> T2<A, B> of(A a, B b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return new T2<A, B>() {

            @Override
            public A _1() {
                return a;
            }

            @Override
            public B _2() {
                return b;
            }
        };
    }

    /**
     * Constructs a {@link T2} tuple in a lazy fashion.
     *
     * @param supplierA the supplier of the first value
     * @param supplierB the supplier of the second value
     * @param <A>       the type of the first element
     * @param <B>       the type of the second element
     * @return the binary tuple
     */
    public static <A, B> T2<A, B> of$(Supplier<A> supplierA, Supplier<B> supplierB) {
        return new T2<A, B>() {

            @Override
            public A _1() {
                return Objects.requireNonNull(supplierA.get());
            }

            @Override
            public B _2() {
                return Objects.requireNonNull(supplierB.get());
            }
        };
    }

    /**
     * Maps both elements of the tuple at the same time.
     *
     * @param fn1  the first transformation function
     * @param fn2  the second transformation function
     * @param <AA> Nthe type of the first element
     * @param <BB> Nthe type of the second element
     * @return the transformed tuple
     */
    public <AA, BB> T2<AA, BB> bimap(Function<? super A, ? extends AA> fn1, Function<? super B, ? extends BB> fn2) {
        return T2.of(fn1.apply(_1()), fn2.apply(_2()));
    }

    /**
     * Maps both elements of the tuple in a lazy fashion.
     *
     * @param fn1  the first transformation function
     * @param fn2  the second transformation function
     * @param <AA> Nthe type of the first element
     * @param <BB> Nthe type of the second element
     * @return the transformed tuple
     */
    public <AA, BB> T2<AA, BB> bimap$(Function<? super A, ? extends AA> fn1, Function<? super B, ? extends BB> fn2) {
        return T2.of$(() -> fn1.apply(_1()), () -> fn2.apply(_2()));
    }

    /**
     * Maps the first element of the tuple.
     *
     * @param fn   the transformation function
     * @param <AA> Nthe type of the first element
     * @return the transformed tuple
     */
    public <AA> T2<AA, B> map_1(Function<? super A, ? extends AA> fn) {
        return T2.of(fn.apply(_1()), _2());
    }

    /**
     * Maps the first element of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <AA> Nthe type of the first element
     * @return the transformed tuple
     */
    public <AA> T2<AA, B> map_1$(Function<? super A, ? extends AA> fn) {
        return T2.of$(() -> fn.apply(_1()), this::_2);
    }

    /**
     * Maps the second element of the tuple.
     *
     * @param fn   the transformation function
     * @param <BB> Nthe type of the second element
     * @return the transformed tuple
     */
    public <BB> T2<A, BB> map_2(Function<? super B, ? extends BB> fn) {
        return T2.of(_1(), fn.apply(_2()));
    }

    /**
     * Maps the second element of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <BB> Nthe type of the second element
     * @return the transformed tuple
     */
    public <BB> T2<A, BB> map_2$(Function<? super B, ? extends BB> fn) {
        return T2.of$(this::_1, () -> fn.apply(_2()));
    }

    /**
     * The catamorphism of {@link T2}.
     *
     * @param fn  the transformation function
     * @param <C> the result value
     * @return the result
     */
    public <C> C cata(BiFunction<? super A, ? super B, ? extends C> fn) {
        return fn.apply(_1(), _2());
    }

    /**
     * Constructs a tuple with swapped elements.
     *
     * @return the transformed tuple
     */
    public T2<B, A> swap() {
        return of(_2(), _1());
    }

    /**
     * Constructs a tuple with swapped elements in a lazy fashion.
     *
     * @return the transformed tuple
     */
    public T2<B, A> swap$() {
        return T2.of$(this::_2, this::_1);
    }

    /**
     * Merges two tuples using two functions.
     *
     * @param a    the first tuple
     * @param b    the second tuple
     * @param fn1  the first merging function
     * @param fn2  the second merging function
     * @param <A1> the type of the first element of the first tuple
     * @param <A2> the type of the second element of the first tuple
     * @param <B1> the type of the first element of the second tuple
     * @param <B2> the type of the second element of the second tuple
     * @param <C1> the type of the first element of the merged tuple
     * @param <C2> the type of the second element of the merged tuple
     * @return the merged tuple
     */
    public static <A1, A2, B1, B2, C1, C2> T2<C1, C2> merge(T2<A1, A2> a, T2<B1, B2> b, BiFunction<A1, B1, C1> fn1, BiFunction<A2, B2, C2> fn2) {
        return T2.of(fn1.apply(a._1(), b._1()), fn2.apply(a._2(), b._2()));
    }

    /**
     * Merges two tuples using two functions in a lazy fashion.
     *
     * @param a    the first tuple
     * @param b    the second tuple
     * @param fn1  the first merging function
     * @param fn2  the second merging function
     * @param <A1> the type of the first element of the first tuple
     * @param <A2> the type of the second element of the first tuple
     * @param <B1> the type of the first element of the second tuple
     * @param <B2> the type of the second element of the second tuple
     * @param <C1> the type of the first element of the merged tuple
     * @param <C2> the type of the second element of the merged tuple
     * @return the merged tuple
     */
    public static <A1, A2, B1, B2, C1, C2> T2<C1, C2> merge$(T2<A1, A2> a, T2<B1, B2> b, BiFunction<A1, B1, C1> fn1, BiFunction<A2, B2, C2> fn2) {
        return T2.of$(() -> fn1.apply(a._1(), b._1()), () -> fn2.apply(a._2(), b._2()));
    }

    /**
     * Converts the tuple to a heterogenous list.
     *
     * @return the {@link HList}
     */
    public HCons<A, HCons<B, HNil>> toHList() {
        return HList.cons(_1(), HList.cons(_2(), HList.nil));
    }

    /**
     * Converts the tuple to  map entry.
     *
     * @return the {@link java.util.Map.Entry}
     */
    public Map.Entry<A, B> toMapEntry() {
        return new Map.Entry<A, B>() {
            @Override
            public A getKey() {
                return _1();
            }

            @Override
            public B getValue() {
                return _2();
            }

            @Override
            public B setValue(B value) {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Constructs a tuple from a {@link java.util.Map.Entry}
     *
     * @param entry the map entry
     * @param <A>   the type of the first element
     * @param <B>   the type of the second element
     * @return the tuple
     */
    public static <A, B> T2<A, B> fromMapEntry(Map.Entry<A, B> entry) {
        return T2.of(entry.getKey(), entry.getValue());
    }

    @Override
    public int hashCode() {
        return 31 * _1().hashCode() + 37 * _2().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T2) {
            T2<?, ?> that = (T2) o;
            return this._1().equals(that._1())
                       && this._2().equals(that._2());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", _1(), _2());
    }

    /**
     * The {@link Eq} instance.
     *
     * @param eqA the eq instance of the type of the first element
     * @param eqB the eq instance of the type of the second element
     * @param <A> the type of the first element
     * @param <B> the type of the second element
     * @return the instance
     */
    public static <A, B> Eq<T2<A, B>> eq(Eq<? super A> eqA, Eq<? super B> eqB) {
        return (one, two) -> eqA.eq(one._1(), two._1())
                                 && eqB.eq(one._2(), two._2());
    }

    /**
     * The {@link Ord} instance.
     *
     * @param ordA the ord instance of the type of the first element
     * @param ordB the ord instance of the type of the second element
     * @param <A>  the type of the first element
     * @param <B>  the type of the second element
     * @return the instance
     */
    public static <A, B> Ord<T2<A, B>> ord(Ord<? super A> ordA, Ord<? super B> ordB) {
        return (one, two) -> ordA.cmp(one._1(), two._1())
                                 .andThen(ordB.cmp(one._2(), two._2()));
    }

    /**
     * The {@link Semigroup} instance.
     *
     * @param semigroupA the semigroup of the type of the first element
     * @param semigroupB the semigroup of the type of the second element
     * @param <A>        the type of the first element
     * @param <B>        the type of the second element
     * @return the instance
     */
    public static <A, B> T2Semigroup<A, B> semigroup(Semigroup<A> semigroupA, Semigroup<B> semigroupB) {
        return new T2Semigroup<A, B>() {
            @Override
            public Semigroup<A> getA() {
                return semigroupA;
            }

            @Override
            public Semigroup<B> getB() {
                return semigroupB;
            }
        };
    }

    /**
     * The {@link Monoid} instance.
     *
     * @param monoidA the monoid instance of the type of the first element
     * @param monoidB the monoid instance of the type of the second element
     * @param <A>     the type of the first element
     * @param <B>     the type of the second element
     * @return the instance
     */
    public static <A, B> T2Monoid<A, B> monoid(Monoid<A> monoidA, Monoid<B> monoidB) {
        return new T2Monoid<A, B>() {
            @Override
            public Monoid<A> getA() {
                return monoidA;
            }

            @Override
            public Monoid<B> getB() {
                return monoidB;
            }
        };
    }

    /**
     * The {@link Group} instance.
     *
     * @param groupA the group instance of the type of the first element
     * @param groupB the group instance of the type of the second element
     * @param <A>    the type of the first element
     * @param <B>    the type of the second element
     * @return the instance
     */
    public static <A, B> T2Group<A, B> group(Group<A> groupA, Group<B> groupB) {
        return new T2Group<A, B>() {
            @Override
            public Group<A> getA() {
                return groupA;
            }

            @Override
            public Group<B> getB() {
                return groupB;
            }
        };
    }

    /**
     * The {@link Functor} instance.
     *
     * @param <M> the type of the first element
     * @return the instance
     */
    public static <M> T2Functor<M> functor() {
        return new T2Functor<M>() {
        };
    }

    /**
     * The {@link Apply} instance.
     *
     * @param mSemigroup the semigroup of the type of the first element
     * @param <M>        the type of the first element
     * @return the instance
     */
    public static <M> T2Apply<M> apply(Semigroup<M> mSemigroup) {
        return () -> mSemigroup;
    }

    /**
     * The {@link Applicative} instance.
     *
     * @param mMonoid the monoid of the type of the first element
     * @param <M>     the type of the first element
     * @return the instance
     */
    public static <M> T2Applicative<M> applicative(Monoid<M> mMonoid) {
        return () -> mMonoid;
    }

    /**
     * The {@link Bind} instance.
     *
     * @param mSemigroup the semigroup of the type of the first element
     * @param <M>        the type of the first element
     * @return the instance
     */
    public static <M> T2Bind<M> bind(Semigroup<M> mSemigroup) {
        return () -> mSemigroup;
    }

    /**
     * The {@link Monad} instance.
     *
     * @param mMonoid the monoid of the type of the first element
     * @param <M>     the type of the first element
     * @return the instance
     */
    public static <M> T2Monad<M> monad(Monoid<M> mMonoid) {
        return () -> mMonoid;
    }

    /**
     * The {@link MonadRec} instance.
     *
     * @param mMonoid the monoid of the type of the first element
     * @param <M>     the type of the first element
     * @return instance
     */
    public static <M> T2MonadRec<M> monadRec(Monoid<M> mMonoid) {
        return () -> mMonoid;
    }

    /**
     * The {@link Comonad} instance.
     *
     * @param <M> the monoid of the type of the first element
     * @return the instance
     */
    public static <M> T2Comonad<M> comonad() {
        return new T2Comonad<M>() {
        };
    }

    /**
     * The {@link Bifunctor} instance.
     */
    public static final T2Bifunctor bifunctor = new T2Bifunctor() {
    };

    /**
     * The {@link Biapply} instance.
     */
    public static final T2Biapply biapply = new T2Biapply() {
    };

    /**
     * The {@link Biapplicative} instance.
     */
    public static final T2Biapplicative biapplicative = new T2Biapplicative() {
    };

    /**
     * The {@link Bifoldable} instance.
     */
    public static final T2Bifoldable bifoldable = new T2Bifoldable() {
    };
}

