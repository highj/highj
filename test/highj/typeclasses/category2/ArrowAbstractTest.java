/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import highj.data2.FunctionArrow;
import highj.data2.FunctionOf;
import fj.F;
import fj.P;
import fj.P2;
import fj.Show;
import highj.__;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class ArrowAbstractTest {
    
    Arrow<FunctionOf> arrow;
    F<String, Integer> lengthFn;
    F<Integer, Integer> sqrFn;
    
    @Before
    public void setUp() {
        arrow = FunctionArrow.getInstance();
        lengthFn = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
        sqrFn = new F<Integer, Integer>() {
            @Override
            public Integer f(Integer a) {
               return a*a;
            }
        };
    }
    
    @After
    public void tearDown() {
        arrow = null;
        lengthFn = null;
        sqrFn = null;
    }

    @Test
    public void testSecond() {
        //second length (3.14, "PIZZA")
        //-- (3.14,5)        
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, P2<Double, String>, P2<Double, Integer>> sec = arrow.second(lengthArrow);
        Show<P2<Double, Integer>> show = Show.p2Show(Show.doubleShow, Show.intShow);
        assertEquals("(3.14,5)", show.showS(FunctionOf.apply(sec, P.p(3.14, "PIZZA"))));
    }

    @Test
    public void testSplit() {
        //length *** (^2) $ ("PIZZA",6)
        //-- (5,36)
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, Integer, Integer> sqrArrow = FunctionOf.wrap(sqrFn);
        __<FunctionOf, P2<String, Integer>, P2<Integer, Integer>> splitted = 
                arrow.split(lengthArrow, sqrArrow);
        Show<P2<Integer, Integer>> show = Show.p2Show(Show.intShow, Show.intShow);
        assertEquals("(5,36)", show.showS(FunctionOf.apply(splitted, P.p("PIZZA", 6))));
    }

    @Test
    public void testFanout() {
        //maximum &&& length $ "PIZZA"
        //-- ('Z',5)
        F<String, Character> maxCharFn = new F<String, Character>(){
            @Override
            public Character f(String a) {
                char max = '\0';
                for(char c : a.toCharArray()) {
                    if (c > max) max = c;
                }
                return max;
            }
        };
        __<FunctionOf, String, Character> maxCharArrow = FunctionOf.wrap(maxCharFn);
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, String, P2<Character, Integer>> fanned = 
                arrow.fanout(maxCharArrow,lengthArrow);
        Show<P2<Character, Integer>> show = Show.p2Show(Show.charShow, Show.intShow);
        assertEquals("(Z,5)", show.showS(FunctionOf.apply(fanned, "PIZZA")));
    }

    @Test
    public void testThen() {
        //length >>> (^2) $ "PIZZA"
        //-- 25
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, Integer, Integer> sqrArrow = FunctionOf.wrap(sqrFn);
        __<FunctionOf, String, Integer> thenArrow = arrow.then(lengthArrow, sqrArrow);
        assertEquals(Integer.valueOf(25), FunctionOf.apply(thenArrow, "PIZZA"));
    }

}
