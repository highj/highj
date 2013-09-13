package org.highj.data.collection;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.either.EitherBifunctor;
import org.highj.data.collection.either.EitherMonad;
import org.highj.data.collection.either.EitherMonadPlus;
import org.highj.data.functions.Strings;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass0.compare.Ord;
import org.junit.Test;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.highj.data.collection.Either.*;
import static org.highj.data.collection.Either.bifunctor;
import static org.junit.Assert.*;

public class EitherTest {
    @Test
    public void testNarrow() throws Exception {
        __<Either.µ, String, Integer> wideLeft = newLeft("Test");
        Either<String, Integer> left = narrow(wideLeft);
        assertEquals(newLeft("Test", Integer.class), left);
        __<Either.µ, String, Integer> wideRight = newRight(42);
        Either<String, Integer> right = narrow(wideRight);
        assertEquals(newRight(String.class, 42), right);
    }

    @Test
    public void testEither() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(Integer.valueOf(4), left.either(String::length, x -> x));
        assertEquals(Integer.valueOf(42), right.either(String::length, x -> x));
    }

    @Test
    public void testConstant() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals("x", left.constant("x", "y"));
        assertEquals("y", right.constant("x", "y"));
    }

    @Test
    public void testBimap() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(newLeft("TestTest", Integer.class), left.bimap(x -> x + x, y -> y / 7));
        assertEquals(newRight(String.class, 6), right.bimap(x -> x + x, y -> y / 7));
    }

    @Test
    public void testLeftMap() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(newLeft("TestTest", Integer.class), left.leftMap(x -> x + x));
        assertEquals(newRight(String.class, 42), right.leftMap(x -> x + x));
    }

    @Test
    public void testRightMap() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(newLeft("Test", Integer.class), left.rightMap(x -> x / 7));
        assertEquals(newRight(String.class, 6), right.rightMap(x -> x / 7));
    }

    @Test
    public void testNewLeft() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        assertEquals("Left(Test)", left.toString());
    }

    @Test
    public void testLazyLeft() throws Exception {
        Either<String, Integer> left = lazyLeft(() -> "Test");
        assertEquals(newLeft("Test", Integer.class), left);
    }

    @Test
    public void testNewRight() throws Exception {
        Either<String, Integer> right = newRight(42);
        assertEquals("Right(42)", right.toString());
    }

    @Test
    public void testLazyRight() throws Exception {
        Either<String, Integer> right = lazyRight(() -> 42);
        assertEquals(newRight(String.class, 42), right);
    }

    @Test
    public void testSwap() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(newRight(Integer.class, "Test"), left.swap());
        assertEquals(newLeft(42, String.class), right.swap());
    }

    @Test
    public void testIsLeft() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertTrue(left.isLeft());
        assertFalse(right.isLeft());
    }

    @Test
    public void testIsRight() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertFalse(left.isRight());
        assertTrue(right.isRight());
    }

    @Test
    public void testMaybeLeft() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(Maybe.Just("Test"), left.maybeLeft());
        assertEquals(Maybe.<String>Nothing(), right.maybeLeft());
    }

    @Test
    public void testMaybeRight() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(Maybe.<Integer>Nothing(), left.maybeRight());
        assertEquals(Maybe.Just(42), right.maybeRight());
    }

    @Test
    public void testLeftOrElse() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals("Test", left.leftOrElse("Default"));
        assertEquals("Default", right.leftOrElse("Default"));
    }

    @Test
    public void testRightOrElse() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(Integer.valueOf(12), left.rightOrElse(12));
        assertEquals(Integer.valueOf(42), right.rightOrElse(12));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetLeft() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals("Test", left.getLeft());
        right.getLeft();
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetRight() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(Integer.valueOf(42), right.getRight());
        left.getRight();
    }

    @Test
    public void testLefts() throws Exception {
        List<Either<String, Integer>> list = List.of(
                newLeft("a", Integer.class),
                newLeft("b", Integer.class),
                newRight(String.class, 1),
                newLeft("c", Integer.class),
                newRight(String.class, 2));
        assertEquals(List.of("a", "b", "c"), lefts(list));
    }

    @Test
    public void testLazyLefts() throws Exception {
        List<Either<String, Integer>> list = List.cycle(
                newLeft("a", Integer.class),
                newLeft("b", Integer.class),
                newRight(String.class, 1));
        assertEquals(List.of("a", "b", "a", "b"), lazyLefts(list).take(4));
    }

    @Test
    public void testRights() throws Exception {
        List<Either<String, Integer>> list = List.of(
                newLeft("a", Integer.class),
                newLeft("b", Integer.class),
                newRight(String.class, 1),
                newLeft("c", Integer.class),
                newRight(String.class, 2));
        assertEquals(List.of(1,2), rights(list));
    }

    @Test
    public void testLazyRights() throws Exception {
        List<Either<String, Integer>> list = List.cycle(
                newLeft("a", Integer.class),
                newRight(String.class, 1),
                newRight(String.class, 2));
        assertEquals(List.of(1, 2, 1, 2), lazyRights(list).take(4));

    }

    @Test
    public void testSplit() throws Exception {
        List<Either<String, Integer>> list = List.of(
                newLeft("a", Integer.class),
                newLeft("b", Integer.class),
                newRight(String.class, 1),
                newLeft("c", Integer.class),
                newRight(String.class, 2));
        assertEquals(T2.of(List.of("a", "b", "c"), List.of(1, 2)), split(list));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Left(Test)", newLeft("Test").toString());
        assertEquals("Right(42)", newRight(42).toString());
    }

    @Test
    public void testHashCode() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> left2 = newLeft("Test");
        Either<String, Integer> left3 = newLeft("TestX");
        Either<String, Integer> lazyLeft = lazyLeft(() -> "Test");
        Either<String, Integer> right = newRight(42);
        Either<String, Integer> right2 = newRight(42);
        Either<String, Integer> right3 = newRight(43);
        Either<String, Integer> lazyRight = lazyRight(() -> 42);
        Either<Integer,String> rightString = newRight("Test");
        assertTrue(left.hashCode() == left2.hashCode());
        assertTrue(left.hashCode() != left3.hashCode());
        assertTrue(left.hashCode() == lazyLeft.hashCode());
        assertTrue(right.hashCode() == right2.hashCode());
        assertTrue(right.hashCode() != right3.hashCode());
        assertTrue(right.hashCode() == lazyRight.hashCode());
        assertTrue(left.hashCode() != right.hashCode());
        assertTrue(left.hashCode() != rightString.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> left2 = newLeft("Test");
        Either<String, Integer> left3 = newLeft("TestX");
        Either<String, Integer> lazyLeft = lazyLeft(() -> "Test");
        Either<String, Integer> right = newRight(42);
        Either<String, Integer> right2 = newRight(42);
        Either<String, Integer> right3 = newRight(43);
        Either<String, Integer> lazyRight = lazyRight(() -> 42);
        Either<Integer,String> rightString = newRight("Test");
        assertTrue(left.equals(left2));
        assertFalse(left.equals(left3));
        assertTrue(left.equals(lazyLeft));
        assertTrue(right.equals(right2));
        assertFalse(right.equals(right3));
        assertTrue(right.equals(lazyRight));
        assertFalse(left.equals(right));
        assertFalse(left.equals(rightString));
    }

    @Test
    public void testUnify() throws Exception {
        Either<String, String> left = newLeft("Foo");
        Either<String, String> right = newRight("Bar");
        assertEquals("Foo", unify(left));
        assertEquals("Bar", unify(right));
    }

    @Test
    public void testEq() throws Exception {
        Eq<Either<String,Integer>> eq = eq(new Eq.JavaEq<String>(), new Eq.JavaEq<Integer>());
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> left2 = newLeft("Test");
        Either<String, Integer> left3 = newLeft("TestX");
        Either<String, Integer> lazyLeft = lazyLeft(() -> "Test");
        Either<String, Integer> right = newRight(42);
        Either<String, Integer> right2 = newRight(42);
        Either<String, Integer> right3 = newRight(43);
        Either<String, Integer> lazyRight = lazyRight(() -> 42);
        assertTrue(eq.eq(left,left2));
        assertFalse(eq.eq(left, left3));
        assertTrue(eq.eq(left, lazyLeft));
        assertTrue(eq.eq(right, right2));
        assertFalse(eq.eq(right, right3));
        assertTrue(eq.eq(right, lazyRight));
        assertFalse(eq.eq(left, right));
        assertFalse(eq.eq(left, null));
        assertFalse(eq.eq(null, right));
        assertTrue(eq.eq(null, null));
    }

    @Test
    public void testOrd() throws Exception {
        Ord<Either<String,Integer>> ord = Either.ord(Ord.<String>fromComparable(), Ord.<Integer>fromComparable());
        List<Either<String, Integer>> list = List.of(
                newLeft("a", Integer.class),
                newLeft("c", Integer.class),
                newRight(String.class, 2),
                newLeft("b", Integer.class),
                newRight(String.class, 1));
        List<Either<String, Integer>> expected = List.of(
                newLeft("a", Integer.class),
                newLeft("b", Integer.class),
                newLeft("c", Integer.class),
                newRight(String.class, 1),
                newRight(String.class, 2));
        assertEquals(expected, list.sort(ord));
    }

    @Test
    public void testMonad() throws Exception {
        EitherMonad<String> eitherMonad = Either.monad();
        //map
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(newLeft("Test", Integer.class), eitherMonad.map(x -> x / 2, left));
        assertEquals(newRight(String.class, 21), eitherMonad.map(x -> x / 2, right));
        //ap
        Either<String, Function<Integer,Integer>> leftFn = newLeft("Nope");
        Either<String, Function<Integer,Integer>> rightFn = newRight(x -> x / 2);
        assertEquals(newLeft("Nope", Integer.class), eitherMonad.ap(leftFn, left));  //left biased like Haskell
        assertEquals(newLeft("Nope", Integer.class), eitherMonad.ap(leftFn, right));
        assertEquals(newLeft("Test", Integer.class), eitherMonad.ap(rightFn, left));
        assertEquals(newRight(String.class, 21), eitherMonad.ap(rightFn, right));
        //pure
        assertEquals(newRight(String.class, 12), eitherMonad.pure(12));
        //bind
        Either<String, Integer> rightOdd = newRight(43);
        Function<Integer, _<__.µ<Either.µ,String>, Integer>> halfEven = x -> x % 2 == 0 ? newRight(x / 2) : newLeft("Odd");
        assertEquals(newLeft("Test", Integer.class), eitherMonad.bind(left, halfEven));
        assertEquals(newRight(String.class, 21), eitherMonad.bind(right, halfEven));
        assertEquals(newLeft("Odd", Integer.class), eitherMonad.bind(rightOdd, halfEven));
    }


    @Test
    public void testFirstBiasedMonadPlus() throws Exception {
        EitherMonadPlus<String> eitherMonadPlus = Either.firstBiasedMonadPlus(Strings.group);
        //mzero
        Either<String,Integer> zero = eitherMonadPlus.mzero();
        assertEquals(newLeft("", Integer.class), zero);
        //mplus
        Either<String, Integer> left1 = newLeft("one");
        Either<String, Integer> left2 = newLeft("two");
        Either<String, Integer> right1 = newRight(1);
        Either<String, Integer> right2 = newRight(2);
        assertEquals(newLeft("onetwo", Integer.class), eitherMonadPlus.mplus(left1,left2));
        assertEquals(newRight(String.class, 1), eitherMonadPlus.mplus(right1,left2));
        assertEquals(newRight(String.class, 2), eitherMonadPlus.mplus(left1,right2));
        assertEquals(newRight(String.class, 1), eitherMonadPlus.mplus(right1,right2)); //first biased
    }

    @Test
    public void testLastBiasedMonadPlus() throws Exception {
        EitherMonadPlus<String> eitherMonadPlus = Either.lastBiasedMonadPlus(Strings.group);
        //mzero
        Either<String,Integer> zero = eitherMonadPlus.mzero();
        assertEquals(newLeft("", Integer.class), zero);
        //mplus
        Either<String, Integer> left1 = newLeft("one");
        Either<String, Integer> left2 = newLeft("two");
        Either<String, Integer> right1 = newRight(1);
        Either<String, Integer> right2 = newRight(2);
        assertEquals(newLeft("onetwo", Integer.class), eitherMonadPlus.mplus(left1,left2));
        assertEquals(newRight(String.class, 1), eitherMonadPlus.mplus(right1,left2));
        assertEquals(newRight(String.class, 2), eitherMonadPlus.mplus(left1,right2));
        assertEquals(newRight(String.class, 2), eitherMonadPlus.mplus(right1,right2)); //last biased
    }

    @Test
    public void testBiFunctor() throws Exception {
        Either<String, Integer> left = newLeft("Test");
        Either<String, Integer> right = newRight(42);
        assertEquals(newLeft("TestTest", Integer.class), bifunctor.bimap(x -> x + x, y -> y / 7, left));
        assertEquals(newRight(String.class, 6), bifunctor.bimap(x -> x + x, y -> y / 7, right));
    }

}
