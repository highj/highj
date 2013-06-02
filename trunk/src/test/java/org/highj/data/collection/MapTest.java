package org.highj.data.collection;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.data.functions.Integers;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MapTest {

    private final static Map<String,Integer> aMap = Map.<String, Integer>empty().plus("x",3).plus("y",5).plus("z",10);

    @Test
    public void testNarrow() throws Exception {
        __<Map.µ,String, Integer> map = aMap;
        Map<String, Integer> result = Map.narrow(map);
        assertEquals("Map(x->3,y->5,z->10)", result.toString());
        _<__.µ<Map.µ,String>, Integer> map1 = aMap;
        Map<String, Integer> result1 = Map.narrow(map1);
        assertEquals("Map(x->3,y->5,z->10)", result1.toString());
    }

    @Test
    public void test$() throws Exception {
        assertEquals("Just(5)", aMap.apply("y").toString());
        assertEquals("Nothing", aMap.apply("a").toString());
    }

    @Test
    public void testGetOrElse() throws Exception {
        assertEquals("5", aMap.getOrElse("y",42).toString());
        assertEquals("42", aMap.getOrElse("a", 42).toString());
    }

    @Test
    public void testGet() throws Exception {
        assertEquals("5", aMap.get("y").toString());
        try {
            aMap.get("a").toString();
            fail();
        } catch (NoSuchElementException ex) {
            /* expected */
        }
    }

    @Test
    public void testPlusAB() throws Exception {
        assertEquals("Map(a->20)", Map.empty().plus("a", 20).toString());
        assertEquals("Map(x->13,y->5,z->10)", aMap.plus("x", 13).toString());
        assertEquals("Map(a->20,x->3,y->5,z->10)", aMap.plus("a", 20).toString());
    }

    @Test
    public void testPlusT2() throws Exception {
        T2<String, Integer> t2 = Tuple.of("a",20);
        assertEquals("Map(a->20)", Map.<String,Integer>empty().plus(t2).toString());
        assertEquals("Map(a->20,x->3,y->5,z->10)", aMap.plus(t2).toString());
    }

    @Test
    public void testMinus1() throws Exception {
        assertEquals("Map()", Map.empty().minus("a").toString());
        assertEquals("Map(x->3,y->5,z->10)", aMap.minus("a").toString());
        assertEquals("Map(x->3,z->10)", aMap.minus("y").toString());
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(Map.empty().isEmpty());
        assertTrue(! aMap.isEmpty());
        assertTrue(aMap.minus("y", "x", "z").isEmpty());
    }


    @Test
    public void testEmpty() throws Exception {
        assertEquals("Map()", Map.empty().toString());
    }

    @Test
    public void testOf0() throws Exception {
        assertEquals("Map()", Map.of().toString());
    }

    @Test
    public void testOfT2() throws Exception {
        assertEquals("Map(x->3,y->5,z->10)",
                Map.of(Tuple.of("y",5), Tuple.of("z",10), Tuple.of("x",3)).toString());
    }

    @Test
    public void testOfIterable() throws Exception {
        List<T2<String,Integer>> list = List.of(Tuple.of("y", 5), Tuple.of("z", 10), Tuple.of("x", 3));
        assertEquals("Map(x->3,y->5,z->10)",
                Map.of(list).toString());
    }

    @Test
    public void testPlusT2s() throws Exception {
        assertEquals("Map(a->1,x->13,y->5,z->10)",
                aMap.plus(Tuple.of("a", 1), Tuple.of("x", 13)).toString());
    }

    @Test
    public void testMinusN() throws Exception {
        assertEquals("Map(x->3,z->10)", aMap.minus("a","y","b").toString());
    }

    @Test
    public void testPlusIterable() throws Exception {
        List<T2<String,Integer>> list = List.of(Tuple.of("a",1), Tuple.of("x",13));
        assertEquals("Map(a->1,x->13,y->5,z->10)",
                aMap.plus(list).toString());

    }

    @Test
    public void testMinusIterable() throws Exception {
        List<String> list = List.of("a", "y");
        assertEquals("Map(x->3,z->10)",
                aMap.minus(list).toString());
    }


    @Test
    public void testSize() throws Exception {
        assertEquals(0, Map.empty().size());
        assertEquals(3, aMap.size());
    }


    @Test
    public void testF1() throws Exception {
        Function<String, Maybe<Integer>> f1 = aMap.apply();
        assertEquals("Just(5)", f1.apply("y").toString());
        assertEquals("Nothing", f1.apply("a").toString());
    }


    @Test
    public void testIterator() throws Exception {
        assertTrue(! Map.empty().iterator().hasNext());

        Iterator<T2<String,Integer>> it = aMap.iterator();
        assertEquals("(x,3)", it.next().toString());
        assertEquals("(y,5)", it.next().toString());
        assertEquals("(z,10)", it.next().toString());
        assertTrue(! it.hasNext());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Map()", Map.empty().toString());
        assertEquals("Map(x->3,y->5,z->10)", aMap.toString());
    }

    @Test
    public void testKeys() throws Exception {
       assertEquals("Set()", Map.empty().keys().toString());
       assertEquals("Set(x,y,z)", aMap.keys().toString());
    }

    @Test
    public void testValues() throws Exception {
        assertEquals("Set()", Map.empty().values().toString());
        assertEquals("Set(3,5,10)", aMap.values().toString());
    }

    @Test
    public void testMap() throws Exception {
        assertEquals("Map(x->-3,y->-5,z->-10)", aMap.map(Integers.negate).toString());
    }


}