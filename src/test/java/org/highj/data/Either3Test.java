package org.highj.data;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.eq.Eq;
import org.highj.data.instance.either3.*;
import org.highj.data.ord.Ord;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.highj.Hkt.asEither3;
import static org.highj.data.ord.Ordering.*;

public class Either3Test {

    @Test
    public void applicative() {
        Either3Applicative<Integer, String> applicative = Either3.applicative();
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);

        Either3<Integer, String, Function<Long, String>> leftFn = Either3.Left(57);
        Either3<Integer, String, Function<Long, String>> middleFn = Either3.Middle("bar");
        Either3<Integer, String, Function<Long, String>> rightFn = Either3.Right(x -> x + "!");

        assertThat(applicative.ap(leftFn, left)).isEqualTo(leftFn);
        assertThat(applicative.ap(leftFn, middle)).isEqualTo(leftFn);
        assertThat(applicative.ap(leftFn, right)).isEqualTo(leftFn);

        assertThat(applicative.ap(middleFn, left)).isEqualTo(middleFn);
        assertThat(applicative.ap(middleFn, middle)).isEqualTo(middleFn);
        assertThat(applicative.ap(middleFn, right)).isEqualTo(middleFn);

        assertThat(applicative.ap(rightFn, left)).isEqualTo(left);
        assertThat(applicative.ap(rightFn, middle)).isEqualTo(middle);
        assertThat(applicative.ap(rightFn, right)).isEqualTo(Either3.Right("4711!"));
    }

    @Test
    public void bifunctor() {
        Either3Bifunctor<Integer> bifunctor = Either3.bifunctor();
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);

        assertThat(bifunctor.first(x -> x + "!", left)).isEqualTo(left);
        assertThat(bifunctor.first(x -> x + "!", middle)).isEqualTo(Either3.Middle("foo!"));
        assertThat(bifunctor.first(x -> x + "!", right)).isEqualTo(right);

        assertThat(bifunctor.second(x -> x + "?", left)).isEqualTo(left);
        assertThat(bifunctor.second(x -> x + "?", middle)).isEqualTo(middle);
        assertThat(bifunctor.second(x -> x + "?", right)).isEqualTo(Either3.Right("4711?"));

        assertThat(bifunctor.bimap(x -> x + "!", y -> y + "?", left)).isEqualTo(left);
        assertThat(bifunctor.bimap(x -> x + "!", y -> y + "?", middle)).isEqualTo(Either3.Middle("foo!"));
        assertThat(bifunctor.bimap(x -> x + "!", y -> y + "?", right)).isEqualTo(Either3.Right("4711?"));
    }

    @Test
    public void caseLeft() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left
                       .caseLeft(x -> "L" + (x * 2))
                       .caseMiddle(s -> "M" + s + "!")
                       .caseRight(k -> "R" + (k + 1))
        ).isEqualTo("L84");
        assertThat(middle
                       .caseLeft(x -> "L" + (x * 2))
                       .caseMiddle(s -> "M" + s + "!")
                       .caseRight(k -> "R" + (k + 1))
        ).isEqualTo("Mfoo!");
        assertThat(right
                       .caseLeft(x -> "L" + (x * 2))
                       .caseMiddle(s -> "M" + s + "!")
                       .caseRight(k -> "R" + (k + 1))
        ).isEqualTo("R4712");
    }

    @Test
    public void combineLeft() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.combineLeft()).isEqualTo(Either.Left(Either.Left(42)));
        assertThat(middle.combineLeft()).isEqualTo(Either.Left(Either.Right("foo")));
        assertThat(right.combineLeft()).isEqualTo(Either.Right(4711L));
    }

    @Test
    public void combineRight() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.combineRight()).isEqualTo(Either.Left(42));
        assertThat(middle.combineRight()).isEqualTo(Either.Right(Either.Left("foo")));
        assertThat(right.combineRight()).isEqualTo(Either.Right(Either.Right(4711L)));
    }

    @Test
    public void constant() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.constant("L", "M", "R")).isEqualTo("L");
        assertThat(middle.constant("L", "M", "R")).isEqualTo("M");
        assertThat(right.constant("L", "M", "R")).isEqualTo("R");
    }

    @Test
    public void either() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.<String>either(x -> "L" + (x * 2), s -> "M" + s + "!", k -> "R" + (k + 1))).isEqualTo("L84");
        assertThat(middle.<String>either(x -> "L" + (x * 2), s -> "M" + s + "!", k -> "R" + (k + 1))).isEqualTo("Mfoo!");
        assertThat(right.<String>either(x -> "L" + (x * 2), s -> "M" + s + "!", k -> "R" + (k + 1))).isEqualTo("R4712");
    }

    @Test
    public void eq() {
        Either3Eq<Integer, String, Long> eq = Either3.eq(
            Eq.fromEquals(),
            Strings.eqIgnoreCase,
            Eq.fromEquals());
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);

        assertThat(eq.eq(null, null)).isTrue();

        assertThat(eq.eq(left, null)).isFalse();
        assertThat(eq.eq(null, left)).isFalse();

        assertThat(eq.eq(left, middle)).isFalse();
        assertThat(eq.eq(left, right)).isFalse();
        assertThat(eq.eq(middle, right)).isFalse();

        assertThat(eq.eq(left, Either3.Left(100))).isFalse();
        assertThat(eq.eq(middle, Either3.Middle("FOOO"))).isFalse();
        assertThat(eq.eq(right, Either3.Right(4712L))).isFalse();

        assertThat(eq.eq(left, Either3.Left(42))).isTrue();
        assertThat(eq.eq(middle, Either3.Middle("FoO"))).isTrue();
        assertThat(eq.eq(right, Either3.Right(4711L))).isTrue();
    }

    @Test
    public void equals() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left).isEqualTo(left);
        assertThat(middle).isEqualTo(middle);
        assertThat(right).isEqualTo(right);

        assertThat(left).isEqualTo(Either3.Left(42));
        assertThat(middle).isEqualTo(Either3.Middle("foo"));
        assertThat(right).isEqualTo(Either3.Right(4711L));

        assertThat(left).isNotEqualTo(middle);
        assertThat(left).isNotEqualTo(right);
        assertThat(middle).isNotEqualTo(left);
        assertThat(middle).isNotEqualTo(right);
        assertThat(right).isNotEqualTo(left);
        assertThat(right).isNotEqualTo(middle);
    }

    @Test
    public void functor() {
        Either3Functor<Integer, String> functor = Either3.functor();
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);

        assertThat(functor.map(x -> x + "!", left)).isEqualTo(left);
        assertThat(functor.map(x -> x + "!", middle)).isEqualTo(middle);
        assertThat(functor.map(x -> x + "!", right)).isEqualTo(Either3.Right("4711!"));
    }

    @Test
    public void getLeft() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.getLeft()).isEqualTo(42);
        try {
            middle.getLeft();
            failBecauseExceptionWasNotThrown(NoSuchElementException.class);
        } catch (NoSuchElementException ex) {
            //expected
        }

        try {
            right.getLeft();
            failBecauseExceptionWasNotThrown(NoSuchElementException.class);
        } catch (NoSuchElementException ex) {
            //expected
        }
    }

    @Test
    public void getMiddle() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(middle.getMiddle()).isEqualTo("foo");
        try {
            left.getMiddle();
            failBecauseExceptionWasNotThrown(NoSuchElementException.class);
        } catch (NoSuchElementException ex) {
            //expected
        }

        try {
            right.getMiddle();
            failBecauseExceptionWasNotThrown(NoSuchElementException.class);
        } catch (NoSuchElementException ex) {
            //expected
        }
    }

    @Test
    public void getRight() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(right.getRight()).isEqualTo(4711L);
        try {
            left.getRight();
            failBecauseExceptionWasNotThrown(NoSuchElementException.class);
        } catch (NoSuchElementException ex) {
            //expected
        }

        try {
            middle.getRight();
            failBecauseExceptionWasNotThrown(NoSuchElementException.class);
        } catch (NoSuchElementException ex) {
            //expected
        }
    }

    @Test
    public void isLeft() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.isLeft()).isTrue();
        assertThat(middle.isLeft()).isFalse();
        assertThat(right.isLeft()).isFalse();
    }

    @Test
    public void isMiddle() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.isMiddle()).isFalse();
        assertThat(middle.isMiddle()).isTrue();
        assertThat(right.isMiddle()).isFalse();
    }

    @Test
    public void isRight() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.isRight()).isFalse();
        assertThat(middle.isRight()).isFalse();
        assertThat(right.isRight()).isTrue();
    }

    @Test
    public void left() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        assertThat(left.isLeft()).isTrue();
        assertThat(left.getLeft()).isEqualTo(42);

        Either3<Integer, String, Long> lazyLeft = Either3.Left$(() -> 42);
        assertThat(lazyLeft.isLeft()).isTrue();
        assertThat(lazyLeft.getLeft()).isEqualTo(42);

        //test laziness
        Either3.Left$(() -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void leftMap() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.leftMap(x -> x * 2)).isEqualTo(Either3.Left(84));
        assertThat(middle.leftMap(x -> x * 2)).isEqualTo(Either3.Middle("foo"));
        assertThat(right.leftMap(x -> x * 2)).isEqualTo(Either3.Right(4711L));
    }

    @Test
    public void leftOrElse() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.leftOrElse(12)).isEqualTo(42);
        assertThat(middle.leftOrElse(12)).isEqualTo(12);
        assertThat(right.leftOrElse(12)).isEqualTo(12);
    }

    @Test
    public void maybeLeft() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.maybeLeft()).containsExactly(42);
        assertThat(middle.maybeLeft()).isEmpty();
        assertThat(right.maybeLeft()).isEmpty();
    }

    @Test
    public void maybeMiddle() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.maybeMiddle()).isEmpty();
        assertThat(middle.maybeMiddle()).containsExactly("foo");
        assertThat(right.maybeMiddle()).isEmpty();
    }

    @Test
    public void maybeRight() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.maybeRight()).isEmpty();
        assertThat(middle.maybeRight()).isEmpty();
        assertThat(right.maybeRight()).containsExactly(4711L);
    }

    @Test
    public void middle() {
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        assertThat(middle.isMiddle()).isTrue();
        assertThat(middle.getMiddle()).isEqualTo("foo");

        Either3<Integer, String, Long> lazyMiddle = Either3.Middle$(() -> "foo");
        assertThat(lazyMiddle.isMiddle()).isTrue();
        assertThat(lazyMiddle.getMiddle()).isEqualTo("foo");

        //test laziness
        Either3.Middle$(() -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void middleMap() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.middleMap(s -> s + "!")).isEqualTo(Either3.Left(42));
        assertThat(middle.middleMap(s -> s + "!")).isEqualTo(Either3.Middle("foo!"));
        assertThat(right.middleMap(s -> s + "!")).isEqualTo(Either3.Right(4711L));
    }

    @Test
    public void middleOrElse() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.middleOrElse("bar")).isEqualTo("bar");
        assertThat(middle.middleOrElse("bar")).isEqualTo("foo");
        assertThat(right.middleOrElse("bar")).isEqualTo("bar");
    }

    @Test
    public void monad() {
        Either3Monad<Integer, String> monad = Either3.monad();

        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);

        assertThat(monad.bind(left, x -> Either3.Middle(x + "!"))).isEqualTo(left);
        assertThat(monad.bind(middle, x -> Either3.Middle(x + "!"))).isEqualTo(middle);
        assertThat(monad.bind(right, x -> Either3.Middle(x + "!"))).isEqualTo(Either3.Middle("4711!"));
    }

    @Test
    public void monadRec() {
        Either3Monad<String, String> monadRec = Either3.monad();
        Function<T2<Integer, Integer>, __<__<__<Either3.µ, String>, String>, Either<T2<Integer, Integer>, Integer>>> factorial = pair -> {
            int factor = pair._1();
            int product = pair._2();
            if (factor < 0) {
                return Either3.Left("Can't be negative");
            } else if (factor > 10) {
                return Either3.Middle("Factorial gets too big");
            } else if (factor == 0) {
                return Either3.Right(Either.Right(product));
            } else {
                return Either3.Right(Either.Left(T2.of(factor - 1, factor * product)));
            }
        };
        Either3<String, String, Integer> validResult = monadRec.tailRec(factorial, T2.of(10, 1));
        assertThat(validResult).isEqualTo(Either3.Right(3628800));
        Either3<String, String, Integer> tooBigResult = monadRec.tailRec(factorial, T2.of(20, 1));
        assertThat(tooBigResult).isEqualTo(Either3.Middle("Factorial gets too big"));
        Either3<String, String, Integer> invalidResult = monadRec.tailRec(factorial, T2.of(-10, 1));
        assertThat(invalidResult).isEqualTo(Either3.Left("Can't be negative"));
    }

    @Test
    public void narrow() {
        Either3<Integer, String, Long> either3 = Either3.Left(42);
        __3<Either3.µ, Integer, String, Long> either3HKT = either3;
        assertThat(asEither3(either3HKT)).isSameAs(either3);
        __<__<__<Either3.µ, Integer>, String>, Long> either3curried = either3;
        assertThat(asEither3(either3curried)).isSameAs(either3curried);
    }

    @Test
    public void ord() {
        Either3Ord<Integer, String, Long> ord = Either3.ord(
            Ord.<Integer>fromComparable(),
            Strings.ordIgnoreCase,
            Ord.<Long>fromComparable());
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);

        assertThat(ord.cmp(null, null)).isEqualTo(EQ);

        assertThat(ord.cmp(left, null)).isEqualTo(GT);
        assertThat(ord.cmp(null, left)).isEqualTo(LT);

        assertThat(ord.cmp(left, middle)).isEqualTo(LT);
        assertThat(ord.cmp(left, right)).isEqualTo(LT);
        assertThat(ord.cmp(middle, right)).isEqualTo(LT);

        assertThat(ord.cmp(middle, left)).isEqualTo(GT);
        assertThat(ord.cmp(right, left)).isEqualTo(GT);
        assertThat(ord.cmp(right, middle)).isEqualTo(GT);

        assertThat(ord.cmp(left, Either3.Left(100))).isEqualTo(LT);
        assertThat(ord.cmp(middle, Either3.Middle("FOOO"))).isEqualTo(LT);
        assertThat(ord.cmp(right, Either3.Right(4712L))).isEqualTo(LT);

        assertThat(ord.cmp(left, Either3.Left(42))).isEqualTo(EQ);
        assertThat(ord.cmp(middle, Either3.Middle("FoO"))).isEqualTo(EQ);
        assertThat(ord.cmp(right, Either3.Right(4711L))).isEqualTo(EQ);

        assertThat(ord.cmp(left, Either3.Left(10))).isEqualTo(GT);
        assertThat(ord.cmp(middle, Either3.Middle("FO"))).isEqualTo(GT);
        assertThat(ord.cmp(right, Either3.Right(4710L))).isEqualTo(GT);
    }

    @Test
    public void right() {
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(right.isRight()).isTrue();
        assertThat(right.getRight()).isEqualTo(4711L);

        Either3<Integer, String, Long> lazyRight = Either3.Right$(() -> 4711L);
        assertThat(lazyRight.isRight()).isTrue();
        assertThat(lazyRight.getRight()).isEqualTo(4711L);

        //test laziness
        Either3.Right$(() -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void rightMap() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.rightMap(k -> k + 1)).isEqualTo(Either3.Left(42));
        assertThat(middle.rightMap(k -> k + 1)).isEqualTo(Either3.Middle("foo"));
        assertThat(right.rightMap(k -> k + 1)).isEqualTo(Either3.Right(4712L));
    }

    @Test
    public void rightOrElse() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.rightOrElse(666L)).isEqualTo(666L);
        assertThat(middle.rightOrElse(666L)).isEqualTo(666L);
        assertThat(right.rightOrElse(666L)).isEqualTo(4711L);
    }

    @Test
    public void testToString() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.toString()).isEqualTo("Left3(42)");
        assertThat(middle.toString()).isEqualTo("Middle3(foo)");
        assertThat(right.toString()).isEqualTo("Right3(4711)");
    }

    @Test
    public void trimap() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.trimap(x -> x * 2, s -> s + "!", k -> k + 1)).isEqualTo(Either3.Left(84));
        assertThat(middle.trimap(x -> x * 2, s -> s + "!", k -> k + 1)).isEqualTo(Either3.Middle("foo!"));
        assertThat(right.trimap(x -> x * 2, s -> s + "!", k -> k + 1)).isEqualTo(Either3.Right(4712L));
    }
}