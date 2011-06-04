/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;
import fj.Show;
import fj.data.List;
import highj.data.OptionMonadPlus;
import highj.data.ListMonadPlus;
import highj.data.OptionOf;
import highj.data.ListOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class BindAbstractTest {
    
    private Bind<ListOf> listBind;
    private Bind<OptionOf> optionBind;
    
    @Before
    public void setUp() {
        listBind = ListMonadPlus.getInstance();
        optionBind = OptionMonadPlus.getInstance();
    }
    
    @After
    public void tearDown() {
        listBind = null;
        optionBind = null;
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
        List<Integer> resultList = ListOf.unwrap(listBind.join(nestedIntList));
        List<Integer> expectedList = List.list(1,2,3,4,5,6);
        
        Show<List<Integer>> show = Show.listShow(Show.intShow);
        assertEquals(show.showS(expectedList), show.showS(resultList));
    }
      
    @Test
    public void testSemicolon() {
        //[1,2,3] >> ['a','b'] 
        //-- "ababab"
        _<ListOf,Integer> intList = ListOf.list(1,2,3);
        _<ListOf,String> stringList = ListOf.list("a","b");
        List<String> resultList = ListOf.unwrap(listBind.semicolon(intList,stringList));
        List<String> expectedList = List.list("a","b","a","b","a","b");
        Show<List<String>> show = Show.listShow(Show.stringShow);
        assertEquals(show.showS(expectedList), show.showS(resultList));
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
        F<String, _<OptionOf, Double>> lengthSqrtOptFn = optionBind.kleisli(lengthOptFn, sqrtOptFn);
        //... $ "123456789"
        //-- Just 3.0
        assertEquals("3.0", OptionOf.get(lengthSqrtOptFn.f("123456789")).toString());
        //... $ ""
        //-- Nothing
        assertEquals(true, OptionOf.isNone(lengthSqrtOptFn.f("")));
        //... $ "null"
        //-- Nothing
        assertEquals(true, OptionOf.isNone(lengthSqrtOptFn.f(null)));
    }
   
}
