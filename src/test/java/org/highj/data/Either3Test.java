package org.highj.data;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class Either3Test {

    @Rule
    public ExpectedException shouldThrow = ExpectedException.none();

    @Test
    public void narrow() {
        Either3<Integer, String, Long> either3 = Either3.Left(42);
        __3<Either3.µ, Integer, String, Long> either3HKT = either3;
        assertThat(Either3.narrow(either3HKT)).isSameAs(either3);
        __<__<__<Either3.µ, Integer>, String>, Long> either3curried = either3;
        assertThat(Either3.narrow(either3curried)).isSameAs(either3curried);
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
    public void constant() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.constant("L", "M", "R")).isEqualTo("L");
        assertThat(middle.constant("L", "M", "R")).isEqualTo("M");
        assertThat(right.constant("L", "M", "R")).isEqualTo("R");
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
    public void middleMap() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.middleMap(s -> s + "!")).isEqualTo(Either3.Left(42));
        assertThat(middle.middleMap(s -> s + "!")).isEqualTo(Either3.Middle("foo!"));
        assertThat(right.middleMap(s -> s + "!")).isEqualTo(Either3.Right(4711L));
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
    public void leftOrElse() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.leftOrElse(12)).isEqualTo(42);
        assertThat(middle.leftOrElse(12)).isEqualTo(12);
        assertThat(right.leftOrElse(12)).isEqualTo(12);
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
    public void rightOrElse() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.rightOrElse(666L)).isEqualTo(666L);
        assertThat(middle.rightOrElse(666L)).isEqualTo(666L);
        assertThat(right.rightOrElse(666L)).isEqualTo(4711L);
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
    public void testToString() {
        Either3<Integer, String, Long> left = Either3.Left(42);
        Either3<Integer, String, Long> middle = Either3.Middle("foo");
        Either3<Integer, String, Long> right = Either3.Right(4711L);
        assertThat(left.toString()).isEqualTo("Left3(42)");
        assertThat(middle.toString()).isEqualTo("Middle3(foo)");
        assertThat(right.toString()).isEqualTo("Right3(4711)");
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

}