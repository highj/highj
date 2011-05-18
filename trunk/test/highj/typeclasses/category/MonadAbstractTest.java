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
    
    private ListMonadPlus monad;
    
    public MonadAbstractTest() {
    }
  
    @Before
    public void setUp() {
        monad = ListMonadPlus.getInstance();
    }
    
    @After
    public void tearDown() {
        monad = null;
    }

    @Test
    public void testSemicolon() {
        _<ListOf,Integer> intList = ListOf.wrap(List.list(1,2,3));
        _<ListOf,String> stringList = ListOf.wrap(List.list("a","b"));
        List<String> resultList = ListOf.unwrap(monad.semicolon(intList,stringList));
        List<String> expectedList = List.list("a","b","a","b","a","b");
        Show<List<String>> show = Show.listShow(Show.stringShow);
        assertEquals(show.showS(expectedList), show.showS(resultList));
    }

    @Test
    public void testJoin() {
        _<ListOf,_<ListOf, Integer>> nestedIntList = ListOf.wrap(List.list(
           ListOf.wrap(List.list(1,2,3)),
           ListOf.wrap(List.<Integer>list()),
           ListOf.wrap(List.list(4,5)),
           ListOf.wrap(List.list(6))
        ));
        List<Integer> resultList = ListOf.unwrap(monad.join(nestedIntList));
        List<Integer> expectedList = List.list(1,2,3,4,5,6);
        
        Show<List<Integer>> show = Show.listShow(Show.intShow);
        assertEquals(show.showS(expectedList), show.showS(resultList));
    }

     /* 
    @Test
    public void testLiftM() {
    }

    @Test
    public void testLiftM2() {
    }

    @Test
    public void testLiftM3() {
    }

    @Test
    public void testReturnM() {
    }*/

    @Test
    public void testSequence() {
        _<ListOf,_<ListOf, Integer>> list = ListOf.wrap(List.list(
                ListOf.wrap(List.list(1,2)),
                ListOf.wrap(List.list(3)),
                ListOf.wrap(List.list(4,5,6))
         ));
        _<ListOf,_<ListOf, Integer>> sequencedListWrapped = monad.sequence(list);
        List<List<Integer>> sequencedList = ListOf.unwrap(sequencedListWrapped).map(
                 new F<_<ListOf, Integer>,List<Integer>>(){
            @Override
            public List<Integer> f(_<ListOf, Integer> a) {
                return ListOf.unwrap(a);
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
    }

    @Test
    public void testMapMFlat() {
    }

    @Test
    public void testMapM_() {
    }

    @Test
    public void testMapM_Flat() {
    }

    @Test
    public void testKleisli() {
    }

    @Test
    public void testSequenceFlat() {
    }

    @Test
    public void testAp() {
    }*/

  
}
