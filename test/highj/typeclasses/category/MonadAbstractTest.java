/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.Unit;
import highj.data.OptionOf;
import fj.F2;
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
    public void testLiftM() {
        //liftM length ["one","two","three"]
        //-- [3,3,5]
        _<ListOf,Integer> result = listMonad.<String,Integer>liftM(lengthFn, ListOf.list("one","two","three"));
        assertEquals("[3, 3, 5]", ListOf.toString(result));
    }

    @Test
    public void testLiftMFn() {
        F<_<ListOf, String>, _<ListOf, Integer>> fn = listMonad.<String,Integer>liftMFn().f(lengthFn);
        //liftM length []
        //-- []
        assertEquals("[]", ListOf.toString(fn.f(ListOf.<String>empty())));
        //liftM length ["one","two","three"]
        //-- [3,3,5]
        assertEquals("[3, 3, 5]", ListOf.toString(fn.f(ListOf.list("one","two","three"))));
    }

      
    @Test
    public void testLiftM2() {
        //liftM2 (\x y -> show x ++ show y) [1,2,3] [True, False]
        //-- ["1True","1False","2True","2False","3True","3False"]
        F<Integer, F<Boolean,String>> fn2 = new F2<Integer,Boolean,String>(){

            @Override
            public String f(Integer a, Boolean b) {
                return "" + a + b;
            }
        
        }.curry();
        _<ListOf,String> result = listMonad.liftM2(fn2, ListOf.list(1,2,3), ListOf.list(true,false));
        assertEquals("[1true, 1false, 2true, 2false, 3true, 3false]", ListOf.toString(result));
    }

    @Test
    public void testLiftM3() {
        //liftM3 (\x y z -> concat[show x,show y,show z]) [1,2,3] [True, False] [0.1,0.2]
        //-- ["1True0.1","1True0.2","1False0.1","1False0.2","2True0.1","2True0.2","2False0.1",
        //-- "2False0.2","3True0.1","3True0.2","3False0.1","3False0.2"]
        F<Integer, F<Boolean,F<Double,String>>> fn3 = new F<Integer, F<Boolean,F<Double,String>>>(){

            @Override
            public F<Boolean, F<Double, String>> f(final Integer a) {
                return new F2<Boolean, Double, String>() {

                    @Override
                    public String f(Boolean b, Double c) {
                        return "" + a + b + c;
                    }
                    
                }.curry();
            }
        };
        _<ListOf,String> result = listMonad.liftM3(fn3, ListOf.list(1,2), ListOf.list(true,false), ListOf.list(0.1, 0.2));
        assertEquals("[1true0.1, 1true0.2, 1false0.1, 1false0.2, 2true0.1, 2true0.2, 2false0.1, 2false0.2]",
                ListOf.toString(result));
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
