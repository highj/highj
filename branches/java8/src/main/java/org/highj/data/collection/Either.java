package org.highj.data.collection;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.either.EitherMonad;
import org.highj.data.collection.either.EitherMonadPlus;
import org.highj.data.compare.Eq;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.data.functions.Functions;
import org.highj.data.functions.Strings;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.alternative.Alt;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Either<A, B> implements __<Either.µ, A, B> {

    private static final String SHOW_LEFT = "LeftLazy(%s)";
    private static final String SHOW_RIGHT = "RightLazy(%s)";

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
     * @param leftFn  function to be applied if it is a LeftLazy value
     * @param rightFn function to be applied if it is a RightLazy value
     * @param <C>     the result type
     * @return the result of the function application
     */
    public abstract <C> C either(Function<A, C> leftFn, Function<B, C> rightFn);

    /**
     * The catamorphism for constant functions
     *
     * @param left  result if it is a LeftLazy value
     * @param right result if it is a RightLazy value
     * @param <C>   the result type
     * @return the result of the function application
     */
    public <C> C constant(C left, C right) {
        return either(Functions.<A, C>constant(left), Functions.<B, C>constant(right));
    }

    /**
     * Converts the Either
     *
     * @param leftFn  the mapping function for a LeftLazy
     * @param rightFn the mapping function for a RightLazy
     * @param <C>     the new LeftLazy type
     * @param <D>     the new RightLazy type
     * @return the converted Either
     */
    public <C, D> Either<C, D> bimap(Function<? super A, ? extends C> leftFn, Function<? super B, ? extends D> rightFn) {
        return either(a -> Either.<C, D>Left(leftFn.apply(a)),
                b -> Either.<C, D>Right(rightFn.apply(b)));
    }

    public <C> Either<C, B> leftMap(Function<? super A, ? extends C> leftFn) {
        return bimap(leftFn, Functions.<B>id());
    }

    public <C> Either<A, C> rightMap(Function<? super B, ? extends C> rightFn) {
        return bimap(Functions.<A>id(), rightFn);
    }

    /**
     * Construction of a left value
     *
     * @param a   the Left value
     * @param <A> the type of the Left value
     * @param <B> the type of the Right value
     * @return the Left Either
     */
    public static <A, B> Either<A, B> Left(final A a) {
        return new Either<A, B>() {

            @Override
            public <C> C either(Function<A, C> leftFn, Function<B, C> rightFn) {
                return leftFn.apply(a);
            }
        };
    }

    /**
     * Construction of a lazy LeftLazy value
     *
     * @param thunk the Left thunk
     * @param <A>   the type of the Left value
     * @param <B>   the type of the Right value
     * @return the Left Either
     */
    public static <A, B> Either<A, B> LeftLazy(final Supplier<A> thunk) {
        return new Either<A, B>() {

            @Override
            public <C> C either(Function<A, C> leftFn, Function<B, C> rightFn) {
                return leftFn.apply(thunk.get());
            }
        };
    }

    /**
     * Construction of a Right value
     *
     * @param b   the Right value
     * @param <A> the type of the Left value
     * @param <B> the type of the Right value
     * @return the Right Either
     */
    public static <A, B> Either<A, B> Right(final B b) {
        return new Either<A, B>() {

            @Override
            public <C> C either(Function<A, C> leftFn, Function<B, C> rightFn) {
                return rightFn.apply(b);
            }
        };
    }

    /**
     * Construction of a lazy Right value
     *
     * @param thunk the Right thunk
     * @param <A>   the type of the Left value
     * @param <B>   the type of the Right value
     * @return the Right Either
     */
    public static <A, B> Either<A, B> RightLazy(final Supplier<B> thunk) {
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
        return either(Either::<B, A>Right, Either::<B, A>Left);
    }

    /**
     * Test for Left
     *
     * @return true if this is a Left
     */
    public boolean isLeft() {
        return constant(true, false);
    }

    /**
     * Test for Right
     *
     * @return true if this is a Right
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
     * @return the Left value if it exists, else the default value
     */
    public A leftOrElse(A defaultValue) {
        return either(Functions.<A>id(), Functions.<B, A>constant(defaultValue));
    }

    /**
     * Extracts the Right value, or the default value if ir doesn't exist
     *
     * @param defaultValue default value
     * @return the Right value if it exists, else the default value
     */
    public B rightOrElse(B defaultValue) {
        return either(Functions.<A, B>constant(defaultValue), Functions.<B>id());
    }

    /**
     * Extracts the Left value
     *
     * @return the Left value, or throws an exception if none exists
     */
    public A getLeft() throws NoSuchElementException {
        return either(Functions.<A>id(), Functions.<B, A>constant(
                Functions.<A>error(NoSuchElementException.class, "getLeft called on a RightLazy")));
    }

    /**
     * Extracts the Right value
     *
     * @return the Right value, or throws an exception if none exists
     */
    public B getRight() throws NoSuchElementException {
        return either(Functions.<A, B>constant(
                Functions.<B>error(NoSuchElementException.class, "getRight called on a LeftLazy")), Functions.<B>id());
    }

    /**
     * Extracts the Left values from a List
     *
     * @param list a List of Eithers
     * @return a List of all Left values
     */
    public static <A> List<A> lefts(List<Either<A, ?>> list) {
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
    public static <A> List<A> leftsLazy(List<Either<A, ?>> list) throws StackOverflowError {
        if (list.isEmpty()) {
            return List.nil();
        } else {
            Either<A, ?> head = list.head();
            return head.either(a -> List.consLazy(a, () -> leftsLazy(list.tail())), b -> leftsLazy(list.tail()));
        }
    }

    /**
     * Extracts the Right values from a List
     *
     * @param list a List of Eithers
     * @return a List of all Right values
     */
    public static <B> List<B> rights(List<Either<?, B>> list) {
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
    public static <B> List<B> rightsLazy(List<Either<?, B>> list) throws StackOverflowError {
        if (list.isEmpty()) {
            return List.nil();
        } else {
            Either<?, B> head = list.head();
            return head.either(a -> rightsLazy(list.tail()), b -> List.consLazy(b, () -> rightsLazy(list.tail())));
        }
    }

    /**
     * Extracts the values from a List
     *
     * @param list a List of Eithers
     * @return a Pair of a List of all Left and a list of all Right values
     */   public static <A,B> T2<List<A>,List<B>> split(List<Either<A,B>> list) {
        List<A> lefts = List.nil();
        List<B> rights = List.nil();
        for (Either<A, B> either : list) {
           if (either.isLeft()) {
               lefts = lefts.plus(either.getLeft());
           } else {
               rights = rights.plus(either.getRight());
           }
        }
        return Tuple.of(lefts.reverse(), rights.reverse());
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

    public static <A> A unify(Either<A,A> either) {
        return either.either(Functions.<A>id(), Functions.<A>id());
    }

    /**
     * Generates an Eq instance
     *
     * @param eqA Eq instance for LeftLazy type
     * @param eqB Eq instance for RightLazy type
     * @param <A> LeftLazy type
     * @param <B> RightLazy type
     * @return Eq instance for Either
     */
    public static <A, B> Eq<Either<A, B>> eq(final Eq<A> eqA, final Eq<B> eqB) {
        return (one, two) -> {
            if (one.isLeft() && two.isLeft()) {
                return eqA.eq(one.getLeft(), two.getLeft());
            }
            return one.isRight() && two.isRight() && eqB.eq(one.getRight(), two.getRight());
        };
    }

    /**
     * The monadTrans of Either
     *
     * @param <S> the Left type of Either
     * @return the Either monad
     */
    private static <S> Monad<__.µ<µ, S>> monad() {
        return new EitherMonad<>();
    }

    public static <S> MonadPlus<__.µ<µ, S>> firstBiasedMonadPlus(Monoid<S> monoid) {
        return new EitherMonadPlus<>(monoid, EitherMonadPlus.Bias.FIRST_RIGHT);
    }

    public static <S> MonadPlus<__.µ<µ, S>> lastBiasedMonadPlus(Monoid<S> monoid) {
        return new EitherMonadPlus<>(monoid, EitherMonadPlus.Bias.LAST_RIGHT);
    }
}
