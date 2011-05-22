/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

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
    private F<String, Integer> lengthFn;
    
    public MonadAbstractTest() {
    }
  
    @Before
    public void setUp() {
        listMonad = ListMonadPlus.getInstance();
        optionMonad = OptionMonadPlus.getInstance();
        lengthFn = new F<String, Integer>() {
            @Override
            public Integer f(String a) {
                return a.length();
            }
            
        };
    }
    
    @After
    public void tearDown() {
        listMonad = null;
        optionMonad = null;
        lengthFn = null;
    }

    @Test
    public void testSemicolon() {
        //[1,2,3] >> ['a','b'] 
        //-- "ababab"
        _<ListOf,Integer> intList = ListOf.list(1,2,3);
        _<ListOf,String> stringList = ListOf.list("a","b");
        List<String> resultList = ListOf.unwrap(listMonad.semicolon(intList,stringList));
        List<String> expectedList = List.list("a","b","a","b","a","b");
        Show<List<String>> show = Show.listShow(Show.stringShow);
        assertEquals(show.showS(expectedList), show.showS(resultList));
    }

    @Test
    public void testJoin() {
        //join [[1,2,3],[],[4,5],[6]]
        //-- [1,2,3,4,5,6]
        _<ListOf,_<ListOf, Integer>> nestedIntList = ListOf.list(
           ListOf.list(1,2,3),
           ListOf.<Integer>list(),
           ListOf.list(4,5),
           ListOf.list(6)
        );
        List<Integer> resultList = ListOf.unwrap(listMonad.join(nestedIntList));
        List<Integer> expectedList = List.list(1,2,3,4,5,6);
        
        Show<List<Integer>> show = Show.listShow(Show.intShow);
        assertEquals(show.showS(expectedList), show.showS(resultList));
    }

    @Test
    public void testReturnM() {
        //(return 42) :: [Int]
        //-- [42]
        assertEquals("[42]", ListOf.toString(listMonad.returnM(42)));
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
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,3]
        //-- Just [2,4,6]        
        _<OptionOf,_<ListOf,Integer>> result = optionMonad.mapM(fn, ListOf.list(1,2,3));
        assertEquals("[2, 4, 6]", ListOf.toString(OptionOf.get(result)));
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,0,3]
        //-- Nothing
        _<OptionOf,_<ListOf,Integer>> resultEmpty = optionMonad.mapM(fn, ListOf.list(1,2,0,3));
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
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,3]
        //-- Just [2,4,6]        
        _<OptionOf,List<Integer>> result = optionMonad.mapMFlat(fn, List.list(1,2,3));
        assertEquals("[2, 4, 6]", OptionOf.get(result).toCollection().toString());
        //mapM (\x -> if x == 0 then Nothing else Just (2*x)) [1,2,0,3]
        //-- Nothing
        _<OptionOf, List<Integer>> resultEmpty = optionMonad.mapMFlat(fn, List.list(1,2,0,3));
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
        _<ListOf, Unit> result = listMonad.mapM_(fn, ListOf.list(1,2,3));
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
        _<ListOf, Unit> result = listMonad.mapM_Flat(fn, List.list(1,2,3));
        assertEquals(8, ListOf.unwrap(result).length());
        assertEquals(Unit.unit(), ListOf.unwrap(result).head());
        assertEquals(Unit.unit(), ListOf.unwrap(result).last());
    }

    
    @Test
    public void testKleisli() {
        //(\x -> if x == "null" then Nothing else Just (length x)) 
        // >=> (\x -> if x == 0 then Nothing else Just(sqrt $ fromIntegral x)) 
        //...
        F<String,_<OptionOf, Integer>> lengthOptFn = new F<String,_<OptionOf, Integer>>() {
            @Override
            public _<OptionOf, Integer> f(String a) {
                return a == null ? OptionOf.<Integer>none() : OptionOf.some(a.length());
            }
        };
        F<Integer,_<OptionOf, Double>> sqrtOptFn = new F<Integer,_<OptionOf, Double>>() {
            @Override
            public _<OptionOf, Double> f(Integer a) {
                return a == 0 ? OptionOf.<Double>none() : OptionOf.some(Math.sqrt(a));
            }
            
        };
        F<String, _<OptionOf, Double>> lengthSqrtOptFn = optionMonad.kleisli(lengthOptFn, sqrtOptFn);
        //$ "123456789"
        //-- Just 3.0
        assertEquals("3.0", OptionOf.get(lengthSqrtOptFn.f("123456789")).toString());
        //... $ ""
        //-- Nothing
        assertEquals(true, OptionOf.isNone(lengthSqrtOptFn.f("")));
        //... $ "null"
        //-- Nothing
        assertEquals(true, OptionOf.isNone(lengthSqrtOptFn.f(null)));
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

    @Test
    public void testAp() {
        //Just length `ap` Just "12345"
        //-- Just 5
        _<OptionOf, Integer> result = optionMonad.ap(OptionOf.some(lengthFn), OptionOf.some("12345"));
        assertEquals(Integer.valueOf(5), OptionOf.get(result));
        //Nothing `ap` Just "12345"
        //-- Nothing
        _<OptionOf, Integer> resultEmpty1 = optionMonad.ap(OptionOf.<F<String,Integer>>none(), OptionOf.some("12345"));
        assertEquals(true, OptionOf.isNone(resultEmpty1));
        //Just length `ap` Nothing"
        //-- Nothing
        _<OptionOf, Integer> resultEmpty2 = optionMonad.ap(OptionOf.some(lengthFn), OptionOf.<String>none());
        assertEquals(true, OptionOf.isNone(resultEmpty2));
    }

}
