/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.Show;
import highj.data.ListMonadPlus;
import fj.F;
import fj.data.List;
import highj._;
import highj.data.ListOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class MonadAbstractTest {
    
    public MonadAbstractTest() {
    }
  
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /*@Test
    public void testBind() {
        System.out.println("bind");
        _<Ctor, A> nestedA = null;
        F<A, _<Ctor, B>> fn = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.bind(nestedA, fn);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSemicolon() {
        System.out.println("semicolon");
        _<Ctor, A> nestedA = null;
        _<Ctor, B> nestedB = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.semicolon(nestedA, nestedB);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testJoin() {
        System.out.println("join");
        _<Ctor, _<Ctor, A>> nestedNestedA = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.join(nestedNestedA);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLiftM() {
        System.out.println("liftM");
        F<A, B> fn = null;
        _<Ctor, A> nestedA = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.liftM(fn, nestedA);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLiftM2() {
        System.out.println("liftM2");
        F<A, F<B, C>> f = null;
        _<Ctor, A> ta = null;
        _<Ctor, B> tb = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.liftM2(f, ta, tb);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLiftM3() {
        System.out.println("liftM3");
        F<A, F<B, F<C, D>>> f = null;
        _<Ctor, A> ta = null;
        _<Ctor, B> tb = null;
        _<Ctor, C> tc = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.liftM3(f, ta, tb, tc);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testReturnM() {
        System.out.println("returnM");
        Object a = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.returnM(a);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }*/

    @Test
    public void testSequence() {
        final Monad<ListOf> monad = ListMonadPlus.getInstance();
        final ListOf listOf = ListOf.getInstance();
        _<ListOf,Integer> list12 = listOf.wrap(List.list(1,2));
        _<ListOf,Integer> list3 = listOf.wrap(List.list(3));
        _<ListOf,Integer> list456 = listOf.wrap(List.list(4,5,6));
        _<ListOf,_<ListOf, Integer>> list = listOf.wrap(List.list(list12,list3,list456));
        _<ListOf,_<ListOf, Integer>> sequencedListWrapped = monad.sequence(list);
        List<List<Integer>> sequencedList = listOf.unwrap(sequencedListWrapped).map(
                 new F<_<ListOf, Integer>,List<Integer>>(){
            @Override
            public List<Integer> f(_<ListOf, Integer> a) {
                return listOf.unwrap(a);
            }
        });
        //Haskell gives [[1,3,4],[1,3,5],[1,3,6],[2,3,4],[2,3,5],[2,3,6]]
        List<List<Integer>> expectedList = List.list(
            List.list(1,3,4),List.list(1,3,5),List.list(1,3,6),    
            List.list(2,3,4),List.list(2,3,5),List.list(2,3,6)    
        );
        Show<List<List<Integer>>> show = Show.listShow(Show.listShow(Show.intShow));
        assertEquals(show.showS(expectedList), show.showS(sequencedList));
    }

    /*@Test
    public void testMapM() {
        System.out.println("mapM");
        F<A, _<Ctor, B>> fn = null;
        _<ListOf, A> list = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.mapM(fn, list);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testMapMFlat() {
        System.out.println("mapMFlat");
        F<A, _<Ctor, B>> fn = null;
        List<A> list = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.mapMFlat(fn, list);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testKleisli() {
        System.out.println("kleisli");
        F<A, _<Ctor, B>> f = null;
        F<B, _<Ctor, C>> g = null;
        MonadAbstract instance = new MonadAbstractImpl();
        F expResult = null;
        F result = instance.kleisli(f, g);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSequenceFlat() {
        System.out.println("sequenceFlat");
        List<_<Ctor, A>> list = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.sequenceFlat(list);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAp() {
        System.out.println("ap");
        _<Ctor, F<A, B>> nestedFn = null;
        _<Ctor, A> nestedA = null;
        MonadAbstract instance = new MonadAbstractImpl();
        _ expResult = null;
        _ result = instance.ap(nestedFn, nestedA);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }*/

  
}
