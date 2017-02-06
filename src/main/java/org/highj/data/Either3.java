package org.highj.data;

import org.derive4j.hkt.__3;
import org.highj.data.eq.Eq;
import org.highj.data.instance.either3.*;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.T3;
import org.highj.function.Functions;
import org.highj.function.Strings;
import org.highj.typeclass1.monad.MonadRec;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The type disjunction for three types.
 *
 * @param <A> the "left" type
 * @param <B> the "middle" type
 * @param <C> the "right" type
 */
public abstract class Either3<A, B, C> implements __3<Either3.µ, A, B, C> {

    private static final String SHOW_LEFT = "Left3(%s)";
    private static final String SHOW_MIDDLE = "Middle3(%s)";
    private static final String SHOW_RIGHT = "Right3(%s)";

    public interface µ {
    }

    private Either3() {
    }

    /**
     * The catamorphism of {@link Either3}
     *
     * @param leftFn   function for Left values
     * @param middleFn function for Middle values
     * @param rightFn  function for Right values
     * @param <D>      the result type
     * @return the result of the function application
     */
    public abstract <D> D either(Function<A, D> leftFn, Function<B, D> middleFn, Function<C, D> rightFn);

    /**
     * The catamorphism using constant functions.
     *
     * @param left   result if it is a Left value
     * @param middle result if it is a Middle value
     * @param right  result if it is a Right value
     * @param <D>    the result type
     * @return the result of the function application
     */
    public <D> D constant(D left, D middle, D right) {
        return either(Functions.constant(left),
                Functions.constant(middle),
                Functions.constant(right));
    }

    /**
     * Converts an {@link Either3}.
     *
     * @param leftFn   the mapping function for a Left
     * @param middleFn the mapping function for a Left
     * @param rightFn  the mapping function for a Right
     * @param <A_>     the new Left type
     * @param <B_>     the new Left type
     * @param <C_>     the new Right type
     * @return the converted {@link Either}
     */
    public <A_, B_, C_> Either3<A_, B_, C_> trimap(
            Function<? super A, ? extends A_> leftFn,
            Function<? super B, ? extends B_> middleFn,
            Function<? super C, ? extends C_> rightFn) {
        return either(a -> Either3.<A_, B_, C_>Left(leftFn.apply(a)),
                b -> Either3.<A_, B_, C_>Middle(middleFn.apply(b)),
                c -> Either3.<A_, B_, C_>Right(rightFn.apply(c)));
    }

    /**
     * Transforms the left part of {@link Either3}.
     *
     * @param leftFn transformation function
     * @param <A_>   new Left type
     * @return the converted {@link Either3}
     */
    public <A_> Either3<A_, B, C> leftMap(Function<? super A, ? extends A_> leftFn) {
        return trimap(leftFn, Function.identity(), Function.identity());
    }

    /**
     * Transforms the middle part of {@link Either3}.
     *
     * @param middleFn transformation function
     * @param <B_>     new Middle type
     * @return the converted {@link Either3}
     */
    public <B_> Either3<A, B_, C> middleMap(Function<? super B, ? extends B_> middleFn) {
        return trimap(Function.identity(), middleFn, Function.identity());
    }

    /**
     * Transforms the right part of {@link Either3}.
     *
     * @param rightFn transformation function
     * @param <C_>    new Right type
     * @return the converted {@link Either3}
     */
    public <C_> Either3<A, B, C_> rightMap(Function<? super C, ? extends C_> rightFn) {
        return trimap(Function.identity(), Function.identity(), rightFn);
    }

    /**
     * Converts the {@link Either3} to a left-nested {@link Either}.
     *
     * @return the nested {@link Either} instance
     */
    public Either<Either<A, B>, C> combineLeft() {
        return either(a -> Either.Left(Either.Left(a)),
                b -> Either.Left(Either.Right(b)),
                c -> Either.Right(c));
    }

    /**
     * Converts the {@link Either3} to a right-nested {@link Either}.
     *
     * @return the nested {@link Either} instance
     */
    public Either<A, Either<B, C>> combineRight() {
        return either(a -> Either.Left(a),
                b -> Either.Right(Either.Left(b)),
                c -> Either.Right(Either.Right(c)));
    }

    /**
     * Constructs a Left instance.
     *
     * @param a   the Left value
     * @param <A> the Left type
     * @param <B> the Middle type
     * @param <C> the Right type
     * @return the Left instance
     */
    public static <A, B, C> Either3<A, B, C> Left(A a) {
        return new Either3<A, B, C>() {
            @Override
            public <D> D either(Function<A, D> leftFn, Function<B, D> middleFn, Function<C, D> rightFn) {
                return leftFn.apply(a);
            }
        };
    }

    /**
     * Constructs a lazy Left instance.
     *
     * @param supplier the {@link Supplier} for the Left value
     * @param <A>      the Left type
     * @param <B>      the Middle type
     * @param <C>      the Right type
     * @return the Left instance
     */
    public static <A, B, C> Either3<A, B, C> Left$(Supplier<A> supplier) {
        return new Either3<A, B, C>() {
            @Override
            public <D> D either(Function<A, D> leftFn, Function<B, D> middleFn, Function<C, D> rightFn) {
                return leftFn.apply(supplier.get());
            }
        };
    }

    /**
     * Constructs a Middle instance.
     *
     * @param b   the Middle value
     * @param <A> the Left type
     * @param <B> the Middle type
     * @param <C> the Right type
     * @return the Middle instance
     */
    public static <A, B, C> Either3<A, B, C> Middle(B b) {
        return new Either3<A, B, C>() {
            @Override
            public <D> D either(Function<A, D> leftFn, Function<B, D> middleFn, Function<C, D> rightFn) {
                return middleFn.apply(b);
            }
        };
    }

    /**
     * Constructs a lazy Middle instance.
     *
     * @param supplier the {@link Supplier} for the Middle value
     * @param <A>      the Left type
     * @param <B>      the Middle type
     * @param <C>      the Right type
     * @return the Middle instance
     */
    public static <A, B, C> Either3<A, B, C> Middle$(Supplier<B> supplier) {
        return new Either3<A, B, C>() {
            @Override
            public <D> D either(Function<A, D> leftFn, Function<B, D> middleFn, Function<C, D> rightFn) {
                return middleFn.apply(supplier.get());
            }
        };
    }

    /**
     * Constructs a Right instance.
     *
     * @param c   the Right value
     * @param <A> the Left type
     * @param <B> the Middle type
     * @param <C> the Right type
     * @return the Right instance
     */
    public static <A, B, C> Either3<A, B, C> Right(C c) {
        return new Either3<A, B, C>() {
            @Override
            public <D> D either(Function<A, D> leftFn, Function<B, D> middleFn, Function<C, D> rightFn) {
                return rightFn.apply(c);
            }
        };
    }

    /**
     * Constructs a lazy Right instance.
     *
     * @param supplier the {@link Supplier} for the Right value
     * @param <A>      the Left type
     * @param <B>      the Middle type
     * @param <C>      the Right type
     * @return the Right instance
     */
    public static <A, B, C> Either3<A, B, C> Right$(Supplier<C> supplier) {
        return new Either3<A, B, C>() {
            @Override
            public <D> D either(Function<A, D> leftFn, Function<B, D> middleFn, Function<C, D> rightFn) {
                return rightFn.apply(supplier.get());
            }
        };
    }

    /**
     * Test if this is a Left instance.
     *
     * @return true for Left instance
     */
    public boolean isLeft() {
        return constant(true, false, false);
    }

    /**
     * Test if this is a Middle instance.
     *
     * @return true for Middle instance
     */
    public boolean isMiddle() {
        return constant(false, true, false);
    }

    /**
     * Test if this is a Right instance.
     *
     * @return true for Right instance
     */
    public boolean isRight() {
        return constant(false, false, true);
    }

    /**
     * Extracts the Left value as {@link Maybe}.
     *
     * @return a {@link Maybe} containing the Left value, if it exists
     */
    public Maybe<A> maybeLeft() {
        return either(Maybe::Just,
                Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()));
    }

    /**
     * Extracts the Middle value as {@link Maybe}.
     *
     * @return a {@link Maybe} containing the Middle value, if it exists
     */
    public Maybe<B> maybeMiddle() {
        return either(Functions.constant(Maybe.Nothing()),
                Maybe::Just,
                Functions.constant(Maybe.Nothing()));
    }

    /**
     * Extracts the right value as {@link Maybe}.
     *
     * @return a {@link Maybe} containing the Right value, if it exists
     */
    public Maybe<C> maybeRight() {
        return either(Functions.constant(Maybe.Nothing()),
                Functions.constant(Maybe.Nothing()),
                Maybe::Just);
    }

    /**
     * Extracts the Left value, or uses the default value if it doesn't exist.
     *
     * @param defaultValue default value
     * @return the Left value if it exists, else the default value
     */
    public A leftOrElse(A defaultValue) {
        return maybeLeft().getOrElse(defaultValue);
    }

    /**
     * Extracts the Middle value, or uses the default value if it doesn't exist.
     *
     * @param defaultValue default value
     * @return the Middle value if it exists, else the default value
     */
    public B middleOrElse(B defaultValue) {
        return maybeMiddle().getOrElse(defaultValue);
    }

    /**
     * Extracts the Right value, or uses the default value if it doesn't exist.
     *
     * @param defaultValue default value
     * @return the Right value if it exists, else the default value
     */
    public C rightOrElse(C defaultValue) {
        return maybeRight().getOrElse(defaultValue);
    }

    /**
     * Extracts the Left value.
     *
     * @return the Left value
     * @throws NoSuchElementException when not called on a Left
     */
    public A getLeft() throws NoSuchElementException {
        return maybeLeft().getOrException(NoSuchElementException.class);
    }

    /**
     * Extracts the Middle value.
     *
     * @return the Middle value
     * @throws NoSuchElementException when not called on a Middle
     */
    public B getMiddle() throws NoSuchElementException {
        return maybeMiddle().getOrException(NoSuchElementException.class);
    }

    /**
     * Extracts the Right value.
     *
     * @return the Right value
     * @throws NoSuchElementException when not called on a Right
     */
    public C getRight() throws NoSuchElementException {
        return maybeRight().getOrException(NoSuchElementException.class);
    }

    @Override
    public String toString() {
        return either(
                Strings.format(SHOW_LEFT),
                Strings.format(SHOW_MIDDLE),
                Strings.format(SHOW_RIGHT));
    }

    @Override
    public int hashCode() {
        return 17 + either(a -> a.hashCode() * 31,
                b -> b.hashCode() * 47,
                c -> c.hashCode() * 53);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Either3) {
            Either3<?, ?, ?> that = (Either3) obj;
            return this.maybeLeft().equals(that.maybeLeft()) &&
                    this.maybeMiddle().equals(that.maybeMiddle()) &&
                    this.maybeRight().equals(that.maybeRight());
        }
        return false;
    }

    /**
     * Starts an evaluation based on the current {@link Either3} using a fluent interface.
     * <p>
     * Example usage:
     * <pre>
     * {@code
     * String result = someEither3
     *   .caseLeft(a -> "Left is " + a)
     *   .caseMiddle(b -> "Middle is " + b)
     *   .caseRight(c -> "Right is " + c);
     * }
     * </pre>
     *
     * @param leftFn function to be used if this is a Left value
     * @param <R>    the result type
     * @return intermediate result for fluent interface
     */
    public <R> CaseLeft<R> caseLeft(Function<A, R> leftFn) {
        return new CaseLeft<>(leftFn);
    }

    /**
     * Intermediate result of an evaluation, see {@link Either3#caseLeft} for usage.
     *
     * @param <R> the result type
     */
    public class CaseLeft<R> {
        private final Maybe<R> maybe;

        private CaseLeft(Function<A, R> leftFn) {
            this.maybe = maybeLeft().map(leftFn);
        }

        public CaseMiddle<R> caseMiddle(Function<B, R> middleFn) {
            return new CaseMiddle<>(maybe, middleFn);
        }
    }

    /**
     * Intermediate result of an evaluation, see {@link Either3#caseLeft} for usage.
     *
     * @param <R> the result type
     */
    public class CaseMiddle<R> {
        private final Maybe<R> maybe;

        private CaseMiddle(Maybe<R> maybe, Function<B, R> middleFn) {
            this.maybe = maybe.orElse(maybeMiddle().map(middleFn));
        }

        public R caseRight(Function<C, R> rightFn) {
            return maybe.getOrElse(() -> rightFn.apply(getRight()));
        }
    }

    /**
     * The {@link Eq} instance of {@link Either3}.
     *
     * @param eqA the {@link Eq} instance of the left type
     * @param eqB the {@link Eq} instance of the middle type
     * @param eqC the {@link Eq} instance of the right type
     * @param <A> the left type
     * @param <B> the middle type
     * @param <C> the right type
     * @return the instance
     */
    public static <A, B, C> Either3Eq<A, B, C> eq(Eq<A> eqA, Eq<B> eqB, Eq<C> eqC) {
        return () -> T3.of(eqA, eqB, eqC);
    }

    /**
     * The {@link Ord} instance of {@link Either3}.
     *
     * @param ordA the {@link Ord} instance of the left type
     * @param ordB the {@link Ord} instance of the middle type
     * @param ordC the {@link Ord} instance of the right type
     * @param <A>  the left type
     * @param <B>  the middle type
     * @param <C>  the right type
     * @return the instance
     */
    public static <A, B, C> Either3Ord<A, B, C> ord(Ord<A> ordA, Ord<B> ordB, Ord<C> ordC) {
        return () -> T3.of(ordA, ordB, ordC);
    }

    /**
     * The functor instance of  {@link Either3}.
     * @param <S> the fixed left type
     * @param <T> the fixed middle type
     * @return the instance
     */
    public static <S, T> Either3Functor<S, T> functor() {
        return new Either3Functor<S, T>() {
        };
    }

    /**
     * The applicative instance of  {@link Either3}.
     * @param <S> the fixed left type
     * @param <T> the fixed middle type
     * @return the instance
     */
    public static <S, T> Either3Applicative<S, T> applicative() {
        return new Either3Applicative<S, T>() {
        };
    }

    /**
     * The monad instance of {@link Either3}, also implementing {@link MonadRec}.
     * @param <S> the fixed left type
     * @param <T> the fixed middle type
     * @return the instance
     */
    public static <S, T> Either3Monad<S, T> monad() {
        return new Either3Monad<S, T>() {
        };
    }

    /**
     * The bifunctor instance of  {@link Either3}.
     * @param <S> the fixed left type
     * @return  the instance
     */
    public static <S> Either3Bifunctor<S> bifunctor() {
        return new Either3Bifunctor<S>() {
        };
    }
}
