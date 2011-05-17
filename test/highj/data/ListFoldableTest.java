/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F2;
import fj.F;
import fj.Monoid;
import fj.data.List;
import highj._;
import highj.typeclasses.structural.Foldable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dgronau
 */
public class ListFoldableTest {
    
    private ListOf listOf;
    private Foldable<ListOf> foldable;
    
    @Before
    public void setUp() {
        listOf = ListOf.getInstance();
        foldable = ListFoldable.getInstance();
    }
    
    @After
    public void tearDown() {
        listOf = null;
        foldable = null;
    }

    /**
     * Test of foldr method, of class ListFoldable.
     */
    @Test
    public void testFoldr() {
        F2<String, Integer, Integer> fn = new F2<String,Integer,Integer>() {
            @Override
            public Integer f(String a, Integer b) {
                return a.length() + 10 * b;
            }
            
        };
        _<ListOf,String> list = listOf.wrap(List.list("one","two","three","four"));
        
        //then length of the individual strings "backwards"
        int lengths = foldable.foldr(fn, 0, list); 
        assertEquals(4533, lengths);
        //curried version of method
        int lengthsCurried = foldable.foldr(fn.curry(), 0, list); 
        assertEquals(4533, lengthsCurried);
    }

    /**
     * Test of foldl method, of class ListFoldable.
     */
    @Test
    public void testFoldl() {
        F2<Integer, String, Integer> fn = new F2<Integer, String, Integer>() {
            @Override
            public Integer f(Integer a, String b) {
                return 10*a + b.length();
            }
            
        };
        _<ListOf,String> list = listOf.wrap(List.list("one","two","three","four"));
        
        //then length of the individual strings "forward"
        int lengths = foldable.foldl(fn, 0, list); 
        assertEquals(3354, lengths);
        //curried version of method
        int lengthsCurried = foldable.foldl(fn.curry(), 0, list); 
        assertEquals(3354, lengthsCurried);
    }
    
    @Test
    public void testFold() {
        _<ListOf,String> list = listOf.wrap(List.list("one","two","three","four"));
        String foldedString = foldable.fold(Monoid.stringMonoid, list);
        assertEquals("onetwothreefour", foldedString);
    }
    
    @Test
    public void testFoldMap() {
        _<ListOf,String> list = listOf.wrap(List.list("one","two","three","four"));
        F<String,Integer> fn = new F<String,Integer>() {
            @Override
            public Integer f(String a) {
                return a.length();
            }
            
        };
        int productOfStringLength = foldable.foldMap(Monoid.intMultiplicationMonoid, fn, list);
        assertEquals(3*3*5*4, productOfStringLength);
    }
    
    @Test public void testFoldr1() {
        _<ListOf,Integer> list = listOf.wrap(List.list(1, 10, 100, 1000));
        F2<Integer, Integer, Integer> fn = new F2<Integer, Integer, Integer>() {
            @Override
            public Integer f(Integer a, Integer b) {
                return b - a;
            }
        };
        int differenceBackwards = foldable.foldr1(fn, list);
        assertEquals(1000-100-10-1, differenceBackwards);
        //curried version of method
        int differenceBackwardsCurried = foldable.foldr1(fn.curry(), list);
        assertEquals(1000-100-10-1, differenceBackwardsCurried);
    }

    @Test public void testFoldl1() {
        _<ListOf,Integer> list = listOf.wrap(List.list(1000, 100, 10, 1));
        F2<Integer, Integer, Integer> fn = new F2<Integer, Integer, Integer>() {
            @Override
            public Integer f(Integer a, Integer b) {
                return a - b;
            }
        };
        int differenceForward = foldable.foldl1(fn, list);
        assertEquals(1000-100-10-1, differenceForward);
        //curried version of method
        int differenceForwardCurried = foldable.foldl1(fn.curry(), list);
        assertEquals(1000-100-10-1, differenceForwardCurried);
    }

}
