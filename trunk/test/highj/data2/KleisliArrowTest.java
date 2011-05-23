/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.P2;
import fj.P;
import highj._;
import fj.F;
import fj.Show;
import highj.__;
import highj.data.OptionMonadPlus;
import highj.data.OptionOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dgronau
 */
public class KleisliArrowTest {

    private KleisliArrow<OptionOf> arrow;
    private __<Kleisli<OptionOf>, String, Integer> kleisliLength;

    @Before
    public void setUp() {
        arrow = new KleisliArrow<OptionOf>(OptionMonadPlus.getInstance());
        //kleisliLength = Kleisli (\x -> if null x then Nothing else Just (length x))
        kleisliLength = Kleisli.wrap(new F<String, _<OptionOf, Integer>>(){
            @Override
            public _<OptionOf, Integer> f(String s) {
                return s.equals("") ? OptionOf.<Integer>none() : OptionOf.some(s.length());
            }
        });
    }
    
    @After
    public void tearDown() {
        arrow = null;
        kleisliLength = null;
    }

    @Test
    public void testArr() {
        //runKleisli (arr(length) :: Kleisli Maybe String Int) "PIZZA"
        //-- Just 5        
        F<String, Integer> lengthFn = new F<String,Integer>(){
            @Override
            public Integer f(String s) {
                return s.length();
            }
        };
        __< Kleisli<OptionOf>, String, Integer> lengthKleisli = arrow.arr(lengthFn);
        assertEquals(Integer.valueOf(5), OptionOf.get(Kleisli.apply(lengthKleisli, "PIZZA")));
    }

    @Test
    public void testFirst() {
        __<Kleisli<OptionOf>,P2<String,Double>, P2<Integer,Double>> pairKleisli = 
                arrow.first(kleisliLength);
        //runKleisli (first kleisliLength) ("", 3.14)
        //-- Nothing
        assertEquals(true, OptionOf.isNone(Kleisli.apply(pairKleisli, P.p("",3.14))));
        //runKleisli (first kleisliLength) ("PIZZA", 3.14)
        //--Just (5,3.14)
        Show<P2<Integer,Double>> show = Show.p2Show(Show.intShow, Show.doubleShow);
        assertEquals("(5,3.14)", show.showS(OptionOf.get(Kleisli.apply(pairKleisli, P.p("PIZZA",3.14)))));
    }

    @Test
    public void testSecond() {
        __<Kleisli<OptionOf>,P2<Double,String>, P2<Double,Integer>> pairKleisli = 
                arrow.second(kleisliLength);
        //runKleisli (second kleisliLength) (3.14, "")
        //-- Nothing
        assertEquals(true, OptionOf.isNone(Kleisli.apply(pairKleisli, P.p(3.14,""))));
        //runKleisli (second kleisliLength) (3.14, "PIZZA")
        //--Just (5,3.14)
        Show<P2<Double,Integer>> show = Show.p2Show(Show.doubleShow, Show.intShow);
        assertEquals("(3.14,5)", show.showS(OptionOf.get(Kleisli.apply(pairKleisli, P.p(3.14,"PIZZA")))));
    }

    @Test
    public void testId() {
        //import qualified Control.Category as C (id)
        //runKleisli (C.id :: Kleisli Maybe String String) "PIZZA"
        __<Kleisli<OptionOf>, String, String> idArrow = arrow.id();
        assertEquals("PIZZA", OptionOf.get(Kleisli.apply(idArrow, "PIZZA")));
    }

    @Test
    public void testDot() {
        //runKleisli (arr(^2) <<< kleisliLength) "PIZZA"
        //-- Just 25        
        __<Kleisli<OptionOf>, Integer, Integer> sqrArrow = arrow.arr(new F<Integer,Integer>(){
            @Override
            public Integer f(Integer a) {
                return a*a;
            }
        });
        __<Kleisli<OptionOf>, String, Integer> dotArrow = arrow.dot(sqrArrow, kleisliLength);
        assertEquals(Integer.valueOf(25), OptionOf.get(Kleisli.apply(dotArrow, "PIZZA")));
        
    }
}
