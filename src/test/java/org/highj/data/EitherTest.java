package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.eq.Eq;
import org.highj.data.instance.either.EitherExtend;
import org.highj.data.instance.either.EitherMonad;
import org.highj.data.instance.either.EitherMonadPlus;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.highj.Hkt.asEither;
import static org.highj.data.Either.*;

public class EitherTest {

    @Test
    public void testBiFunctor() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(bifunctor.bimap(x -> x + x, y -> y / 7, left).getLeft()).isEqualTo("TestTest");
        assertThat(bifunctor.bimap(x -> x + x, y -> y / 7, right).getRight()).isEqualTo(6);
    }

    @Test
    public void testBimap() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.bimap(x -> x + x, y -> y / 7).getLeft()).isEqualTo("TestTest");
        assertThat(right.bimap(x -> x + x, y -> y / 7).getRight()).isEqualTo(6);
    }

    @Test
    public void testConstant() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.constant("x", "y")).isEqualTo("x");
        assertThat(right.constant("x", "y")).isEqualTo("y");
    }

    @Test
    public void testEither() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.either(String::length, Function.identity())).isEqualTo(4);
        assertThat(right.either(String::length, Function.identity())).isEqualTo(42);
    }

    @Test
    public void testEq() {
        Eq<Either<String, Integer>> eq = eq(Eq.fromEquals(), Eq.fromEquals());
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> left2 = Left("Test");
        Either<String, Integer> left3 = Left("TestX");
        Either<String, Integer> lazyLeft = Left$(() -> "Test");
        Either<String, Integer> right = Right(42);
        Either<String, Integer> right2 = Right(42);
        Either<String, Integer> right3 = Right(43);
        Either<String, Integer> lazyRight = Right$(() -> 42);
        assertThat(eq.eq(left, left2)).isTrue();
        assertThat(eq.eq(left, left3)).isFalse();
        assertThat(eq.eq(left, lazyLeft)).isTrue();
        assertThat(eq.eq(right, right2)).isTrue();
        assertThat(eq.eq(right, right3)).isFalse();
        assertThat(eq.eq(right, lazyRight)).isTrue();
        assertThat(eq.eq(left, right)).isFalse();
        assertThat(eq.eq(left, null)).isFalse();
        assertThat(eq.eq(null, right)).isFalse();
        assertThat(eq.eq(null, null)).isTrue();
    }

    @Test
    public void testEquals() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> left2 = Left("Test");
        Either<String, Integer> left3 = Left("TestX");
        Either<String, Integer> lazyLeft = Left$(() -> "Test");
        Either<String, Integer> right = Right(42);
        Either<String, Integer> right2 = Right(42);
        Either<String, Integer> right3 = Right(43);
        Either<String, Integer> lazyRight = Right$(() -> 42);
        Either<Integer, String> rightString = Right("Test");
        assertThat(left.equals(left2)).isTrue();
        assertThat(left.equals(left3)).isFalse();
        assertThat(left.equals(lazyLeft)).isTrue();
        assertThat(right.equals(right2)).isTrue();
        assertThat(right.equals(right3)).isFalse();
        assertThat(right.equals(lazyRight)).isTrue();
        assertThat(left.equals(right)).isFalse();
        assertThat(left.equals(rightString)).isFalse();
    }

    @Test
    public void testExtend() {
        EitherExtend<String> extend = Either.extend();
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(asEither(extend.duplicate(left)).getLeft()).isEqualTo("Test");
        assertThat(asEither(asEither(extend.duplicate(right)).getRight()).getRight()).isEqualTo(42);

        Function<__<__<µ, String>, Integer>, __<__<µ, String>, Integer>> fun = extend.extend(
            either -> asEither(either).rightMap(x -> x / 2).rightOrElse(-1));
        assertThat(asEither(fun.apply(left)).getLeft()).isEqualTo("Test");
        assertThat(asEither(fun.apply(right)).getRight()).isEqualTo(21);
    }

    @Test
    public void testFirstBiasedMonadPlus() {
        EitherMonadPlus<String> eitherMonadPlus = Either.firstBiasedMonadPlus(Strings.monoid);
        testMonadPlus(eitherMonadPlus, 1);
    }

    @Test
    public void testGetLeft() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.getLeft()).isEqualTo("Test");
        assertThatThrownBy(right::getLeft)
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testGetRight() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(right.getRight()).isEqualTo(42);
        assertThatThrownBy(left::getRight)
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testHashCode() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> left2 = Left("Test");
        Either<String, Integer> left3 = Left("TestX");
        Either<String, Integer> lazyLeft = Left$(() -> "Test");
        Either<String, Integer> right = Right(42);
        Either<String, Integer> right2 = Right(42);
        Either<String, Integer> right3 = Right(43);
        Either<String, Integer> lazyRight = Right$(() -> 42);
        Either<Integer, String> rightString = Right("Test");
        assertThat(left.hashCode()).isEqualTo(left2.hashCode());
        assertThat(left.hashCode()).isNotEqualTo(left3.hashCode());
        assertThat(left.hashCode()).isEqualTo(lazyLeft.hashCode());
        assertThat(right.hashCode()).isEqualTo(right2.hashCode());
        assertThat(right.hashCode()).isNotEqualTo(right3.hashCode());
        assertThat(right.hashCode()).isEqualTo(lazyRight.hashCode());
        assertThat(left.hashCode()).isNotEqualTo(right.hashCode());
        assertThat(left.hashCode()).isNotEqualTo(rightString.hashCode());
    }

    @Test
    public void testIsLeft() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.isLeft()).isTrue();
        assertThat(right.isLeft()).isFalse();
    }

    @Test
    public void testIsRight() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.isRight()).isFalse();
        assertThat(right.isRight()).isTrue();
    }

    @Test
    public void testLastBiasedMonadPlus() {
        EitherMonadPlus<String> eitherMonadPlus = Either.lastBiasedMonadPlus(Strings.monoid);
        testMonadPlus(eitherMonadPlus, 2);
    }

    @Test
    public void testLazyLeft() {
        String[] sideEffects = {"unchanged"};
        Either<String, Integer> left = Left$(() -> {
            sideEffects[0] = "changed";
            return "Test";
        });
        assertThat(left.isLeft()).isTrue();
        assertThat(sideEffects[0]).isEqualTo("unchanged");
        assertThat(left.getLeft()).isEqualTo("Test");
        assertThat(sideEffects[0]).isEqualTo("changed");
    }

    @Test
    public void testLazyLefts() {
        List<Either<String, Integer>> list = List.cycle(
            Left("a", Integer.class),
            Left("b", Integer.class),
            Right(String.class, 1));
        assertThat(lazyLefts(list).take(4)).containsExactly("a", "b", "a", "b");
    }

    @Test
    public void testLazyRight() {
        String[] sideEffects = {"unchanged"};
        Either<String, Integer> right = Right$(() -> {
            sideEffects[0] = "changed";
            return 42;
        });
        assertThat(right.isRight()).isTrue();
        assertThat(sideEffects[0]).isEqualTo("unchanged");
        assertThat(right.getRight()).isEqualTo(42);
        assertThat(sideEffects[0]).isEqualTo("changed");
    }

    @Test
    public void testLazyRights() {
        List<Either<String, Integer>> list = List.cycle(
            Left("a", Integer.class),
            Right(String.class, 1),
            Right(String.class, 2));
        assertThat(lazyRights(list).take(4)).containsExactly(1, 2, 1, 2);
    }

    @Test
    public void testLeftMap() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.leftMap(x -> x + x).getLeft()).isEqualTo("TestTest");
        assertThat(right.leftMap(x -> x + x).getRight()).isEqualTo(42);
    }

    @Test
    public void testLeftOrElse() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.leftOrElse("Default")).isEqualTo("Test");
        assertThat(right.leftOrElse("Default")).isEqualTo("Default");
    }

    @Test
    public void testLefts() {
        List<Either<String, Integer>> list = List.of(
            Left("a", Integer.class),
            Left("b", Integer.class),
            Right(String.class, 1),
            Left("c", Integer.class),
            Right(String.class, 2));
        assertThat(lefts(list)).containsExactly("a", "b", "c");
    }

    @Test
    public void testMaybeLeft() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.maybeLeft()).isEqualTo(Maybe.Just("Test"));
        assertThat(right.maybeLeft().isNothing()).isTrue();
    }

    @Test
    public void testMaybeRight() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.maybeRight().isNothing()).isTrue();
        assertThat(right.maybeRight()).isEqualTo(Maybe.Just(42));
    }

    @Test
    public void testMonad() {
        EitherMonad<String> eitherMonad = Either.monad();

        //map
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(eitherMonad.map(x -> x / 2, left).getLeft()).isEqualTo("Test");
        assertThat(eitherMonad.map(x -> x / 2, right).getRight()).isEqualTo(21);

        //ap
        Either<String, Function<Integer, Integer>> leftFn = Left("Nope");
        Either<String, Function<Integer, Integer>> rightFn = Right(x -> x / 2);
        assertThat(eitherMonad.ap(leftFn, left).getLeft()).isEqualTo("Nope");  //left biased like Haskell
        assertThat(eitherMonad.ap(leftFn, right).getLeft()).isEqualTo("Nope");
        assertThat(eitherMonad.ap(rightFn, left).getLeft()).isEqualTo("Test");
        assertThat(eitherMonad.ap(rightFn, right).getRight()).isEqualTo(21);

        //pure
        assertThat(eitherMonad.pure(12).getRight()).isEqualTo(12);

        //bind
        Either<String, Integer> rightOdd = Right(43);
        Function<Integer, __<__<µ, String>, Integer>> halfEven =
            x -> x % 2 == 0 ? Right(x / 2) : Left("Odd");
        assertThat(eitherMonad.bind(left, halfEven).getLeft()).isEqualTo("Test");
        assertThat(eitherMonad.bind(right, halfEven).getRight()).isEqualTo(21);
        assertThat(eitherMonad.bind(rightOdd, halfEven).getLeft()).isEqualTo("Odd");

        //tailRec
        Function<T2<Integer, Integer>, __<__<µ, String>, Either<T2<Integer, Integer>, Integer>>> factorial = pair -> {
            int factor = pair._1();
            int product = pair._2();
            if (factor < 0) {
                return Either.Left("Can't be negative");
            } else if (factor == 0) {
                return Either.Right(Either.Right(product));
            } else {
                return Either.Right(Either.Left(T2.of(factor - 1, factor * product)));
            }
        };
        Either<String, Integer> validResult = eitherMonad.tailRec(factorial, T2.of(10, 1));
        assertThat(validResult).isEqualTo(Either.Right(3628800));
        Either<String, Integer> invalidResult = eitherMonad.tailRec(factorial, T2.of(-10, 1));
        assertThat(invalidResult).isEqualTo(Either.Left("Can't be negative"));
    }

    private void testMonadPlus(EitherMonadPlus<String> eitherMonadPlus, Integer expectedWhenBiased) {
        Either<String, Integer> zero = eitherMonadPlus.mzero();
        assertThat(zero.getLeft()).isEqualTo("");
        //mplus
        Either<String, Integer> left1 = Left("one");
        Either<String, Integer> left2 = Left("two");
        Either<String, Integer> right1 = Right(1);
        Either<String, Integer> right2 = Right(2);
        assertThat(eitherMonadPlus.mplus(left1, left2).getLeft()).isEqualTo("onetwo");
        assertThat(eitherMonadPlus.mplus(right1, left2).getRight()).isEqualTo(1);
        assertThat(eitherMonadPlus.mplus(left1, right2).getRight()).isEqualTo(2);
        assertThat(eitherMonadPlus.mplus(right1, right2).getRight()).isEqualTo(expectedWhenBiased);
    }

    @Test
    public void testNewLeft() {
        Either<String, Integer> left = Left("Test");
        assertThat(left.isLeft()).isTrue();
        assertThat(left.getLeft()).isEqualTo("Test");
    }

    @Test
    public void testNewRight() {
        Either<String, Integer> right = Right(42);
        assertThat(right.isRight()).isTrue();
        assertThat(right.getRight()).isEqualTo(42);
    }

    @Test
    public void testOrd() {
        Ord<Either<String, Integer>> ord = Either.ord(
            Ord.<String>fromComparable(),
            Ord.<Integer>fromComparable());
        List<Either<String, Integer>> list = List.of(
            Left("a", Integer.class),
            Left("c", Integer.class),
            Right(String.class, 2),
            Left("b", Integer.class),
            Right(String.class, 1));
        assertThat(list.sort(ord)).containsExactly(
            Left("a", Integer.class),
            Left("b", Integer.class),
            Left("c", Integer.class),
            Right(String.class, 1),
            Right(String.class, 2));
    }

    @Test
    public void testRightMap() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.rightMap(x -> x / 7).getLeft()).isEqualTo("Test");
        assertThat(right.rightMap(x -> x / 7).getRight()).isEqualTo(6);
    }

    @Test
    public void testRightOrElse() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.rightOrElse(12)).isEqualTo(12);
        assertThat(right.rightOrElse(12)).isEqualTo(42);
    }

    @Test
    public void testRights() {
        List<Either<String, Integer>> list = List.of(
            Left("a", Integer.class),
            Left("b", Integer.class),
            Right(String.class, 1),
            Left("c", Integer.class),
            Right(String.class, 2));
        assertThat(rights(list)).containsExactly(1, 2);
    }

    @Test
    public void testSplit() {
        List<Either<String, Integer>> list = List.of(
            Left("a", Integer.class),
            Left("b", Integer.class),
            Right(String.class, 1),
            Left("c", Integer.class),
            Right(String.class, 2));
        T2<List<String>, List<Integer>> t2 = split(list);
        assertThat(t2._1()).containsExactly("a", "b", "c");
        assertThat(t2._2()).containsExactly(1, 2);
    }

    @Test
    public void testSwap() {
        Either<String, Integer> left = Left("Test");
        Either<String, Integer> right = Right(42);
        assertThat(left.swap()).isEqualTo(Right(Integer.class, "Test"));
        assertThat(right.swap()).isEqualTo(Left(42, String.class));
    }

    @Test
    public void testToString() {
        assertThat(Left("Test").toString()).isEqualTo("Left(Test)");
        assertThat(Right(42).toString()).isEqualTo("Right(42)");
    }

    @Test
    public void testUnify() {
        Either<String, String> left = Left("Foo");
        Either<String, String> right = Right("Bar");
        assertThat(unify(left)).isEqualTo("Foo");
        assertThat(unify(right)).isEqualTo("Bar");
    }
}
