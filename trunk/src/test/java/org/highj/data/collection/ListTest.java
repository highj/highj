package org.highj.data.collection;

import org.highj._;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.function.Integers;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ListTest {
    @Test
    public void testNarrow() throws Exception {
        _<List.µ, Integer> list_ = List.of(1, 2, 3);
        List<Integer> list = List.narrow(list_);
        assertEquals("List(1,2,3)", list.toString());
        List<Integer> listByF1 = List.<Integer>narrow().$(list_);
        assertEquals("List(1,2,3)", listByF1.toString());
    }

    @Test
    public void testContravariant() throws Exception {
        List<Integer> intList = List.of(1, 2, 3);
        List<Number> numList = List.<Number, Integer>contravariant(intList);
        assertEquals("List(1,2,3)", numList.toString());
    }

    @Test
    public void testEmpty() throws Exception {
        List<Integer> empty = List.empty();
        assertEquals("List()", empty.toString());
    }

    @Test
    public void testOf() throws Exception {

    }


    @Test
    public void testCons() throws Exception {

    }

    @Test
    public void testFromStream() throws Exception {

    }


    @Test
    public void testMinus() throws Exception {

    }

    @Test
    public void testMinusAll() throws Exception {

    }

    @Test
    public void testNil() throws Exception {
        List<Integer> empty = List.Nil();
        assertEquals("List()", empty.toString());
    }

    @Test
    public void testMaybeHead() throws Exception {

    }

    @Test
    public void testMaybeTail() throws Exception {

    }

    @Test
    public void testHead() throws Exception {

    }

    @Test
    public void testTail() throws Exception {

    }

    @Test
    public void testLazyTail() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(List.of().isEmpty());
        assertTrue(!List.of(1, 2, 4).isEmpty());
    }

    @Test
    public void test$() throws Exception {
        List<Integer> list = List.of(10, 20, 30, 40);
        assertEquals(10, list.$(0).intValue());
        assertEquals(40, list.$(3).intValue());
        try {
            list.$(-1);
            fail();
        } catch (IndexOutOfBoundsException ex) {
            /* expected*/
        }
        try {
            list.$(4);
            fail();
        } catch (IndexOutOfBoundsException ex) {
            /* expected*/
        }
    }

    @Test
    public void testContains() throws Exception {
        List<Integer> list = List.of(2, 8, 8, 4, 6, -2, 10);
        assertTrue(list.contains(4));
        assertTrue(!list.contains(5));
        assertTrue(list.contains(Integers.negative));
        assertTrue(!list.contains(Integers.odd));
    }

    @Test
    public void testCount() throws Exception {
        List<Integer> list = List.of(2, 3, 3, 2, 3, 4, 3, 3);
        assertEquals(5, list.count(3));
        assertEquals(3, list.count(Integers.even));
    }

    @Test
    public void testTake() throws Exception {
        List<Integer> infiniteList = List.range(1);
        assertEquals("List()", infiniteList.take(-5).toString());
        assertEquals("List()", infiniteList.take(0).toString());
        assertEquals("List(1)", infiniteList.take(1).toString());
        assertEquals("List(1,2,3,4,5)", infiniteList.take(5).toString());
        List<Integer> finiteList = List.range(1, 1, 4);
        assertEquals("List()", finiteList.take(-5).toString());
        assertEquals("List()", finiteList.take(0).toString());
        assertEquals("List(1)", finiteList.take(1).toString());
        assertEquals("List(1,2,3,4)", finiteList.take(4).toString());
        assertEquals("List(1,2,3,4)", finiteList.take(5).toString());
    }

    @Test
    public void testTakeWhile() throws Exception {

    }

    @Test
    public void testDrop() throws Exception {

    }

    @Test
    public void testDropWhile() throws Exception {

    }

    @Test
    public void testSize() throws Exception {
        assertEquals(0, List.of().size());
        assertEquals(1, List.of(1).size());
        assertEquals(5, List.of(1, 2, 3, 4, 5).size());
    }

    @Test
    public void testIterator() throws Exception {
         StringBuilder sb = new StringBuilder();
        for(int i : List.of(7,2,5,9)) {
            sb.append(i);
        }
        assertEquals("7259", sb.toString());
    }

    @Test
    public void testToJList() throws Exception {
        assertEquals("[1, 2, 3, 4, 5]", List.of(1, 2, 3, 4, 5).toJList().toString());
    }


    @Test
    public void testRange() throws Exception {
        assertEquals("List(1,2,3)", List.range(1).take(3).toString());
        assertEquals("List(20,30,40)", List.range(20, 10).take(3).toString());
        assertEquals("List(20,30,40)", List.range(20, 10, 40).toString());
        assertEquals("List(20,30,40)", List.range(20, 10, 43).toString());
        assertEquals("List(20,18,16,14)", List.range(20, -2, 14).toString());
        assertEquals("List(20,18,16,14)", List.range(20, -2, 13).toString());
    }

    @Test
    public void testCycle() throws Exception {
        assertEquals("List(1,2,3,1,2,3,1,2,3)", List.cycle(1, 2, 3).take(9).toString());
    }

    @Test
    public void testRepeat() throws Exception {
        assertEquals("List(2,2,2,2,2)", List.repeat(2).take(5).toString());
    }

    @Test
    public void testReplicate() throws Exception {
        assertEquals("List()", List.replicate(-5, 2).toString());
        assertEquals("List()", List.replicate(0, 2).toString());
        assertEquals("List(2,2,2,2,2)", List.replicate(5, 2).toString());
    }

    @Test
    public void testAppend() throws Exception {
        assertEquals("List(2,5,3,8,9)", List.append(List.of(2, 5), List.of(3, 8, 9)).toString());
        assertEquals("List(2,5)", List.append(List.of(2, 5), List.<Integer>of()).toString());
        assertEquals("List(3,8,9)", List.append(List.<Integer>of(), List.of(3, 8, 9)).toString());
        assertEquals("List()", List.append(List.<Integer>of(), List.<Integer>of()).toString());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("List()", List.of().toString());
        assertEquals("List(2,5,3,8)", List.of(2, 5, 3, 8).toString());
    }

    @Test
    public void testMap() throws Exception {
        List<Integer> list = List.of(2, 5, 3, 8);
        assertEquals("List(true,false,false,true)", list.map(Integers.even).toString());
    }

    @Test
    public void testFilter() throws Exception {
        List<Integer> list = List.of(2, 5, 3, 8);
        assertEquals("List(2,8)", list.filter(Integers.even).toString());
        assertEquals("List()", list.filter(Integers.negative).toString());
        assertEquals("List(2,5,3,8)", list.filter(Integers.positive).toString());
    }

    @Test
    public void testReverse() throws Exception {
        assertEquals("List()", List.of().reverse().toString());
        assertEquals("List(3,2,1)", List.of(1, 2, 3).reverse().toString());
    }

    @Test
    public void testFoldr() throws Exception {

    }

    @Test
    public void testFoldl() throws Exception {

    }

    @Test
    public void testZip() throws Exception {
        List<Integer> intList = List.of(1,5,7,3);
        List<String> stringList = List.of("one","five","seven","three","blubb");
        List<Boolean> boolList = List.of(true, false, false, false);
        assertEquals("List((1,one),(5,five),(7,seven),(3,three))", List.zip(intList,stringList).toString());
        assertEquals("List((1,one,true),(5,five,false),(7,seven,false),(3,three,false))", List.zip(intList,stringList,boolList).toString());
    }

    @Test
    public void testZipWith() throws Exception {

    }

    @Test
    public void testUnzip() throws Exception {
        List<T2<Integer,String>> list = List.of(Tuple.of(1,"one"),Tuple.of(5,"five"),Tuple.of(7,"seven"),Tuple.of(3,"three"));
        assertEquals("(List(1,5,7,3),List(one,five,seven,three))", List.unzip(list).toString());
    }

    @Test
    public void testJoin() throws Exception {
        List<List<Integer>> list = List.of(List.of(1, 2), List.<Integer>of(), List.of(30, 40, 50), List.of(600));
        assertEquals("List(1,2,30,40,50,600)", List.join(list).toString());
        _<List.µ, _<List.µ, Integer>> list_ = List.<_<List.µ, Integer>>of(List.of(1, 2), List.<Integer>of(), List.of(30, 40, 50), List.of(600));
        assertEquals("List(1,2,30,40,50,600)", List.monad.join(list_).toString());
    }

    @Test
    public void testIntersperse() throws Exception {
        assertEquals("List(1,42,3,42,5,42,7)", List.of(1, 3, 5, 7).intersperse(42).toString());
        //test laziness
        assertEquals("List(1,42,2,42,3)", List.range(1).intersperse(42).take(5).toString());
    }
}
