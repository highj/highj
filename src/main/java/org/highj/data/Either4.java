package org.highj.data;

import org.derive4j.hkt.__4;
import org.highj.data.eq.Eq;
import org.highj.data.instance.either4.Either4Applicative;
import org.highj.data.instance.either4.Either4Bifunctor;
import org.highj.data.instance.either4.Either4Eq;
import org.highj.data.instance.either4.Either4Functor;
import org.highj.data.instance.either4.Either4Monad;
import org.highj.data.instance.either4.Either4Ord;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.T4;
import org.highj.function.Functions;
import org.highj.function.Strings;
import org.highj.typeclass1.monad.MonadRec;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The type disjunction for four types.
 *
 * @param <A> the "E1" type
 * @param <B> the "E2" type
 * @param <C> the "E3" type
 * @param <D> the "E4" type
 */
public abstract class Either4<A, B, C, D> implements __4<Either4.µ, A, B, C, D> {

    private static final String SHOW_E1 = "E1(%s)";
    private static final String SHOW_E2 = "E2(%s)";
    private static final String SHOW_E3 = "E3(%s)";
    private static final String SHOW_E4 = "E4(%s)";

    public interface µ {
    }

    private Either4() {
    }

    /**
     * The catamorphism of {@link Either4}
     *
     * @param fn1 function for E1 values
     * @param fn2 function for E2 values
     * @param fn3 function for E3 values
     * @param fn4 function for E4 values
     * @param <E> the result type
     * @return the result of the function application
     */
    public abstract <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4);

    /**
     * The catamorphism using constant functions.
     *
     * @param r1  result if it is a E1 value
     * @param r2  result if it is a E2 value
     * @param r3  result if it is a E3 value
     * @param r4  result if it is a E4 value
     * @param <E> the result type
     * @return the result of the function application
     */
    public <E> E constant(E r1, E r2, E r3, E r4) {
        return either(Functions.constant(r1),
                Functions.constant(r2),
                Functions.constant(r3),
                Functions.constant(r4));
    }

    /**
     * Converts an {@link Either4}.
     *
     * @param fn1  the mapping function for an E1
     * @param fn2  the mapping function for an E2
     * @param fn3  the mapping function for an E3
     * @param fn4  the mapping function for an E4
     * @param <A_> the new Left type
     * @param <B_> the new Left type
     * @param <C_> the new Right type
     * @param <D_> the new Right type
     * @return the converted {@link Either}
     */
    public <A_, B_, C_, D_> Either4<A_, B_, C_, D_> quadMap(
            Function<? super A, ? extends A_> fn1,
            Function<? super B, ? extends B_> fn2,
            Function<? super C, ? extends C_> fn3,
            Function<? super D, ? extends D_> fn4) {
        return either(a -> Either4.<A_, B_, C_, D_> E1(fn1.apply(a)),
                b -> Either4.<A_, B_, C_, D_> E2(fn2.apply(b)),
                c -> Either4.<A_, B_, C_, D_> E3(fn3.apply(c)),
                d -> Either4.<A_, B_, C_, D_> E4(fn4.apply(d)));
    }

    /**
     * Transforms the E1 of {@link Either4}.
     *
     * @param fn1  transformation function
     * @param <A_> new E1 type
     * @return the converted {@link Either4}
     */
    public <A_> Either4<A_, B, C, D> map1(Function<? super A, ? extends A_> fn1) {
        return quadMap(fn1, Function.identity(), Function.identity(), Function.identity());
    }

    /**
     * Transforms the E2 of {@link Either4}.
     *
     * @param fn2  transformation function
     * @param <B_> new E2 type
     * @return the converted {@link Either4}
     */
    public <B_> Either4<A, B_, C, D> map2(Function<? super B, ? extends B_> fn2) {
        return quadMap(Function.identity(), fn2, Function.identity(), Function.identity());
    }

    /**
     * Transforms the E3 of {@link Either4}.
     *
     * @param fn3  transformation function
     * @param <C_> new E3 type
     * @return the converted {@link Either4}
     */
    public <C_> Either4<A, B, C_, D> map3(Function<? super C, ? extends C_> fn3) {
        return quadMap(Function.identity(), Function.identity(), fn3, Function.identity());
    }

    /**
     * Transforms the E4 of {@link Either4}.
     *
     * @param fn4  transformation function
     * @param <D_> new E4 type
     * @return the converted {@link Either4}
     */
    public <D_> Either4<A, B, C, D_> map4(Function<? super D, ? extends D_> fn4) {
        return quadMap(Function.identity(), Function.identity(), Function.identity(), fn4);
    }

    /**
     * Converts the {@link Either4} to a left-nested {@link Either3}.
     *
     * @return the nested {@link Either3} instance
     */
    public Either3<Either<A, B>, C, D> combineLeft() {
        return either(a -> Either3.Left(Either.Left(a)),
                b -> Either3.Left(Either.Right(b)),
                c -> Either3.Middle(c),
                d -> Either3.Right(d));
    }

    /**
     * Converts the {@link Either4} to a middle-nested {@link Either3}.
     *
     * @return the nested {@link Either3} instance
     */
    public Either3<A, Either<B, C>, D> combineMiddle() {
        return either(a -> Either3.Left(a),
                b -> Either3.Middle(Either.Left(b)),
                c -> Either3.Middle(Either.Right(c)),
                d -> Either3.Right(d));
    }

    /**
     * Converts the {@link Either4} to a right-nested {@link Either3}.
     *
     * @return the nested {@link Either3} instance
     */
    public Either3<A, B, Either<C, D>> combineRight() {
        return either(a -> Either3.Left(a),
                b -> Either3.Middle(b),
                c -> Either3.Right(Either.Left(c)),
                d -> Either3.Right(Either.Right(d)));
    }

    /**
     * Constructs an E1 instance.
     *
     * @param a   the E1 value
     * @param <A> the E1 type
     * @param <B> the E2 type
     * @param <C> the E3 type
     * @param <D> the E4 type
     * @return the E1 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E1(A a) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn1.apply(a);
            }
        };
    }

    /**
     * Constructs a lazy E1 instance.
     *
     * @param supplier the {@link Supplier} for the E1 value
     * @param <A>      the E1 type
     * @param <B>      the E2 type
     * @param <C>      the E3 type
     * @param <D>      the E4 type
     * @return the E1 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E1$(Supplier<A> supplier) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn1.apply(supplier.get());
            }
        };
    }

    /**
     * Constructs an E2 instance.
     *
     * @param b   the E2 value
     * @param <A> the E1 type
     * @param <B> the E2 type
     * @param <C> the E3 type
     * @param <D> the E4 type
     * @return the E2 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E2(B b) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn2.apply(b);
            }
        };
    }

    /**
     * Constructs a lazy E2 instance.
     *
     * @param supplier the {@link Supplier} for the E2 value
     * @param <A>      the E1 type
     * @param <B>      the E2 type
     * @param <C>      the E3 type
     * @param <D>      the E4 type
     * @return the E2 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E2$(Supplier<B> supplier) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn2.apply(supplier.get());
            }
        };
    }

    /**
     * Constructs an E3 instance.
     *
     * @param c   the E3 value
     * @param <A> the E1 type
     * @param <B> the E2 type
     * @param <C> the E3 type
     * @param <D> the E4 type
     * @return the E3 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E3(C c) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn3.apply(c);
            }
        };
    }

    /**
     * Constructs a lazy E3 instance.
     *
     * @param supplier the {@link Supplier} for the E3 value
     * @param <A>      the E1 type
     * @param <B>      the E2 type
     * @param <C>      the E3 type
     * @param <D>      the E4 type
     * @return the E3 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E3$(Supplier<C> supplier) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn3.apply(supplier.get());
            }
        };
    }

    /**
     * Constructs an E4 instance.
     *
     * @param d   the E4 value
     * @param <A> the E1 type
     * @param <B> the E2 type
     * @param <C> the E3 type
     * @param <D> the E4 type
     * @return the E4 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E4(D d) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn4.apply(d);
            }
        };
    }

    /**
     * Constructs a lazy E4instance.
     *
     * @param supplier the {@link Supplier} for the E4 value
     * @param <A>      the E1 type
     * @param <B>      the E2 type
     * @param <C>      the E3 type
     * @param <D>      the E4 type
     * @return the E4 instance
     */
    public static <A, B, C, D> Either4<A, B, C, D> E4$(Supplier<D> supplier) {
        return new Either4<A, B, C, D>() {
            @Override
            public <E> E either(Function<A, E> fn1, Function<B, E> fn2, Function<C, E> fn3, Function<D, E> fn4) {
                return fn4.apply(supplier.get());
            }
        };
    }

    /**
     * Returns the index of the current instance, <code>E1.index() == 1</code>,
     * <code>E2.index() == 2</code> etc.
     *
     * @return the index
     */
    public int index() {
        return constant(1, 2, 3, 4);
    }

    /**
     * Test if this is an E1 instance.
     *
     * @return true for E1 instance
     */
    public boolean isE1() {
        return index() == 1;
    }

    /**
     * Test if this is an E1 instance.
     *
     * @return true for E1 instance
     */
    public boolean isE2() {
        return index() == 2;
    }

    /**
     * Test if this is an E1 instance.
     *
     * @return true for E1 instance
     */
    public boolean isE3() {
        return index() == 3;
    }

    /**
     * Test if this is an E1 instance.
     *
     * @return true for E1 instance
     */
    public boolean isE4() {
        return index() == 4;
    }

    /**
     * Extracts the E1 value as {@link Maybe}.
     *
     * @return a {@link Maybe} containing the E1 value, if it exists
     */
    public Maybe<A> maybeE1() {
        return either(Maybe::Just,
                Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()));
    }

    /**
     * Extracts the E2 value as {@link Maybe}.
     *
     * @return a {@link Maybe} containing the E2 value, if it exists
     */
    public Maybe<B> maybeE2() {
        return either(Functions.constant(Maybe.Nothing()),
                Maybe::Just,
                Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()));
    }

    /**
     * Extracts the E3 value as {@link Maybe}.
     *
     * @return a {@link Maybe} containing the E3 value, if it exists
     */
    public Maybe<C> maybeE3() {
        return either(Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()),
                Maybe::Just,
                Functions.constant(Maybe.Nothing()));
    }

    /**
     * Extracts the E4 value as {@link Maybe}.
     *
     * @return a {@link Maybe} containing the E4 value, if it exists
     */
    public Maybe<D> maybeE4() {
        return either(Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()),
                Maybe::Just);
    }

    /**
     * Extracts the E1 value, or uses the default value if it doesn't exist.
     *
     * @param defaultValue default value
     * @return the E1 value if it exists, else the default value
     */
    public A e1OrElse(A defaultValue) {
        return maybeE1().getOrElse(defaultValue);
    }

    /**
     * Extracts the E2 value, or uses the default value if it doesn't exist.
     *
     * @param defaultValue default value
     * @return the E2 value if it exists, else the default value
     */
    public B e2OrElse(B defaultValue) {
        return maybeE2().getOrElse(defaultValue);
    }

    /**
     * Extracts the E3 value, or uses the default value if it doesn't exist.
     *
     * @param defaultValue default value
     * @return the E3 value if it exists, else the default value
     */
    public C e3OrElse(C defaultValue) {
        return maybeE3().getOrElse(defaultValue);
    }

    /**
     * Extracts the E4 value, or uses the default value if it doesn't exist.
     *
     * @param defaultValue default value
     * @return the E4 value if it exists, else the default value
     */
    public D e4OrElse(D defaultValue) {
        return maybeE4().getOrElse(defaultValue);
    }

    /**
     * Extracts the E1 value.
     *
     * @return the E1 value
     * @throws NoSuchElementException when not called on a Left
     */
    public A getE1() throws NoSuchElementException {
        return maybeE1().getOrException(NoSuchElementException.class);
    }

    /**
     * Extracts the E2 value.
     *
     * @return the E2 value
     * @throws NoSuchElementException when not called on a Left
     */
    public B getE2() throws NoSuchElementException {
        return maybeE2().getOrException(NoSuchElementException.class);
    }

    /**
     * Extracts the E3 value.
     *
     * @return the E3 value
     * @throws NoSuchElementException when not called on a Left
     */
    public C getE3() throws NoSuchElementException {
        return maybeE3().getOrException(NoSuchElementException.class);
    }

    /**
     * Extracts the E4 value.
     *
     * @return the E4 value
     * @throws NoSuchElementException when not called on a Left
     */
    public D getE4() throws NoSuchElementException {
        return maybeE4().getOrException(NoSuchElementException.class);
    }

    @Override
    public String toString() {
        return either(
                Strings.format(SHOW_E1),
                Strings.format(SHOW_E2),
                Strings.format(SHOW_E3),
                Strings.format(SHOW_E4));
    }

    @Override
    public int hashCode() {
        return 17 + either(a -> a.hashCode() * 31,
                b -> b.hashCode() * 47,
                c -> c.hashCode() * 53,
                d -> d.hashCode() * 61);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Either4) {
            Either4<?, ?, ?, ?> that = (Either4) obj;
            return this.maybeE1().equals(that.maybeE1()) &&
                    this.maybeE2().equals(that.maybeE2()) &&
                    this.maybeE3().equals(that.maybeE3()) &&
                    this.maybeE4().equals(that.maybeE4());
        }
        return false;
    }

    /**
     * Starts an evaluation based on the current {@link Either4} using a fluent interface.
     * <p>
     * Example usage:
     * <pre>
     * {@code
     * String result = someEither4
     *   .caseE1(a -> "E1 is " + a)
     *   .caseE2(b -> "E2 is " + b)
     *   .caseE3(c -> "E3 is " + c)
     *   .caseE4(d -> "E4 is " + d);
     * }
     * </pre>
     *
     * @param leftFn function to be used if this is a Left value
     * @param <R>    the result type
     * @return intermediate result for fluent interface
     */
    public <R> CaseE1<R> caseE1(Function<A, R> leftFn) {
        return new CaseE1<>(leftFn);
    }

    /**
     * Intermediate result of an evaluation, see {@link Either4#caseE1} for usage.
     *
     * @param <R> the result type
     */
    public class CaseE1<R> {
        private final Maybe<R> maybe;

        private CaseE1(Function<A, R> fn1) {
            this.maybe = maybeE1().map(fn1);
        }

        public CaseE2<R> caseE2(Function<B, R> fn2) {
            return new CaseE2<>(maybe, fn2);
        }
    }

    /**
     * Intermediate result of an evaluation, see {@link Either4#caseE1} for usage.
     *
     * @param <R> the result type
     */
    public class CaseE2<R> {
        private final Maybe<R> maybe;

        private CaseE2(Maybe<R> maybe, Function<B, R> fn2) {
            this.maybe = maybe.orElse(maybeE2().map(fn2));
        }

        public CaseE3<R> caseE3(Function<C, R> fn3) {
            return new CaseE3<>(maybe, fn3);
        }
    }

    /**
     * Intermediate result of an evaluation, see {@link Either4#caseE1} for usage.
     *
     * @param <R> the result type
     */
    public class CaseE3<R> {
        private final Maybe<R> maybe;

        private CaseE3(Maybe<R> maybe, Function<C, R> fn3) {
            this.maybe = maybe.orElse(maybeE3().map(fn3));
        }

        public R caseE4(Function<D, R> fn4) {
            return maybe.getOrElse(() -> fn4.apply(getE4()));
        }
    }

    /**
     * The {@link Eq} instance of {@link Either4}.
     *
     * @param eqA the {@link Eq} instance of the E1 type
     * @param eqB the {@link Eq} instance of the E2 type
     * @param eqC the {@link Eq} instance of the E3 type
     * @param eqD the {@link Eq} instance of the E4 type
     * @param <A> the E1 type
     * @param <B> the E2 type
     * @param <C> the E3 type
     * @param <D> the E4 type
     * @return the instance
     */
    public static <A, B, C, D> Either4Eq<A, B, C, D> eq(Eq<A> eqA, Eq<B> eqB, Eq<C> eqC, Eq<D> eqD) {
        return () -> T4.of(eqA, eqB, eqC, eqD);
    }

    /**
     * The {@link Ord} instance of {@link Either4}.
     *
     * @param ordA the {@link Ord} instance of the E1 type
     * @param ordB the {@link Ord} instance of the E2 type
     * @param ordC the {@link Ord} instance of the E3 type
     * @param ordD the {@link Ord} instance of the E4 type
     * @param <A>  the E1 type
     * @param <B>  the E2 type
     * @param <C>  the E3 type
     * @param <D>  the E3 type
     * @return the instance
     */
    public static <A, B, C, D> Either4Ord<A, B, C, D> ord(Ord<A> ordA, Ord<B> ordB, Ord<C> ordC, Ord<D> ordD) {
        return () -> T4.of(ordA, ordB, ordC, ordD);
    }

    /**
     * The functor instance of  {@link Either4}.
     *
     * @param <S> the fixed E1 type
     * @param <T> the fixed E2 type
     * @param <U> the fixed E3 type
     * @return the instance
     */
    public static <S, T, U> Either4Functor<S, T, U> functor() {
        return new Either4Functor<S, T, U>() {
        };
    }

    /**
     * The applicative instance of  {@link Either4}.
     *
     * @param <S> the fixed E1 type
     * @param <T> the fixed E2 type
     * @param <U> the fixed E3 type
     * @return the instance
     */
    public static <S, T, U> Either4Applicative<S, T, U> applicative() {
        return new Either4Applicative<S, T, U>() {
        };
    }

    /**
     * The monad instance of {@link Either4}, also implementing {@link MonadRec}.
     *
     * @param <S> the fixed E1 type
     * @param <T> the fixed E2 type
     * @param <U> the fixed E3 type
     * @return the instance
     */
    public static <S, T, U> Either4Monad<S, T, U> monad() {
        return new Either4Monad<S, T, U>() {
        };
    }

    /**
     * The bifunctor instance of  {@link Either4}.
     *
     * @param <S> the fixed E1 type
     * @param <T> the fixed E2 type
     * @return the instance
     */
    public static <S, T> Either4Bifunctor<S, T> bifunctor() {
        return new Either4Bifunctor<S, T>() {
        };
    }
}
