/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.function.Strings;
import fj.F2;
import highj._;
import highj.LC;
import highj.typeclasses.category.Applicative;
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
    F<Integer, Integer> sqrFn;
    
    @Before
    public void setUp() {
        arrow = FunctionArrow.getInstance();
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
        sqrFn = null;
    }

    @Test
    public void testSecond() {
        //second length (3.14, "PIZZA")
        //-- (3.14,5)        
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(Strings.length);
        __<FunctionOf, P2<Double, String>, P2<Double, Integer>> sec = arrow.second(lengthArrow);
        Show<P2<Double, Integer>> show = Show.p2Show(Show.doubleShow, Show.intShow);
        assertEquals("(3.14,5)", show.showS(FunctionOf.apply(sec, P.p(3.14, "PIZZA"))));
    }

    @Test
    public void testSplit() {
        //length *** (^2) $ ("PIZZA",6)
        //-- (5,36)
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(Strings.length);
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
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(Strings.length);
        __<FunctionOf, String, P2<Character, Integer>> fanned = 
                arrow.fanout(maxCharArrow,lengthArrow);
        Show<P2<Character, Integer>> show = Show.p2Show(Show.charShow, Show.intShow);
        assertEquals("(Z,5)", show.showS(FunctionOf.apply(fanned, "PIZZA")));
    }

    @Test
    public void testGetApplicativePure() {
        Applicative<LC<FunctionOf,String>> applicative = arrow.getApplicative();
        _<LC<FunctionOf, String>, Integer> pureFnLC = applicative.pure(42);
        F<String, Integer> pureFn = FunctionOf.unwrapLC(pureFnLC);
        assertEquals(Integer.valueOf(42), pureFn.f("PIZZA"));
    }    

    @Test
    public void testGetApplicativeFmap() {
        Applicative<LC<FunctionOf,String>> applicative = arrow.getApplicative();
        _<LC<FunctionOf, String>, Integer> lengthFnLC = FunctionOf.wrapLC(Strings.length);
        _<LC<FunctionOf, String>, Integer> lengthSqrFnLC = applicative.fmap(sqrFn, lengthFnLC);
        F<String, Integer> lengthSqrFn = FunctionOf.unwrapLC(lengthSqrFnLC);
        assertEquals(Integer.valueOf(25), lengthSqrFn.f("PIZZA"));
    }        

    @Test
    public void testGetApplicativeAp() {
        Applicative<LC<FunctionOf,String>> applicative = arrow.getApplicative();
        _<LC<FunctionOf, String>, Integer> lengthFnLC = FunctionOf.wrapLC(Strings.length);
        _<LC<FunctionOf, String>, F<Integer,Double>> lengthSumSqrtLC = FunctionOf.wrapLC(
                new F2<String,Integer,Double>(){

            @Override
            public Double f(String a, Integer b) {
                return Math.sqrt(a.length() + b);
            }
        }.curry());
        _<LC<FunctionOf, String>, Double> lengthSqrtLC = applicative.ap(lengthSumSqrtLC, lengthFnLC);
        F<String, Double> lengthSqrtFn = FunctionOf.unwrapLC(lengthSqrtLC);
        assertEquals(Double.valueOf(4.0), lengthSqrtFn.f("12345678"));
    }
}
