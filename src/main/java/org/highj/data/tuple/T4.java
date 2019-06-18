package org.highj.data.tuple;

import org.derive4j.hkt.__4;
import org.highj.data.HList;
import org.highj.data.HList.HCons;
import org.highj.data.HList.HNil;
import org.highj.data.eq.Eq;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.t4.*;
import org.highj.function.F4;
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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An immutable tuple of arity 4, a.k.a. "quadruple".
 */
public abstract class T4<A, B, C, D> implements __4<T4.µ, A, B, C, D> {
    public interface µ {
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
     * Extracts the fourth element of the tuple.
     *
     * @return the fourth element
     */
    public abstract D _4();

    /**
     * Constructs a {@link T4} from four values.
     *
     * @param a   the first value
     * @param b   the second value
     * @param c   the third value
     * @param d   the fourth value
     * @param <A> the type of the first value
     * @param <B> the type of the second value
     * @param <C> the type of the third value
     * @param <D> the type of the fourth value
     * @return the tuple
     */
    public static <A, B, C, D> T4<A, B, C, D> of(A a, B b, C c, D d) {
        assert a != null && b != null && c != null && d != null;
        return new T4<A, B, C, D>() {

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

            @Override
            public D _4() {
                return d;
            }
        };
    }

    /**
     * Constructs a {@link T4} from four values in a lazy fashion.
     *
     * @param supplierA the supplier of the first value
     * @param supplierB the supplier of the second value
     * @param supplierC the supplier of the third value
     * @param supplierD the supplier of the fourth value
     * @param <A>       the type of the first value
     * @param <B>       the type of the second value
     * @param <C>       the type of the third value
     * @param <D>       the type of the fourth value
     * @return the tuple
     */
    public static <A, B, C, D> T4<A, B, C, D> of$(
        Supplier<A> supplierA,
        Supplier<B> supplierB,
        Supplier<C> supplierC,
        Supplier<D> supplierD) {
        return new T4<A, B, C, D>() {

            @Override
            public A _1() {
                return supplierA.get();
            }

            @Override
            public B _2() {
                return supplierB.get();
            }

            @Override
            public C _3() {
                return supplierC.get();
            }

            @Override
            public D _4() {
                return supplierD.get();
            }
        };
    }

    /**
     * Maps all values of the tuple at the same time.
     *
     * @param fn1  the first transformation function
     * @param fn2  the second transformation function
     * @param fn3  the third transformation function
     * @param fn4  the fourth transformation function
     * @param <AA> the new type of the first element
     * @param <BB> the new type of the second element
     * @param <CC> the new type of the third element
     * @param <DD> the new type of the fourth element
     * @return the transformed tuple
     */
    public <AA, BB, CC, DD> T4<AA, BB, CC, DD> quadmap(
        Function<? super A, ? extends AA> fn1,
        Function<? super B, ? extends BB> fn2,
        Function<? super C, ? extends CC> fn3,
        Function<? super D, ? extends DD> fn4) {
        return of(fn1.apply(_1()), fn2.apply(_2()), fn3.apply(_3()), fn4.apply(_4()));
    }

    /**
     * Maps all values of the tuple at the same time in a lazy fashion.
     *
     * @param fn1  the first transformation function
     * @param fn2  the second transformation function
     * @param fn3  the third transformation function
     * @param fn4  the third transformation function
     * @param <AA> the new type of the first element
     * @param <BB> the new type of the second element
     * @param <CC> the new type of the third element
     * @param <DD> the new type of the fourth element
     * @return the transformed tuple
     */
    public <AA, BB, CC, DD> T4<AA, BB, CC, DD> quadmap$(
        Function<? super A, ? extends AA> fn1,
        Function<? super B, ? extends BB> fn2,
        Function<? super C, ? extends CC> fn3,
        Function<? super D, ? extends DD> fn4) {
        return of$(() -> fn1.apply(_1()), () -> fn2.apply(_2()), () -> fn3.apply(_3()), () -> fn4.apply(_4()));
    }


    /**
     * Maps the first value of the tuple.
     *
     * @param fn   the transformation function
     * @param <AA> the new type of the first element
     * @return the transformed tuple
     */
    public <AA> T4<AA, B, C, D> map_1(Function<? super A, ? extends AA> fn) {
        return of(fn.apply(_1()), _2(), _3(), _4());
    }

    /**
     * Maps the first value of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <AA> the new type of the first element
     * @return the transformed tuple
     */
    public <AA> T4<AA, B, C, D> map_1$(Function<? super A, ? extends AA> fn) {
        return of$(() -> fn.apply(_1()), this::_2, this::_3, this::_4);
    }

    /**
     * Maps the second value of the tuple.
     *
     * @param fn   the transformation function
     * @param <BB> the new type of the second element
     * @return the transformed tuple
     */
    public <BB> T4<A, BB, C, D> map_2(Function<? super B, ? extends BB> fn) {
        return of(_1(), fn.apply(_2()), _3(), _4());
    }

    /**
     * Maps the second value of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <BB> the new type of the second element
     * @return the transformed tuple
     */
    public <BB> T4<A, BB, C, D> map_2$(Function<? super B, ? extends BB> fn) {
        return of$(this::_1, () -> fn.apply(_2()), this::_3, this::_4);
    }

    /**
     * Maps the third value of the tuple.
     *
     * @param fn   the transformation function
     * @param <CC> the new type of the third element
     * @return the transformed tuple
     */
    public <CC> T4<A, B, CC, D> map_3(Function<? super C, ? extends CC> fn) {
        return of(_1(), _2(), fn.apply(_3()), _4());
    }

    /**
     * Maps the third value of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <CC> the new type of the third element
     * @return the transformed tuple
     */
    public <CC> T4<A, B, CC, D> map_3$(Function<? super C, ? extends CC> fn) {
        return of$(this::_1, this::_2, () -> fn.apply(_3()), this::_4);
    }

    /**
     * Maps the fourth value of the tuple.
     *
     * @param fn   the transformation function
     * @param <DD> the new type of the fourth element
     * @return the transformed tuple
     */
    public <DD> T4<A, B, C, DD> map_4(Function<? super D, ? extends DD> fn) {
        return of(_1(), _2(), _3(), fn.apply(_4()));
    }

    /**
     * Maps the fourth value of the tuple in a lazy fashion.
     *
     * @param fn   the transformation function
     * @param <DD> the new type of the third element
     * @return the transformed tuple
     */
    public <DD> T4<A, B, C, DD> map_4$(Function<? super D, ? extends DD> fn) {
        return of$(this::_1, this::_2, this::_3, () -> fn.apply(_4()));
    }

    /**
     * The catamorphism of {@link T4}.
     *
     * @param fn  the function to be applied
     * @param <E> the result type
     * @return the result
     */
    public <E> E cata(F4<? super A, ? super B, ? super C, ? super D, ? extends E> fn) {
        return fn.apply(_1(), _2(), _3(), _4());
    }

    @Override
    public int hashCode() {
        return 31 * _1().hashCode() + 37 * _2().hashCode() + 41 * _3().hashCode() + 43 * _4().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T4) {
            T4<?, ?, ?, ?> that = (T4) o;
            return this._1().equals(that._1())
                       && this._2().equals(that._2())
                       && this._3().equals(that._3())
                       && this._4().equals(that._4());
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
     * @param fn4  the merging function for the fourth values
     * @param <A1> the type of the first element of the first tuple
     * @param <A2> the type of the second element of the first tuple
     * @param <A3> the type of the third element of the first tuple
     * @param <A4> the type of the fourth element of the first tuple
     * @param <B1> the type of the first element of the second tuple
     * @param <B2> the type of the second element of the second tuple
     * @param <B3> the type of the third element of the second tuple
     * @param <B4> the type of the fourth element of the second tuple
     * @param <C1> the type of the first element of the result
     * @param <C2> the type of the second element of the result
     * @param <C3> the type of the third element of the result
     * @param <C4> the type of the fourth element of the result
     * @return the merged tuple
     */
    public static <A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4> T4<C1, C2, C3, C4> merge(
        T4<A1, A2, A3, A4> a, T4<B1, B2, B3, B4> b,
        BiFunction<A1, B1, C1> fn1, BiFunction<A2, B2, C2> fn2,
        BiFunction<A3, B3, C3> fn3, BiFunction<A4, B4, C4> fn4) {
        return of(fn1.apply(a._1(), b._1()),
            fn2.apply(a._2(), b._2()),
            fn3.apply(a._3(), b._3()),
            fn4.apply(a._4(), b._4()));
    }

    /**
     * Merges two tuples using the given functions in a lazy fashion.
     *
     * @param a    the first tuple
     * @param b    the second tuple
     * @param fn1  the merging function for the first values
     * @param fn2  the merging function for the second values
     * @param fn3  the merging function for the third values
     * @param fn4  the merging function for the fourth values
     * @param <A1> the type of the first element of the first tuple
     * @param <A2> the type of the second element of the first tuple
     * @param <A3> the type of the third element of the first tuple
     * @param <A4> the type of the fourth element of the first tuple
     * @param <B1> the type of the first element of the second tuple
     * @param <B2> the type of the second element of the second tuple
     * @param <B3> the type of the third element of the second tuple
     * @param <B4> the type of the fourth element of the second tuple
     * @param <C1> the type of the first element of the result
     * @param <C2> the type of the second element of the result
     * @param <C3> the type of the third element of the result
     * @param <C4> the type of the fourth element of the result
     * @return the merged tuple
     */
    public static <A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4> T4<C1, C2, C3, C4> merge$(
        T4<A1, A2, A3, A4> a, T4<B1, B2, B3, B4> b,
        BiFunction<A1, B1, C1> fn1, BiFunction<A2, B2, C2> fn2,
        BiFunction<A3, B3, C3> fn3, BiFunction<A4, B4, C4> fn4) {
        return of$(() -> fn1.apply(a._1(), b._1()),
            () -> fn2.apply(a._2(), b._2()),
            () -> fn3.apply(a._3(), b._3()),
            () -> fn4.apply(a._4(), b._4()));
    }

    /**
     * The {@link Eq} instance.
     *
     * @param eqA the eq instance of the type of the first value
     * @param eqB the eq instance of the type of the second value
     * @param eqC the eq instance of the type of the third value
     * @param eqD the eq instance of the type of the fourth value
     * @param <A> the type of the first value
     * @param <B> the type of the second value
     * @param <C> the type of the third value
     * @param <D> the type of the fourth value
     * @return the instance
     */
    public static <A, B, C, D> Eq<T4<A, B, C, D>> eq(Eq<? super A> eqA, Eq<? super B> eqB,
                                                     Eq<? super C> eqC, Eq<? super D> eqD) {
        return (one, two) -> eqA.eq(one._1(), two._1())
                                 && eqB.eq(one._2(), two._2())
                                 && eqC.eq(one._3(), two._3())
                                 && eqD.eq(one._4(), two._4());
    }

    /**
     * The {@link Ord} instance.
     *
     * @param ordA the ord instance of the type of the first value
     * @param ordB the ord instance of the type of the second value
     * @param ordC the ord instance of the type of the third value
     * @param ordD the ord instance of the type of the fourth value
     * @param <A>  the type of the first value
     * @param <B>  the type of the second value
     * @param <C>  the type of the third value
     * @param <D>  the type of the fourth value
     * @return the instance
     */
    public static <A, B, C, D> Ord<T4<A, B, C, D>> ord(
        Ord<? super A> ordA, Ord<? super B> ordB,
        Ord<? super C> ordC, Ord<? super D> ordD) {
        return (one, two) -> ordA.cmp(one._1(), two._1())
                                 .andThen(ordB.cmp(one._2(), two._2()))
                                 .andThen(ordC.cmp(one._3(), two._3()))
                                 .andThen(ordD.cmp(one._4(), two._4()));
    }


    @Override
    public String toString() {
        return String.format("(%s,%s,%s,%s)", _1(), _2(), _3(), _4());
    }

    /**
     * The {@link Functor} instance.
     *
     * @param <S> the type of the first element
     * @param <T> the type of the second element
     * @param <U> the type of the third element
     * @return the instance
     */
    public static <S, T, U> T4Functor<S, T, U> functor() {
        return new T4Functor<S, T, U>() {
        };
    }

    /**
     * The {@link Bind} instance.
     *
     * @param semigroupS the semigroup of the type of the first element
     * @param semigroupT the semigroup of the type of the second element
     * @param semigroupU the semigroup of the type of the third element
     * @param <S>        the type of the first element
     * @param <T>        the type of the second element
     * @param <U>        the type of the third element
     * @return the instance
     */
    public static <S, T, U> T4Bind<S, T, U> bind(Semigroup<S> semigroupS, Semigroup<T> semigroupT, Semigroup<U> semigroupU) {
        return new T4Bind<S, T, U>() {
            @Override
            public Semigroup<S> getS() {
                return semigroupS;
            }

            @Override
            public Semigroup<T> getT() {
                return semigroupT;
            }

            @Override
            public Semigroup<U> getU() {
                return semigroupU;
            }
        };
    }

    /**
     * The {@link Apply} instance.
     *
     * @param semigroupS the semigroup of the type of the first element
     * @param semigroupT the semigroup of the type of the second element
     * @param semigroupU the semigroup of the type of the third element
     * @param <S>        the type of the first element
     * @param <T>        the type of the second element
     * @param <U>        the type of the third element
     * @return the instance
     */
    public static <S, T, U> T4Apply<S, T, U> apply(Semigroup<S> semigroupS, Semigroup<T> semigroupT, Semigroup<U> semigroupU) {
        return new T4Apply<S, T, U>() {
            @Override
            public Semigroup<S> getS() {
                return semigroupS;
            }

            @Override
            public Semigroup<T> getT() {
                return semigroupT;
            }

            @Override
            public Semigroup<U> getU() {
                return semigroupU;
            }
        };
    }

    /**
     * The {@link Applicative} instance.
     *
     * @param monoidS the monoid of the type of the first element
     * @param monoidT the monoid of the type of the second element
     * @param monoidU the monoid of the type of the third element
     * @param <S>     the type of the first element
     * @param <T>     the type of the second element
     * @param <U>     the type of the third element
     * @return the instance
     */
    public static <S, T, U> T4Applicative<S, T, U> applicative(Monoid<S> monoidS, Monoid<T> monoidT, Monoid<U> monoidU) {
        return new T4Applicative<S, T, U>() {
            @Override
            public Monoid<S> getS() {
                return monoidS;
            }

            @Override
            public Monoid<T> getT() {
                return monoidT;
            }

            @Override
            public Monoid<U> getU() {
                return monoidU;
            }
        };
    }

    /**
     * The {@link Monad} instance.
     *
     * @param monoidS the monoid of the type of the first element
     * @param monoidT the monoid of the type of the second element
     * @param monoidU the monoid of the type of the third element
     * @param <S>     the type of the first element
     * @param <T>     the type of the second element
     * @param <U>     the type of the third element
     * @return the instance
     */
    public static <S, T, U> T4Monad<S, T, U> monad(Monoid<S> monoidS, Monoid<T> monoidT, Monoid<U> monoidU) {
        return new T4Monad<S, T, U>() {
            @Override
            public Monoid<S> getS() {
                return monoidS;
            }

            @Override
            public Monoid<T> getT() {
                return monoidT;
            }

            @Override
            public Monoid<U> getU() {
                return monoidU;
            }
        };
    }

    /**
     * The {@link Comonad} instance.
     *
     * @param <S> the type of the first element
     * @param <T> the type of the second element
     * @param <U> the type of the third element
     * @return the instance
     */
    public static <S, T, U> T4Comonad<S, T, U> comonad() {
        return new T4Comonad<S, T, U>() {
        };
    }

    /**
     * The {@link Semigroup} instance.
     *
     * @param semigroupA the semigroup of the type of the first element
     * @param semigroupB the semigroup of the type of the second element
     * @param semigroupC the semigroup of the type of the third element
     * @param semigroupD the semigroup of the type of the fourth element
     * @param <A>        the type of the first element
     * @param <B>        the type of the second element
     * @param <C>        the type of the third element
     * @param <D>        the type of the fourth element
     * @return the instance
     */
    public static <A, B, C, D> T4Semigroup<A, B, C, D> semigroup(
        Semigroup<A> semigroupA,
        Semigroup<B> semigroupB,
        Semigroup<C> semigroupC,
        Semigroup<D> semigroupD) {
        return new T4Semigroup<A, B, C, D>() {
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

            @Override
            public Semigroup<D> getD() {
                return semigroupD;
            }
        };
    }

    /**
     * The {@link Monoid} instance.
     *
     * @param monoidA the monoid of the type of the first element
     * @param monoidB the monoid of the type of the second element
     * @param monoidC the monoid of the type of the third element
     * @param monoidD the monoid of the type of the fourth element
     * @param <A>     the type of the first element
     * @param <B>     the type of the second element
     * @param <C>     the type of the third element
     * @param <D>     the type of the fourth element
     * @return the instance
     */
    public static <A, B, C, D> T4Monoid<A, B, C, D> monoid(
        Monoid<A> monoidA,
        Monoid<B> monoidB,
        Monoid<C> monoidC,
        Monoid<D> monoidD) {
        return new T4Monoid<A, B, C, D>() {
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

            @Override
            public Monoid<D> getD() {
                return monoidD;
            }
        };
    }

    /**
     * The {@link Group} instance.
     *
     * @param groupA the group of the type of the first element
     * @param groupB the group of the type of the second element
     * @param groupC the group of the type of the third element
     * @param groupD the group of the type of the fourth element
     * @param <A>    the type of the first element
     * @param <B>    the type of the second element
     * @param <C>    the type of the third element
     * @param <D>    the type of the fourth element
     * @return the instance
     */
    public static <A, B, C, D> T4Group<A, B, C, D> group(
        Group<A> groupA,
        Group<B> groupB,
        Group<C> groupC,
        Group<D> groupD) {
        return new T4Group<A, B, C, D>() {
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

            @Override
            public Group<D> getD() {
                return groupD;
            }
        };
    }

    /**
     * The {@link Bifunctor} instance.
     *
     * @param <S> the type of the first element
     * @param <T> the type of the second element
     * @return the instance
     */
    public static <S, T> T4Bifunctor<S, T> bifunctor() {
        return new T4Bifunctor<S, T>() {
        };
    }

    /**
     * The {@link Biapply} instance.
     *
     * @param semigroupS the semigroup of the type of the first element
     * @param semigroupT the semigroup of the type of the second element
     * @param <S>        the type of the first element
     * @param <T>        the type of the second element
     * @return the instance
     */
    public static <S, T> T4Biapply<S, T> biapply(Semigroup<S> semigroupS, Semigroup<T> semigroupT) {
        return new T4Biapply<S, T>() {
            @Override
            public Semigroup<S> getS() {
                return semigroupS;
            }

            @Override
            public Semigroup<T> getT() {
                return semigroupT;
            }
        };
    }

    /**
     * The {@link Biapplicative} instance.
     *
     * @param monoidS the monoid of the type of the first element
     * @param monoidT the monoid of the type of the second element
     * @param <S>     the type of the first element
     * @param <T>     the type of the second element
     * @return the instance
     */

    public static <S, T> T4Biapplicative<S, T> biapplicative(Monoid<S> monoidS, Monoid<T> monoidT) {
        return new T4Biapplicative<S, T>() {
            @Override
            public Monoid<S> getS() {
                return monoidS;
            }

            @Override
            public Monoid<T> getT() {
                return monoidT;
            }
        };
    }

    /**
     * Converts the tuple to a heterogenous list.
     *
     * @return the {@link HList}
     */
    public HCons<A, HCons<B, HCons<C, HCons<D, HNil>>>> toHList() {
        return HList.cons(_1(), HList.cons(_2(), HList.cons(_3(), HList.cons(_4(), HList.nil))));
    }

}