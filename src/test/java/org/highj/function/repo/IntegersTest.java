package org.highj.function.repo;

import org.highj.function.Integers;
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
    public void negateTest() {
        assertInt(0, Integers.negate.apply(0));
        assertInt(-42, Integers.negate.apply(42));
        assertInt(42, Integers.negate.apply(-42));
    }

    @Test
    public void sqrTest() {
        assertInt(0, Integers.sqr.apply(0));
        assertInt(1764, Integers.sqr.apply(42));
        assertInt(1764, Integers.sqr.apply(-42));
    }

    @Test
    public void notTest() {
        assertInt(-1, Integers.not.apply(0));
        assertInt(~42, Integers.not.apply(42));
        assertInt(~-42, Integers.not.apply(-42));
    }

    @Test
    public void succTest() {
        assertInt(0, Integers.succ.apply(-1));
        assertInt(1, Integers.succ.apply(0));
        assertInt(43, Integers.succ.apply(42));
    }

    @Test
    public void predTest() {
        assertInt(-1, Integers.pred.apply(0));
        assertInt(0, Integers.pred.apply(1));
        assertInt(42, Integers.pred.apply(43));
    }

    @Test
    public void powTest() {
        assertInt(81, Integers.pow.apply(3).apply(4));
        assertInt(100, Integers.pow.apply(10).apply(2));
        assertInt(125, Integers.pow.apply(5).apply(3));
        assertInt(256, Integers.pow.apply(2).apply(8));
    }

    @Test
    public void gcdTest() {
        assertInt(1, Integers.gcd.apply(257).apply(111));
        assertInt(7, Integers.gcd.apply(35).apply(-42));
        assertInt(6, Integers.gcd.apply(-30).apply(66));
    }

    @Test
    public void negativeTest() {
        assertTrue(!Integers.negative.test(0));
        assertTrue(!Integers.negative.test(42));
        assertTrue(Integers.negative.test(-42));
    }

    @Test
    public void positiveTest() {
        assertTrue(!Integers.positive.test(0));
        assertTrue(Integers.positive.test(42));
        assertTrue(!Integers.positive.test(-42));
    }

    @Test
    public void zeroTest() {
        assertTrue(Integers.zero.test(0));
        assertTrue(!Integers.zero.test(42));
        assertTrue(!Integers.zero.test(-42));
    }

    @Test
    public void evenTest() {
        assertTrue(Integers.even.test(0));
        assertTrue(Integers.even.test(42));
        assertTrue(Integers.even.test(-42));
        assertTrue(!Integers.even.test(43));
        assertTrue(!Integers.even.test(-43));
    }

    @Test
    public void oddTest() {
        assertTrue(!Integers.odd.test(0));
        assertTrue(!Integers.odd.test(42));
        assertTrue(!Integers.odd.test(-42));
        assertTrue(Integers.odd.test(43));
        assertTrue(Integers.odd.test(-43));
    }

    @Test
    public void additiveGroupTest() {
        assertInt(0, Integers.additiveGroup.identity());
        assertInt(40, Integers.additiveGroup.apply(17, 23));
        assertInt(-5, Integers.additiveGroup.inverse(5));
        assertInt(5, Integers.additiveGroup.inverse(-5));
        assertInt(0, Integers.additiveGroup.inverse(0));
        assertInt(10, Integers.additiveGroup.fold(1, 2, 3, 4));
    }

    @Test
    public void multiplicativeMonoidTest() {
        assertInt(1, Integers.multiplicativeMonoid.identity());
        assertInt(391, Integers.multiplicativeMonoid.apply(17, 23));
        assertInt(24, Integers.multiplicativeMonoid.fold(1, 2, 3, 4));
    }

    @Test
    public void minMonoidTest() {
        assertInt(Integer.MAX_VALUE, Integers.minMonoid.identity());
        assertInt(17, Integers.minMonoid.apply(17, 23));
        assertInt(-3, Integers.minMonoid.fold(2, -3, 1, 4));
    }

    @Test
    public void maxMonoidTest() {
        assertInt(Integer.MIN_VALUE, Integers.maxMonoid.identity());
        assertInt(23, Integers.maxMonoid.apply(17, 23));
        assertInt(4, Integers.maxMonoid.fold(2, -3, 4, 1));
    }

    @Test
    public void xorSemigroupTest() {
        assertInt(6, Integers.xorSemigroup.apply(17, 23));
        assertInt(2, Integers.xorSemigroup.fold(1, 2, 1));
    }

    private static void assertInt(int x, Integer y) {
        assertEquals(Integer.valueOf(x), y);
    }

}
