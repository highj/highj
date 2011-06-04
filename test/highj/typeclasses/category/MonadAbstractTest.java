/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F2;
import fj.Unit;
import highj.data.OptionOf;
import fj.Show;
import highj.data.ListMonadPlus;
import fj.F;
import fj.data.List;
import highj._;
import highj.data.ListOf;
import highj.data.OptionMonadPlus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Back-to-back test with Haskell 
 * @author DGronau
 */
public class MonadAbstractTest {
    
    private Monad<ListOf> listMonad;
    private Monad<OptionOf> optionMonad;
 
    @Before
    public void setUp() {
        listMonad = ListMonadPlus.getInstance();
        optionMonad = OptionMonadPlus.getInstance();
    }
    
    @After
    public void tearDown() {
        listMonad = null;
        optionMonad = null;
    }

    @Test
    public void testSequence() {
        //sequence [[1,2],[3],[4,5,6]]
        //-- [[1,3,4],[1,3,5],[1,3,6],[2,3,4],[2,3,5],[2,3,6]] 
        final _<ListOf,_<ListOf, Integer>> list = ListOf.list(
                ListOf.list(1,2),
                ListOf.list(3),
                ListOf.list(4,5,6)
         );
        _<ListOf,_<ListOf, Integer>> sequencedListWrapped = listMonad.sequence(list);
        List<List<Integer>> sequencedList = ListOf.unwrap(sequencedListWrapped).map(
                 new F<_<ListOf, Integer>,List<Integer>>(){
            @Override
            public List<Integer> f(_<ListOf, Integer> a) {
                return ListOf.unwrap(a);
            }
        });
        List<List<Integer>> expectedList = List.list(
            List.list(1,3,4),List.list(1,3,5),List.list(1,3,6),    
            List.list(2,3,4),List.list(2,3,5),List.list(2,3,6)    
        );
        Show<List<List<Integer>>> show = Show.listShow(Show.listShow(Show.intShow));
        assertEquals(show.showS(expectedList), show.showS(sequencedList));
    }

    @Test
    public void testMapM() {
        F<Integer,_<OptionOf,Integer>> fn = new F<Integer,_<OptionOf,Integer>>() {
            @Override
            public _<OptionOf, Integer> f(Integer a) {
                return a.equals(0) ? OptionOf.<Integer>none() : OptionOf.some(2*a);
            }
        };
        F<_<ListOf,Integer>,_<OptionOf,_<ListOf,Integer>>> mappedFn = optionMonad.mapM(fn);
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,3]
        //-- Just [2,4,6]        
        _<OptionOf,_<ListOf,Integer>> result = mappedFn.f(ListOf.list(1,2,3));
        assertEquals("[2, 4, 6]", ListOf.toString(OptionOf.get(result)));
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,0,3]
        //-- Nothing
        _<OptionOf,_<ListOf,Integer>> resultEmpty = mappedFn.f(ListOf.list(1,2,0,3));
        assertEquals(true, OptionOf.isNone(resultEmpty));
    }

      
    @Test
    public void testMapMFlat() {
        F<Integer,_<OptionOf,Integer>> fn = new F<Integer,_<OptionOf,Integer>>() {
            @Override
            public _<OptionOf, Integer> f(Integer a) {
                return a.equals(0) ? OptionOf.<Integer>none() : OptionOf.some(2*a);
            }
        };
        F<List<Integer>,_<OptionOf,List<Integer>>> mappedFn = optionMonad.mapMFlat(fn);
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,3]
        //-- Just [2,4,6]        
        _<OptionOf,List<Integer>> result = mappedFn.f(List.list(1,2,3));
        assertEquals("[2, 4, 6]", OptionOf.get(result).toCollection().toString());
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,0,3]
        //-- Nothing
        _<OptionOf, List<Integer>> resultEmpty = mappedFn.f(List.list(1,2,0,3));
        assertEquals(true, OptionOf.isNone(resultEmpty));
    }

    @Test
    public void testMapM_() {
        //mapM_ (replicate 2) [1,2,3]
        //-- [(),(),(),(),(),(),(),()]        
        F<Integer,_<ListOf, Integer>> fn = new F<Integer,_<ListOf, Integer>> () {
            @Override
            public _<ListOf, Integer> f(Integer a) {
                return ListOf.list(a,a);
            }
        };
        _<ListOf, Unit> result = listMonad.mapM_(fn).f(ListOf.list(1,2,3));
        assertEquals(8, ListOf.unwrap(result).length());
        assertEquals(Unit.unit(), ListOf.unwrap(result).head());
        assertEquals(Unit.unit(), ListOf.unwrap(result).last());
    }

    @Test
    public void testMapM_Flat() {
        //mapM_ (replicate 2) [1,2,3]
        //-- [(),(),(),(),(),(),(),()]        
        F<Integer,_<ListOf, Integer>> fn = new F<Integer,_<ListOf, Integer>> () {
            @Override
            public _<ListOf, Integer> f(Integer a) {
                return ListOf.list(a,a);
            }
        };
        _<ListOf, Unit> result = listMonad.mapM_Flat(fn).f(List.list(1,2,3));
        assertEquals(8, ListOf.unwrap(result).length());
        assertEquals(Unit.unit(), ListOf.unwrap(result).head());
        assertEquals(Unit.unit(), ListOf.unwrap(result).last());
    }
   
    @Test
    public void testSequenceFlat() {
        //sequence [[1,2],[3],[4,5,6]]
        //-- [[1,3,4],[1,3,5],[1,3,6],[2,3,4],[2,3,5],[2,3,6]] 
        final List<_<ListOf, Integer>> list = List.list(
                ListOf.list(1,2),
                ListOf.list(3),
                ListOf.list(4,5,6)
         );
        _<ListOf,List<Integer>> sequencedListWrapped = listMonad.sequenceFlat(list);
        List<List<Integer>> sequencedList = ListOf.unwrap(sequencedListWrapped);
        List<List<Integer>> expectedList = List.list(
            List.list(1,3,4),List.list(1,3,5),List.list(1,3,6),    
            List.list(2,3,4),List.list(2,3,5),List.list(2,3,6)    
        );
        Show<List<List<Integer>>> show = Show.listShow(Show.listShow(Show.intShow));
        assertEquals(show.showS(expectedList), show.showS(sequencedList));
    }

    /* TODO: move to OptionMonadTest or so later
    @Test
    public void testAp() {
        //Just length `ap` Just "12345"
        //-- Just 5
        _<OptionOf, Integer> result = optionMonad.ap(OptionOf.some(Strings.length), OptionOf.some("12345"));
        assertEquals(Integer.valueOf(5), OptionOf.get(result));
        //Nothing `ap` Just "12345"
        //-- Nothing
        _<OptionOf, Integer> resultEmpty1 = optionMonad.ap(OptionOf.<F<String,Integer>>none(), OptionOf.some("12345"));
        assertEquals(true, OptionOf.isNone(resultEmpty1));
        //Just length `ap` Nothing"
        //-- Nothing
        _<OptionOf, Integer> resultEmpty2 = optionMonad.ap(OptionOf.some(Strings.length), OptionOf.<String>none());
        assertEquals(true, OptionOf.isNone(resultEmpty2));
    }*/

    @Test
    public void testFoldM() {
        //foldM (\x y -> [0,x + length y]) 0 ["one","two","three"]
        //-- [0,5,0,8,0,5,0,11]
        _<ListOf,String> list = ListOf.list("one","two","three");
        F<Integer, F<String, _<ListOf,Integer>>> listFn = new F2<Integer, String,_<ListOf,Integer>>(){
            @Override
            public _<ListOf, Integer> f(Integer a, String b) {
                return ListOf.list(0, a + b.length());
            }
        }.curry();
        _<ListOf,Integer> result = listMonad.foldM(listFn).f(0, list);
        assertEquals("[0, 5, 0, 8, 0, 5, 0, 11]", ListOf.toString(result));
    }
    
   @Test
    public void testFoldMFlat() {
        //foldM (\x y -> [x,x + length y]) 0 ["one","two","three"]
        //-- [0,5,3,8,3,8,6,11] 
       List<String> list = List.list("one","two","three");
        F2<Integer, String,_<ListOf,Integer>> listFn = new F2<Integer, String,_<ListOf,Integer>>(){
            @Override
            public _<ListOf, Integer> f(Integer a, String b) {
                return ListOf.list(a, a + b.length());
            }
        };
        _<ListOf,Integer> result = listMonad.foldMFlat(listFn).f(0, list);
        assertEquals("[0, 5, 3, 8, 3, 8, 6, 11]", ListOf.toString(result));
    }    
}
