package org.highj.data.collection;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.either.EitherBifunctor;
import org.highj.data.collection.either.EitherMonad;
import org.highj.data.collection.either.EitherMonadPlus;
import org.highj.data.compare.Ordering;
import org.highj.data.functions.Functions;
import org.highj.data.functions.Strings;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass0.group.Monoid;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Either<A, B> implements __<Either.µ, A, B> {

    private static final String SHOW_LEFT = "Left(%s)";
    private static final String SHOW_RIGHT = "Right(%s)";

    /**
     * The witness class of Either
     */
    public static class µ {
    }

    private Either() {
    }

    @SuppressWarnings("unchecked")
    /**
     * Recovers an Either value
     * @param value
     */
    public static <A, B> Either<A, B> narrow(_<__.µ<µ, A>, B> value) {
        return (Either) value;
    }

    /**
     * The catamorphism of Either
     *
     * @param leftFn  function to be applied if it is a Left value
     * @param rightFn function to be applied if it is a Right value
     * @param <C>     the result type
     * @return the result of the function application
     */
    public abstract <C> C either(Function<A, C> leftFn, Function<B, C> rightFn);

    /**
     * The catamorphism for constant functions
     *
     * @param left  result if it is a Left value
     * @param right result if it is a Right value
     * @param <C>   the result type
     * @return the result of the function application
     */
    public <C> C constant(C left, C right) {
        return either(Functions.<A, C>constant(left), Functions.<B, C>constant(right));
    }

    /**
     * Converts the Either
     *
     * @param leftFn  the mapping function for a Left
     * @param rightFn the mapping function for a Right
     * @param <C>     the new Left type
     * @param <D>     the new Right type
     * @return the converted Either
     */
    public <C, D> Either<C, D> bimap(Function<? super A, ? extends C> leftFn, Function<? super B, ? extends D> rightFn) {
        return either(a -> Either.<C, D>newLeft(leftFn.apply(a)),
                b -> Either.<C, D>newRight(rightFn.apply(b)));
    }

    public <C> Either<C, B> leftMap(Function<? super A, ? extends C> leftFn) {
        return bimap(leftFn, Function.<B>identity());
    }

    public <C> Either<A, C> rightMap(Function<? super B, ? extends C> rightFn) {
        return bimap(Function.<A>identity(), rightFn);
    }

    /**
     * Construction of a Left value.
     *
     * @param a   the Left value
     * @param <A> the type of the Left value
     * @param <B> the type of the Right value
     * @return the Left Either
     */
    public static <A, B> Either<A, B> newLeft(final A a) {
        return new Either<A, B>() {

            @Override
            public <C> C either(Function<A, C> leftFn, Function<B, C> rightFn) {
                return leftFn.apply(a);
            }
        };
    }

    /**
     * Construction of a Left value with inference support.
     * <p>
     * In cases where type inference can't infer B, you can write e.g.
     * <code>newLeft("foo", Integer.class)</code>
     * instead of <code>Either.&lt;String, Integer&gt;newLeft("foo")</code>
     *
     * @param a     the Left value
     * @param clazz the class of the Right value
     * @param <A>   the type of the Left value
     * @param <B>   the type of the Right value
     * @return the Left Either
     */
    public static <A, B> Either<A, B> newLeft(final A a, Class<B> clazz) {
        return newLeft(a);
    }

    /**
     * Construction of a lazy Left value.
     *
     * @param thunk the Left thunk
     * @param <A>   the type of the Left value
     * @param <B>   the type of the Right value
     * @return the Left Either
     */
    public static <A, B> Either<A, B> lazyLeft(final Supplier<A> thunk) {
        return new Either<A, B>() {

            @Override
            public <C> C either(Function<A, C> leftFn, Function<B, C> rightFn) {
                return leftFn.apply(thunk.get());
            }
        };
    }

    /**
     * Construction of a Right value.
     *
     * @param b   the Right value
     * @param <A> the type of the Left value
     * @param <B> the type of the Right value
     * @return the Right Either
     */
    public static <A, B> Either<A, B> newRight(final B b) {
        return new Either<A, B>() {

            @Override
            public <C> C either(Function<A, C> leftFn, Function<B, C> rightFn) {
                return rightFn.apply(b);
            }
        };
    }

    /**
     * Construction of a Right value with inference support.
     * <p>
     * In cases where type inference can't infer A, you can write e.g.
     * <code>newRight(Integer.class, "foo")</code>
     * instead of <code>Either.&lt;Integer,String&gt;newRight("foo")</code>
     *
     * @param clazz the class of Left values
     * @param b     the newRight value
     * @param <A>   the type of the Left value
     * @param <B>   the type of the Right value
     * @return the Right Either
     */
    public static <A, B> Either<A, B> newRight(Class<A> clazz, final B b) {
        return newRight(b);
    }

    /**
     * Construction of a lazy Right value.
     *
     * @param thunk the Right thunk
     * @param <A>   the type of the Left value
     * @param <B>   the type of the Right value
     * @return the Right Either
     */
    public static <A, B> Either<A, B> lazyRight(final Supplier<B> thunk) {
        return new Either<A, B>() {

            @Override
            public <C> C either(Function<A, C> leftFn, Function<B, C> rightFn) {
                return rightFn.apply(thunk.get());
            }
        };
    }

    /**
     * Converts an Either&lt;A,B&gt; to an Either &lt;B,A&gt;
     *
     * @return the swapped Either
     */
    public Either<B, A> swap() {
        return either(left -> Either.<B, A>newRight(left), right -> Either.<B, A>newLeft(right));
    }

    /**
     * Test for Left
     *
     * @return true if this is a newLeft
     */
    public boolean isLeft() {
        return constant(true, false);
    }

    /**
     * Test for Right
     *
     * @return true if this is a newRight
     */
    public boolean isRight() {
        return constant(false, true);
    }

    /**
     * Extracts the Left value wrapped in Maybe
     *
     * @return a Maybe containing the Left value, if it exists
     */
    public Maybe<A> maybeLeft() {
        return either(Maybe::<A>Just, Functions.<B, Maybe<A>>constant(Maybe.<A>Nothing()));
    }

    /**
     * Extracts the Right value wrapped in Maybe
     *
     * @return a Maybe containing Right value, if it exists
     */
    public Maybe<B> maybeRight() {
        return either(Functions.<A, Maybe<B>>constant(Maybe.<B>Nothing()), Maybe::<B>Just);
    }

    /**
     * Extracts the Left value, or uses the default value if it doesn't exist
     *
     * @param defaultValue default value
     * @return the newLeft value if it exists, else the default value
     */
    public A leftOrElse(A defaultValue) {
        return either(Function.<A>identity(), Functions.<B, A>constant(defaultValue));
    }

    /**
     * Extracts the Right value, or the default value if ir doesn't exist
     *
     * @param defaultValue default value
     * @return the newRight value if it exists, else the default value
     */
    public B rightOrElse(B defaultValue) {
        return either(Functions.<A, B>constant(defaultValue), Function.<B>identity());
    }

    /**
     * Extracts the Left value
     *
     * @return the Left value, or throws an exception if none exists
     */
    public A getLeft() throws NoSuchElementException {
        return either(Function.<A>identity(), Functions.<B, A>constant(
                Functions.<A>error(NoSuchElementException.class, "getLeft called on a lazyRight")));
    }

    /**
     * Extracts the Right value
     *
     * @return the Right value, or throws an exception if none exists
     */
    public B getRight() throws NoSuchElementException {
        return either(Functions.<A, B>constant(
                Functions.<B>error(NoSuchElementException.class, "getRight called on a lazyLeft")), Function.<B>identity());
    }

    /**
     * Extracts the Left values from a List
     *
     * @param list a List of Eithers
     * @return a List of all Left values
     */
    public static <A> List<A> lefts(List<? extends Either<A, ?>> list) {
        List<A> result = List.nil();
        for (Either<A, ?> either : list) {
            if (either.isLeft()) {
                result = result.plus(either.getLeft());
            }
        }
        return result.reverse();
    }

    /**
     * Extracts the Left values from a List in a lazy fashion
     *
     * @param list a List of Eithers
     * @return a List of all Left values
     */
    public static <A> List<A> lazyLefts(List<? extends Either<A, ?>> list) throws StackOverflowError {
        if (list.isEmpty()) {
            return List.nil();
        } else {
            Either<A, ?> head = list.head();
            return head.<List<A>>either(a -> List.newLazyList(a, () -> Either.<A>lazyLefts(list.tail())),
                    b -> lazyLefts(list.tail()));
        }
    }

    /**
     * Extracts the Right values from a List
     *
     * @param list a List of Eithers
     * @return a List of all Right values
     */
    public static <B> List<B> rights(List<? extends Either<?, B>> list) {
        List<B> result = List.nil();
        for (Either<?, B> either : list) {
            if (either.isRight()) {
                result = result.plus(either.getRight());
            }
        }
        return result.reverse();
    }


    /**
     * Extracts the Right values from a List in a lazy fashion
     *
     * @param list a List of Eithers
     * @return a List of all Right values
     */
    public static <B> List<B> lazyRights(List<? extends Either<?, B>> list) throws StackOverflowError {
        if (list.isEmpty()) {
            return List.nil();
        } else {
            Either<?, B> head = list.head();
            return head.<List<B>>either(a -> lazyRights(list.tail()),
                    b -> List.newLazyList(b, () -> Either.<B>lazyRights(list.tail())));
        }
    }

    /**
     * Extracts the values from a List
     *
     * @param list a List of Eithers
     * @return a Pair of a List of all Left and a list of all Right values
     */
    public static <A, B> T2<List<A>, List<B>> split(List<Either<A, B>> list) {
        List<A> lefts = List.nil();
        List<B> rights = List.nil();
        for (Either<A, B> either : list) {
            if (either.isLeft()) {
                lefts = lefts.plus(either.getLeft());
            } else {
                rights = rights.plus(either.getRight());
            }
        }
        return T2.of(lefts.reverse(), rights.reverse());
    }

    /**
     * Generates a String representation
     *
     * @return a String representation
     */
    @Override
    public String toString() {
        return either(Strings.<A>format(SHOW_LEFT), Strings.<B>format(SHOW_RIGHT));
    }

    /**
     * Calculates a hash code
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return either(x -> x.hashCode() * 31, y -> y.hashCode() * 47);
    }

    /**
     * Test equality
     *
     * @param obj the other object
     * @return true if both obects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Either) {
            Either<?, ?> that = (Either) obj;
            return this.maybeLeft().equals(that.maybeLeft()) &&
                    this.maybeRight().equals(that.maybeRight());
        }
        return false;
    }

    /**
     * Extract the value, if Left and Right have the same type
     *
     * @param either an Either object with same Left and Right type
     * @return the value extracted from Left or Right
     */
    public static <A> A unify(Either<A, A> either) {
        return either.either(Function.<A>identity(), Function.<A>identity());
    }

    /**
     * Generates an Eq instance
     *
     * @param eqA Eq instance for Left type
     * @param eqB Eq instance for Right type
     * @param <A> Left type
     * @param <B> Right type
     * @return Eq instance for Either
     */
    public static <A, B> Eq<Either<A, B>> eq(Eq<A> eqA, Eq<B> eqB) {
        return (one, two) -> {
            if (one == null && two == null) {
                return true;
            } else if (one == null || two == null) {
                return false;
            } else if (one.isLeft() && two.isLeft()) {
                return eqA.eq(one.getLeft(), two.getLeft());
            } else {
                return one.isRight() && two.isRight() && eqB.eq(one.getRight(), two.getRight());
            }
        };
    }

    public static <A, B> Ord<Either<A, B>> ord(Ord<A> ordA, Ord<B> ordB) {
        return (one, two) -> {
            if (one == null && two == null) {
                return Ordering.EQ;
            } else if (one == null) {
                return Ordering.LT;
            } else if (two == null) {
                return Ordering.GT;
            } else if (one.isLeft()) {
                return two.isRight() ? Ordering.LT : ordA.cmp(one.getLeft(), two.getLeft());
            } else {
                return two.isLeft() ? Ordering.GT : ordB.cmp(one.getRight(), two.getRight());
            }
        };
    }

    /**
     * The Monad of Either
     *
     * @param <S> the Left type of Either
     * @return the Either monad
     */
    public static <S> EitherMonad<S> monad() {
        return new EitherMonad<S>() {
        };
    }

    /**
     * The MonadPlus instance of Either which prefers the first Right value.
     *
     * @param monoid the Monoid used to combine Left values
     * @param <S>    the Left type of Either
     * @return the Either MonadPlus instance
     */
    public static <S> EitherMonadPlus<S> firstBiasedMonadPlus(Monoid<S> monoid) {
        return new EitherMonadPlus<>(monoid, EitherMonadPlus.Bias.FIRST_RIGHT);
    }

    /**
     * The MonadPlus instance of Either which prefers the last Right value.
     *
     * @param monoid the Monoid used to combine Left values
     * @param <S>    the Left type of Either
     * @return the Either MonadPlus instance
     */
    public static <S> EitherMonadPlus<S> lastBiasedMonadPlus(Monoid<S> monoid) {
        return new EitherMonadPlus<>(monoid, EitherMonadPlus.Bias.LAST_RIGHT);
    }

    /**
     * The Bifunctor instance of Either.
     */
    public static final EitherBifunctor bifunctor = new EitherBifunctor() {
    };
}
