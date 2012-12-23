package org.highj.function;

import org.highj.data.tuple.T0;
import org.highj.data.collection.List;
import org.highj.function.repo.Strings;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class F0Test {
    @Test
    public void _1Test() throws Exception {
       F0<String> thunk = new F0<String>() {
           @Override
           public String $() {
               return "bla";
           }
       };
        assertEquals("bla", thunk._1());
    }

    @Test
    public void toF1Test() throws Exception {
        F0<String> thunk = new F0<String>() {
            @Override
            public String $() {
                return "bla";
            }
        };
        F1<T0, String> fn = thunk.toF1();
        assertEquals("bla", fn.$(T0.unit));
    }

    @Test
    public void fromF1Test() throws Exception {
        F1<T0, String> fn = new F1<T0,String>() {
            @Override
            public String $(T0 unit) {
                return "bla";
            }
        };
        F0<String> thunk = F0.fromF1(fn);
        assertEquals("bla", thunk.$());
    }

    @Test
    public void constantTest() throws Exception {
        F0<String> thunk = F0.constant("bla");
        assertEquals("bla", thunk.$());
    }

    @Test
    public void errorMsgTest() throws Exception {
        F0<String> thunk = F0.error("bla");
        try {
            thunk.$();
        } catch (RuntimeException ex) {
            assertEquals("bla", ex.getMessage());
            return;
        }
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorClassTest() throws Exception {
        F0<String> thunk = F0.error(IllegalArgumentException.class);
        thunk.$();
    }

    @Test
    public void errorClassMsgTest() throws Exception {
        F0<String> thunk = F0.error(IllegalArgumentException.class, "bla");
        try {
            thunk.$();
        } catch (IllegalArgumentException ex) {
            assertEquals("bla", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void lazyF1Test() throws Exception {
       F1<String, Integer> fn = Strings.length;
       F0<Integer> thunk = F0.lazy(fn, "bla");
       assertEquals(Integer.valueOf(3), thunk.$());
    }

    @Test
    public void lazyF2Test() throws Exception {
        F2<Integer, Integer, Integer> fn = new F2<Integer, Integer, Integer>() {
            @Override
            public Integer $(Integer a, Integer b) {
                return a - b;
            }
        };
        F0<Integer> thunk = F0.lazy(fn, 8, 5);
        assertEquals(Integer.valueOf(3), thunk.$());
    }

    @Test
    public void lazyF3Test() throws Exception {
        F3<Integer, Integer, Integer, Integer> fn = new F3<Integer, Integer, Integer, Integer>() {
            @Override
            public Integer $(Integer a, Integer b, Integer c) {
                return a - b - c;
            }
        };
        F0<Integer> thunk = F0.lazy(fn, 12, 5, 4);
        assertEquals(Integer.valueOf(3), thunk.$());
    }

    @Test
    public void memoizeTest() throws Exception {
        final Iterator<String> it = List.of("bla").iterator();
        F0<String> thunk = new F0<String>(){

            @Override
            public String $() {
                return it.next();   //works only one time
            }
        };
        F0<String> memo = thunk.memoized();
        assertFalse(thunk.isMemoizing());
        assertTrue(memo.isMemoizing());
        assertEquals("bla", memo.$());
        assertEquals("bla", memo.$());  //works only if it really memoizes

        F0<String> memo2 = memo.memoized();
        assertTrue(memo == memo2);
    }
}
