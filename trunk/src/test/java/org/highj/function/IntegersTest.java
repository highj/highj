package org.highj.function;

import org.highj.data.collection.List;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class IntegersTest {

    @Test
    public void eqTest() {
        assertTrue(Integers.eq.eq(42, 42));
        assertTrue(!Integers.eq.eq(42, 43));
        assertTrue(Integers.eq.eq(-3442, -3442));
        assertTrue(!Integers.eq.eq(3442, -3442));
    }

    @Test
    public void absTest() {
        assertInt(0, Integers.abs.$(0));
        assertInt(42, Integers.abs.$(42));
        assertInt(42, Integers.abs.$(-42));
    }

    @Test
    public void negateTest() {
        assertInt(0, Integers.negate.$(0));
        assertInt(-42, Integers.negate.$(42));
        assertInt(42, Integers.negate.$(-42));
    }

    @Test
    public void sqrTest() {
        assertInt(0, Integers.sqr.$(0));
        assertInt(1764, Integers.sqr.$(42));
        assertInt(1764, Integers.sqr.$(-42));
    }

    @Test
    public void notTest() {
        assertInt(-1, Integers.not.$(0));
        assertInt(~42, Integers.not.$(42));
        assertInt(~-42, Integers.not.$(-42));
    }

    @Test
    public void succTest() {
        assertInt(0, Integers.succ.$(-1));
        assertInt(1, Integers.succ.$(0));
        assertInt(43, Integers.succ.$(42));
    }

    @Test
    public void predTest() {
        assertInt(-1, Integers.pred.$(0));
        assertInt(0, Integers.pred.$(1));
        assertInt(42, Integers.pred.$(43));
    }

    @Test
    public void addTest() {
        assertInt(0, Integers.add.$(42, -42));
        assertInt(84, Integers.add.$(42, 42));
        assertInt(-1, Integers.add.$(-3, 2));
    }

    @Test
    public void subtractTest() {
        assertInt(84, Integers.subtract.$(42, -42));
        assertInt(0, Integers.subtract.$(42, 42));
        assertInt(-5, Integers.subtract.$(-3, 2));
    }

    @Test
    public void multiplyTest() {
        assertInt(-1764, Integers.multiply.$(42, -42));
        assertInt(0, Integers.multiply.$(42, 0));
        assertInt(111, Integers.multiply.$(3, 37));
    }

    @Test
    public void divideTest() {
        assertInt(2, Integers.divide.$(13, 5));
        assertInt(-2, Integers.divide.$(13, -5));
        assertInt(2, Integers.divide.$(-13, -5));
        assertInt(-2, Integers.divide.$(-13, 5));
    }

    @Test
    public void powTest() {
        assertInt(81, Integers.pow.$(3, 4));
        assertInt(100, Integers.pow.$(10, 2));
        assertInt(125, Integers.pow.$(5, 3));
        assertInt(256, Integers.pow.$(2, 8));
    }

    @Test
    public void modTest() {
        assertInt(3, Integers.mod.$(13, 5));
        assertInt(3, Integers.mod.$(13, -5));
        assertInt(-3, Integers.mod.$(-13, -5));
        assertInt(-3, Integers.mod.$(-13, 5));
    }

    @Test
    public void andTest() {
        assertInt(0, Integers.and.$(42, 0));
        assertInt(0, Integers.and.$(15, 16));
        assertInt(5, Integers.and.$(13, 21));
    }

    @Test
    public void orTest() {
        assertInt(42, Integers.or.$(42, 0));
        assertInt(31, Integers.or.$(15, 16));
        assertInt(29, Integers.or.$(13, 21));
    }

    @Test
    public void xorTest() {
        assertInt(42, Integers.xor.$(42, 0));
        assertInt(31, Integers.xor.$(15, 16));
        assertInt(24, Integers.xor.$(13, 21));
    }

    @Test
    public void minTest() {
        assertInt(0, Integers.min.$(42, 0));
        assertInt(-15, Integers.min.$(-15, -13));
        assertInt(-42, Integers.min.$(-42, 42));
    }

    @Test
    public void maxTest() {
        assertInt(42, Integers.max.$(42, 0));
        assertInt(-13, Integers.max.$(-15, -13));
        assertInt(42, Integers.max.$(-42, 42));
    }

    @Test
    public void gcdTest() {
        assertInt(1, Integers.gcd.$(257, 111));
        assertInt(7, Integers.gcd.$(35, -42));
        assertInt(6, Integers.gcd.$(-30, 66));
    }

    @Test
    public void negativeTest() {
        assertTrue(!Integers.negative.$(0));
        assertTrue(!Integers.negative.$(42));
        assertTrue(Integers.negative.$(-42));
    }

    @Test
    public void positiveTest() {
        assertTrue(!Integers.positive.$(0));
        assertTrue(Integers.positive.$(42));
        assertTrue(!Integers.positive.$(-42));
    }

    @Test
    public void zeroTest() {
        assertTrue(Integers.zero.$(0));
        assertTrue(!Integers.zero.$(42));
        assertTrue(!Integers.zero.$(-42));
    }

    @Test
    public void evenTest() {
        assertTrue(Integers.even.$(0));
        assertTrue(Integers.even.$(42));
        assertTrue(Integers.even.$(-42));
        assertTrue(!Integers.even.$(43));
        assertTrue(!Integers.even.$(-43));
    }

    @Test
    public void oddTest() {
        assertTrue(!Integers.odd.$(0));
        assertTrue(!Integers.odd.$(42));
        assertTrue(!Integers.odd.$(-42));
        assertTrue(Integers.odd.$(43));
        assertTrue(Integers.odd.$(-43));
    }

    @Test
    public void additiveGroupTest() {
        assertInt(0, Integers.additiveGroup.identity());
        assertInt(40, Integers.additiveGroup.dot(17, 23));
        assertInt(40, Integers.additiveGroup.dot().$(17, 23));
        assertInt(-5, Integers.additiveGroup.inverse(5));
        assertInt(5, Integers.additiveGroup.inverse(-5));
        assertInt(0, Integers.additiveGroup.inverse(0));
        assertInt(-5, Integers.additiveGroup.inverse().$(5));
        assertInt(10, Integers.additiveGroup.fold(1, 2, 3, 4));
    }

    @Test
    public void multiplicativeMonoidTest() {
        assertInt(1, Integers.multiplicativeMonoid.identity());
        assertInt(391, Integers.multiplicativeMonoid.dot(17, 23));
        assertInt(391, Integers.multiplicativeMonoid.dot().$(17, 23));
        assertInt(24, Integers.multiplicativeMonoid.fold(1, 2, 3, 4));
    }

    @Test
    public void minMonoidTest() {
        assertInt(Integer.MAX_VALUE, Integers.minMonoid.identity());
        assertInt(17, Integers.minMonoid.dot(17, 23));
        assertInt(17, Integers.minMonoid.dot().$(17, 23));
        assertInt(-3, Integers.minMonoid.fold(2, -3, 1, 4));
    }

    @Test
    public void maxMonoidTest() {
        assertInt(Integer.MIN_VALUE, Integers.maxMonoid.identity());
        assertInt(23, Integers.maxMonoid.dot(17, 23));
        assertInt(23, Integers.maxMonoid.dot().$(17, 23));
        assertInt(4, Integers.maxMonoid.fold(2, -3, 4, 1));
    }

    @Test
    public void xorSemigroupTest() {
        assertInt(6, Integers.xorSemigroup.dot(17, 23));
        assertInt(6, Integers.xorSemigroup.dot().$(17, 23));
        assertInt(2, Integers.xorSemigroup.fold(1, 2, 1));
    }

    private static void assertInt(int x, Integer y) {
        assertEquals(Integer.valueOf(x), y);
    }

}
