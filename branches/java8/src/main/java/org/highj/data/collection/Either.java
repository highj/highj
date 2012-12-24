package org.highj.data.collection;

import org.highj._;
import org.highj.__;
import org.highj.data.compare.Eq;
import org.highj.function.*;
import org.highj.function.repo.Integers;
import org.highj.function.repo.Objects;
import org.highj.function.repo.Strings;
import org.highj.typeclass.alternative.Alt;
import org.highj.typeclass.monad.Monad;

import java.util.NoSuchElementException;

public abstract class Either<A, B> extends __<Either.µ, A, B> {
    private static final µ hidden = new µ();

    private static final String SHOW_LEFT = "Left(%s)";
    private static final String SHOW_RIGHT = "Right(%s)";

    /**
     * The witness class of Either
     */
    public static class µ {
        private µ() {
        }
    }

    private Either() {
        super(hidden);
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
    protected abstract <C> C cata(F1<A, C> leftFn, F1<B, C> rightFn);

    /**
     * The catamorphism for constant functions
     *
     * @param left  result if it is a Left value
     * @param right result if it is a Right value
     * @param <C>   the result type
     * @return the result of the function application
     */
    public <C> C constant(C left, C right) {
        return cata(F1.<A, C>constant(left), F1.<B, C>constant(right));
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
    public <C, D> Either<C, D> bimap(F1<A, C> leftFn, F1<B, D> rightFn) {
        return cata(leftFn.andThen(Either.<C, D>left()), rightFn.andThen(Either.<C, D>right()));
    }

    public <C> Either<C, B> leftMap(F1<A, C> leftFn) {
        return bimap(leftFn, F1.<B>id());
    }

    public <C> Either<A, C> rightMap(F1<B, C> rightFn) {
        return bimap(F1.<A>id(), rightFn);
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
            public <C> C cata(F1<A, C> leftFn, F1<B, C> rightFn) {
                return leftFn.$(a);
            }
        };
    }

    /**
     * Construction of a lazy Left value
     *
     * @param thunk the Left thunk
     * @param <A>   the type of the Left value
     * @param <B>   the type of the Right value
     * @return the Left Either
     */
    public static <A, B> Either<A, B> Left(final F0<A> thunk) {
        return new Either<A, B>() {

            @Override
            public <C> C cata(F1<A, C> leftFn, F1<B, C> rightFn) {
                return leftFn.$(thunk.$());
            }
        };
    }

    /**
     * Function for constructing a Left value
     *
     * @param <A> the type of the Left value
     * @param <B> the type of the Right value
     * @return a function which returns a Left Either if applied to an A
     */
    public static <A, B> F1<A, Either<A, B>> left() {
        return new F1<A, Either<A, B>>() {

            @Override
            public Either<A, B> $(A a) {
                return Left(a);
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
            public <C> C cata(F1<A, C> leftFn, F1<B, C> rightFn) {
                return rightFn.$(b);
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
    public static <A, B> Either<A, B> Right(final F0<B> thunk) {
        return new Either<A, B>() {

            @Override
            public <C> C cata(F1<A, C> leftFn, F1<B, C> rightFn) {
                return rightFn.$(thunk.$());
            }
        };
    }

    /**
     * Function for constructing a Right value
     *
     * @param <A> the type of the Left value
     * @param <B> the type of the Right value
     * @return a function which returns a Right Either if applied to a B
     */
    public static <A, B> F1<B, Either<A, B>> right() {
        return new F1<B, Either<A, B>>() {

            @Override
            public Either<A, B> $(B b) {
                return Right(b);
            }
        };
    }

    /**
     * Converts an Either&lt;A,B&gt; to an Either &lt;B,A&gt;
     *
     * @return the swapped Either
     */
    public Either<B, A> swap() {
        return cata(Either.<B, A>right(), Either.<B, A>left());
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
        return cata(Maybe.<A>just(), F1.<B, Maybe<A>>constant(Maybe.<A>Nothing()));
    }

    /**
     * Extracts the Right value wrapped in Maybe
     *
     * @return a Maybe containing Right value, if it exists
     */
    public Maybe<B> maybeRight() {
        return cata(F1.<A, Maybe<B>>constant(Maybe.<B>Nothing()), Maybe.<B>just());
    }

    /**
     * Extracts the Left value, mplus the default value if ir doesn't exist
     *
     * @param defaultValue default value
     * @return the Left value if it exists, else the default value
     */
    public A leftOrElse(A defaultValue) {
        return cata(F1.<A>id(), F1.<B, A>constant(defaultValue));
    }

    /**
     * Extracts the Right value, mplus the default value if ir doesn't exist
     *
     * @param defaultValue default value
     * @return the Right value if it exists, else the default value
     */
    public B rightOrElse(B defaultValue) {
        return cata(F1.<A, B>constant(defaultValue), F1.<B>id());
    }

    /**
     * Extracts the Left value
     *
     * @return the Left value, mplus throws an exception if none exists
     */
    public A getLeft() throws NoSuchElementException {
        return cata(F1.<A>id(), F1.<B, A>constant(
                F0.<A>error(NoSuchElementException.class, "getLeft called on a Right")));
    }

    /**
     * Extracts the Right value
     *
     * @return the Right value, mplus throws an exception if none exists
     */
    public B getRight() throws NoSuchElementException {
        return cata(F1.<A, B>constant(
                F0.<B>error(NoSuchElementException.class, "getRight called on a Left")), F2.<B>id());
    }

    /**
     * Extracts the Left values from a List
     *
     * @param list a List of Eithers
     * @return a List of all Left values
     */
    public static <A> List<A> lefts(List<Either<A, ?>> list) {
        List<A> result = List.Nil();
        for (Either<A, ?> either : list) {
            if (either.isLeft()) {
                result = List.Cons(either.getLeft(), result);
            }
        }
        return result.reverse();
    }

    /**
     * Extracts the Right values from a List
     *
     * @param list a List of Eithers
     * @return a List of all Right values
     */
    public static <B> List<B> rights(List<Either<?, B>> list) {
        List<B> result = List.Nil();
        for (Either<?, B> either : list) {
            if (either.isRight()) {
                result = List.Cons(either.getRight(), result);
            }
        }
        return result.reverse();
    }

    /**
     * Generates a String representation
     *
     * @return a String representation
     */
    @Override
    public String toString() {
        return cata(Strings.<A>format(SHOW_LEFT), Strings.<B>format(SHOW_RIGHT));
    }

    /**
     * Calculates a hash code
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return cata(Objects.<A>hashCodeFn().andThen(Integers.multiply.$(31)),
                Objects.<B>hashCodeFn().andThen(Integers.multiply.$(47)));
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
     * Generates an Eq instance
     *
     * @param eqA Eq instance for Left type
     * @param eqB Eq instance for Right type
     * @param <A> Left type
     * @param <B> Right type
     * @return Eq instance for Either
     */
    public static <A, B> Eq<Either<A, B>> eq(final Eq<A> eqA, final Eq<B> eqB) {
        return new Eq<Either<A, B>>() {

            @Override
            public boolean eq(Either<A, B> one, Either<A, B> two) {
                if (one.isLeft() && two.isLeft()) {
                    return eqA.eq(one.getLeft(), two.getLeft());
                }
                if (one.isRight() && two.isRight()) {
                    return eqB.eq(one.getRight(), two.getRight());
                }
                return false;
            }
        };
    }

    /**
     * The monad of Either
     *
     * @param <S> the Left type of Either
     * @return the Either functor
     */
    private static <S> Monad<__.µ<µ, S>> monad() {
        return new Monad<__.µ<µ, S>>() {

            @Override
            public <A, B> _<__.µ<µ, S>, B> map(F1<A, B> fn, _<__.µ<µ, S>, A> nestedA) {
                return narrow(nestedA).rightMap(fn);
            }

            @Override
            public <A> _<__.µ<µ, S>, A> pure(A a) {
                return Right(a);
            }

            @Override
            public <A, B> _<__.µ<µ, S>, B> ap(_<__.µ<µ, S>, F1<A, B>> fn, _<__.µ<µ, S>, A> nestedA) {
                //a <*> b = do x <- a; y <- b; return (x y)
                Either<S,F1<A, B>> eitherFn = narrow(fn);
                if (eitherFn.isLeft()) return Left(eitherFn.getLeft());
                Either<S,A> eitherA = narrow(nestedA);
                return eitherA.isRight()
                        ? pure(eitherFn.getRight().$(eitherA.getRight()))
                        : Either.<S,B>Left(eitherA.getLeft());
            }

            @Override
            public <A, B> _<__.µ<µ, S>, B> bind(_<__.µ<µ, S>, A> a, F1<A, _<__.µ<µ, S>, B>> fn) {
                //Right m >>= k = k m
                //Left e  >>= _ = Left e
                Either<S,A> either = narrow(a);
                return either.isRight()
                        ? fn.$(either.getRight())
                        : Either.<S,B>Left(either.getLeft());
            }
        };
    }

    public static <S> Alt<__.µ<µ, S>> alt() {
        return new Alt<__.µ<µ, S>>() {

            @Override
            public <A> _<__.µ<µ, S>, A> mplus(_<__.µ<µ, S>, A> first, _<__.µ<µ, S>, A> second) {
                return narrow(first).isLeft() ? second : first;
            }

            @Override
            public <A, B> _<__.µ<µ, S>, B> map(F1<A, B> fn, _<__.µ<µ, S>, A> nestedA) {
                return narrow(nestedA).rightMap(fn);
            }
        };
    }
}
